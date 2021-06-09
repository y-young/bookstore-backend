package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.dto.UploadResult;
import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

@RestController
@Api("书籍")
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
    private final BookService bookService;

    @ApiOperation("获取所有书籍列表")
    @GetMapping
    public DataResponse<Page<Book>> listBooks(@RequestParam(value = "keyword", required = false) String keyword, Pageable pageable) {
        return new DataResponse<>(bookService.findAll(keyword, pageable));
    }

    @ApiOperation("获取指定书籍信息")
    @GetMapping("/{bookId}")
    public DataResponse<Book> getBook(@PathVariable Integer bookId) {
        return new DataResponse<>(bookService.findById(bookId));
    }

    @ApiOperation("添加书籍")
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto newBook) {
        bookService.addOne(newBook);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("删除书籍")
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteOne(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("批量删除书籍")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/delete")
    public ResponseEntity<?> deleteBooks(@RequestBody List<Integer> bookIds) {
        bookService.deleteMany(bookIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("修改书籍")
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{bookId}")
    public DataResponse<Book> editBook(@PathVariable Integer bookId, @Valid @RequestBody BookDto book) {
        return new DataResponse<>(bookService.updateOne(bookId, book));
    }

    @ApiOperation("上传封面")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/cover")
    public DataResponse<UploadResult> uploadCover(@RequestParam("file") MultipartFile file) {
        return new DataResponse<>(bookService.uploadCover(file));
    }

    @ApiOperation("查看封面")
    @GetMapping("/cover/{filename}")
    public ResponseEntity<?> viewCover(@PathVariable("filename") String filename) {
        Resource image = bookService.viewCover(filename);
        String fileType = URLConnection.guessContentTypeFromName(image.getFilename());
        return ResponseEntity.ok().contentType(MediaType.valueOf(fileType)).body(image);
    }

    @ApiOperation("统计销量")
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/sales")
    public DataResponse<List<BookSales>> getSales(@RequestParam(value = "start", required = false) Date start, @RequestParam(value = "end", required = false) Date end) {
        return new DataResponse<>(bookService.getSales(start, end));
    }
}
