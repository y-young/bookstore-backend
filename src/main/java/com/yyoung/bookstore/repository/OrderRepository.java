package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

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
}
