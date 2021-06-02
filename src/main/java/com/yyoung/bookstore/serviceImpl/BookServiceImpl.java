package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.dto.UploadResult;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;
    private final String staticPath = "src/main/resources/static";

    public Book findById(Integer bookId) {
        return bookDao.findById(bookId);
    }

    public Page<Book> findAll(String keyword, Pageable pageable) {
        if (keyword != null) {
            return bookDao.findByKeyword(keyword, pageable);
        }
        return bookDao.findAll(pageable);
    }

    public void addOne(BookDto newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        bookDao.addOne(book);
    }

    public void deleteOne(Integer bookId) {
        bookDao.deleteOne(bookId);
    }

    public Book updateOne(Integer bookId, BookDto book) {
        Book existingBook = bookDao.findById(bookId);
        modelMapper.map(book, existingBook);
        return bookDao.updateOne(existingBook);
    }

    public UploadResult uploadCover(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension, storeFilename;
        if (filename != null && filename.contains(".")) {
            extension = filename.substring(filename.lastIndexOf(".") + 1);
        } else {
            throw new ValidationException("请使用合法的文件名");
        }
        String fileType = URLConnection.guessContentTypeFromName(filename);
        if (!Arrays.asList("image/jpeg", "image/png", "image/webp").contains(fileType)) {
            throw new ValidationException("请上传JPG, PNG或WebP类型的图片");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ValidationException("请上传大小小于5MB的图片");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(file.getBytes());
            String md5 = new BigInteger(1, digest).toString(16);
            storeFilename = md5 + "." + extension;
            String uploadPath = new FileSystemResource(staticPath).getFile().getAbsolutePath();
            File storeFile = new File(uploadPath, storeFilename);
            if (!storeFile.exists()) {
                file.transferTo(storeFile);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new UploadResult(storeFilename);
    }

    public Resource viewCover(String filename) {
        String uploadPath = new FileSystemResource(staticPath).getFile().getAbsolutePath();
        Resource image = resourceLoader.getResource("file:" + uploadPath + "/" + filename);
        if (!image.exists()) {
            throw new ResourceNotFoundException();
        }
        return image;
    }

    public List<BookSales> getSales(Date start, Date end) {
        boolean hasStart = start != null, hasEnd = end != null;
        // If there's a date range, it must be bounded
        if (hasStart && !hasEnd || !hasStart && hasEnd) {
            throw new ValidationException("请选择日期范围");
        }
        return bookDao.getSales(start, end);
    }
}
