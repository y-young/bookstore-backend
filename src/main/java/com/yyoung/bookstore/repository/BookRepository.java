package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.dto.BookSales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findByDeletedIsFalse(Pageable pageable);

    Page<Book> findByTitleContainsAndDeletedIsFalse(String keyword, Pageable pageable);

    Optional<Book> findById(Integer bookId);

    List<Book> findByIdIn(List<Integer> bookIds);

    @Query("select new com.yyoung.bookstore.dto.BookSales(b, sum(oi.amount)) from Order o inner join o.items oi inner join oi.book b group by b.id order by sum(oi.amount) desc")
    List<BookSales> getSales();

    @Query("select new com.yyoung.bookstore.dto.BookSales(b, sum(oi.amount)) from Order o inner join o.items oi inner join oi.book b where o.time between :start and :end group by b.id order by sum(oi.amount) desc")
    List<BookSales> getSales(Date start, Date end);
}
