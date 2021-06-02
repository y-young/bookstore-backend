package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderDaoImpl implements OrderDao {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrders(Date start, Date end) {
        return orderRepository.findByTimeBetween(start, end);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get order without limitations, used by administrators
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
    }

    // Get the order of orderId where user_id = userId, along with access control
    public Order getUserOrder(Integer orderId, Integer userId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Order> getUserOrders(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getUserOrders(Integer userId, Date start, Date end) {
        return orderRepository.findByUserIdAndTimeBetween(userId, start, end);
    }

    public List<BookTypeCount> getUserBookStatistics(Integer userId) {
        return orderRepository.getUserBookStatistics(userId);
    }

    public List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end) {
        return orderRepository.getUserBookStatisticsBetween(userId, start, end);
    }
}
