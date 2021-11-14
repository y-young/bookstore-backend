package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.dto.UploadResult;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.BookCover;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.BookCoverRepository;
import com.yyoung.bookstore.service.BookService;
import com.yyoung.bookstore.service.SearchService;
import com.yyoung.bookstore.utils.Helpers;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final SearchService searchService;
    private final BookCoverRepository bookCoverRepository;

    private final ModelMapper modelMapper;

    /*
     BookDao.findById shouldn't be cached since it's used by OrderService to check stock,
     besides it would also result in a detached Hibernate entity fetched from cache.
     https://stackoverflow.com/questions/47154380/saving-entity-with-cached-object-in-it-causing-detached-entity-exception
     */
    @Cacheable(key = "#bookId")
    public Book findById(Integer bookId) {
        return bookDao.findById(bookId);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookDao.findAll(pageable);
    }

    public void addOne(BookDto newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        book = bookDao.addOne(book);
        searchService.addToIndex(book);
    }

    public void deleteOne(Integer bookId) {
        Book book = bookDao.findById(bookId);
        book.setDeleted(true);
        bookDao.updateOne(book);
        searchService.removeFromIndex(book);
    }

    public void deleteMany(List<Integer> bookIds) {
        List<Book> books = bookDao.findByIdIn(bookIds);
        for (Book book :
                books) {
            book.setDeleted(true);
        }
        bookDao.updateMany(books);
        searchService.removeFromIndex(books);
    }

    public Book updateOne(Integer bookId, BookDto book) {
        Book existingBook = bookDao.findById(bookId);
        modelMapper.map(book, existingBook);
        Book newBook = bookDao.updateOne(existingBook);
        searchService.updateIndex(newBook);
        return newBook;
    }

    public UploadResult uploadCover(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new ValidationException("请使用合法的文件名");
        }
        String fileType = URLConnection.guessContentTypeFromName(filename);
        if (!Arrays.asList("image/jpeg", "image/png", "image/webp").contains(fileType)) {
            throw new ValidationException("请上传JPG, PNG或WebP类型的图片");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ValidationException("请上传大小小于5MB的图片");
        }
        BookCover bookCover = new BookCover();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(file.getBytes());
            String md5 = new BigInteger(1, digest).toString(16);
            bookCover.setId(md5);
            bookCover.setData(file.getBytes());
            bookCover = bookCoverRepository.save(bookCover);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new UploadResult(bookCover.getId());
    }

    public byte[] viewCover(String id) {
        Optional<BookCover> bookCover = bookCoverRepository.findById(id);
        if (!bookCover.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return bookCover.get().getData();
    }

    public Page<BookSales> getSales(Date start, Date end, Pageable pageable) {
        if (Helpers.hasDateRange(start, end)) {
            return bookDao.getSales(start, end, pageable);
        }
        return bookDao.getSales(pageable);
    }

    public List<Book> getLatest() {
        return bookDao.getLatest(PageRequest.of(0, 4));
    }

    public List<Book> getBestSales() {
        return bookDao.getBestSales(PageRequest.of(0, 4));
    }
}
