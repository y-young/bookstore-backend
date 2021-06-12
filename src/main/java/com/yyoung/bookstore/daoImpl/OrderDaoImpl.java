package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;
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

    public List<Order> getAllOrders(String bookTitle) {
        return orderRepository.findByItemsBookTitleContains(bookTitle);
    }

    public List<Order> getAllOrders(Date start, Date end) {
        return orderRepository.findByTimeBetween(start, end);
    }

    public List<Order> getAllOrders(String bookTitle, Date start, Date end) {
        return orderRepository.findByItemsBookTitleContainsAndTimeBetween(bookTitle, start, end);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get order without limitations, used by administrators
    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(ResourceNotFoundException::new);
    }

    // Get the order of orderId where user_id = userId, along with access control
    public Order getUserOrder(Integer orderId, User user) {
        return orderRepository.findByIdAndUser(orderId, user).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getUserOrders(User user, String bookTitle) {
        return orderRepository.findDistinctByUserAndItemsBookTitleContains(user, bookTitle);
    }

    public List<Order> getUserOrders(User user, Date start, Date end) {
        return orderRepository.findByUserAndTimeBetween(user, start, end);
    }

    public List<Order> getUserOrders(User user, String bookTitle, Date start, Date end) {
        return orderRepository.findDistinctByUserAndItemsBookTitleContainsAndTimeBetween(user, bookTitle, start, end);
    }

    public List<BookTypeCount> getUserBookStatistics(Integer userId) {
        return orderRepository.getUserBookStatistics(userId);
    }

    public List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end) {
        return orderRepository.getUserBookStatistics(userId, start, end);
    }

    public OrderStatistics getStatistics() {
        return orderRepository.getStatistics();
    }

    public OrderStatistics getStatistics(Date start, Date end) {
        return orderRepository.getStatistics(start, end);
    }
}
