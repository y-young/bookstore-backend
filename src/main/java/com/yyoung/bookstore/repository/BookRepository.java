package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.dto.BookSales;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();

    Optional<Book> findById(Integer bookId);

    @Query("select new com.yyoung.bookstore.dto.BookSales(b, sum(oi.amount)) from Order o inner join o.items oi inner join oi.book b group by b.id order by sum(oi.amount) desc")
    List<BookSales> getSales();
}
