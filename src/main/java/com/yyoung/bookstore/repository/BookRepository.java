package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Book(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getString("isbn"),
                resultSet.getInt("stock"),
                resultSet.getFloat("price"),
                resultSet.getString("type"),
                resultSet.getString("cover")
        );
    }
}

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BookRowMapper());
    }

    public Optional<Book> findById(Integer bookId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", new BookRowMapper(), bookId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public void updateStock(Integer bookId, Integer stock) {
        jdbcTemplate.update("UPDATE book SET stock=? WHERE id=?", stock, bookId);
    }
}
