package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderDaoImpl implements OrderDao {
    private final OrderRepository orderRepository;

    public Integer addOrder(List<OrderItem> items, float total, User user) {
        return orderRepository.addOne(items, total, user.getId());
    }

    // Get the order of orderId where user_id = userId, along with access control
    public OrderDto getOrder(Integer orderId, Integer userId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(ResourceNotFoundException::new);
    }
}
