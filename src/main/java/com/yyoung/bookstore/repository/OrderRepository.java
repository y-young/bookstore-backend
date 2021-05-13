package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class OrderDtoResultExtractor implements ResultSetExtractor<List<OrderDto>> {
    @Override
    public List<OrderDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, OrderDto> orders = new HashMap<>();
        while (resultSet.next()) {
            Integer orderId = resultSet.getInt("id");
            if (!orders.containsKey(orderId)) {
                orders.put(orderId, new OrderDto(
                        orderId,
                        resultSet.getFloat("total"),
                        resultSet.getTimestamp("time")
                ));
            }
            orders.get(orderId).addItem(new OrderItem(
                    resultSet.getInt("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("isbn"),
                    resultSet.getInt("stock"),
                    resultSet.getFloat("price"),
                    resultSet.getString("type"),
                    resultSet.getString("cover"),
                    resultSet.getInt("amount")
            ));
        }
        return new ArrayList<>(orders.values());
    }
}

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addOne(List<OrderItem> items, float total, Integer userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `order` (user_id, total) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setFloat(2, total);
            return preparedStatement;
        }, keyHolder);
        Integer orderId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        jdbcTemplate.batchUpdate("INSERT INTO order_book VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, items.get(i).getId());
                preparedStatement.setInt(3, items.get(i).getAmount());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
        return orderId;
    }

    public Optional<OrderDto> findByIdAndUserId(Integer orderId, Integer userId) {
        List<OrderDto> orders = jdbcTemplate.query(
                "SELECT * FROM `order` JOIN order_book ob on `order`.id = ob.order_id JOIN book b on b.id = ob.book_id WHERE order_id=? AND user_id=?",
                new OrderDtoResultExtractor(),
                orderId,
                userId
        );
        if (orders == null || orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orders.get(0));
    }

    public List<OrderDto> findByUserId(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM `order` JOIN order_book ob on `order`.id = ob.order_id JOIN book b on b.id = ob.book_id WHERE user_id=?",
                new OrderDtoResultExtractor(),
                userId
        );
    }
}
