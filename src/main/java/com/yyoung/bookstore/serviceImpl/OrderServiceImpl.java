package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.constants.Role;
import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.OrderService;
import com.yyoung.bookstore.service.UserService;
import com.yyoung.bookstore.utils.Helpers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final BookDao bookDao;
    private final OrderDao orderDao;
    private final UserService userService;

    public List<Order> viewAllOrders(String bookTitle, Date start, Date end) {
        if (bookTitle != null && !bookTitle.isEmpty()) {
            if (Helpers.hasDateRange(start, end)) {
                return orderDao.getAllOrders(bookTitle, start, end);
            }
            return orderDao.getAllOrders(bookTitle);
        }
        if (Helpers.hasDateRange(start, end)) {
            return orderDao.getAllOrders(start, end);
        }
        return orderDao.getAllOrders();
    }

    @Transactional
    public Order placeOrder(List<OrderItem> items) {
        User user = userService.getCurrentUser();
        Order order = new Order();
        int total = 0, totalAmount = 0;
        for (OrderItem item :
                items) {
            Book book = bookDao.findById(item.getBook().getId());
            Integer amount = item.getAmount();
            if (book.getStock() < item.getAmount()) {
                throw new BusinessLogicException("库存不足");
            }
            bookDao.deductStock(book, amount);
            totalAmount += amount;
            total += book.getPrice() * amount;
            item.setBook(book);
            order.addItem(item);
        }
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setTotal(total);
        return orderDao.addOrder(order);
    }

    public Order viewOrder(Integer orderId) {
        User user = userService.getCurrentUser();
        if (user.getRole().equals(Role.admin)) { // Administrators can view orders of all users
            return orderDao.getOrder(orderId);
        } else { // While normal users can only view their own ones
            return orderDao.getUserOrder(orderId, user.getId());
        }
    }

    public List<Order> viewMyOrders(String bookTitle, Date start, Date end) {
        User user = userService.getCurrentUser();
        if (bookTitle != null && !bookTitle.isEmpty()) {
            if (Helpers.hasDateRange(start, end)) {
                return orderDao.getUserOrders(user.getId(), bookTitle, start, end);
            }
            return orderDao.getAllOrders(bookTitle);
        }
        if (Helpers.hasDateRange(start, end)) {
            return orderDao.getAllOrders(start, end);
        }
        return orderDao.getUserOrders(user.getId());
    }

    public List<BookTypeCount> getMyBookStatistics(Date start, Date end) {
        User user = userService.getCurrentUser();
        if (Helpers.hasDateRange(start, end)) {
            return orderDao.getUserBookStatistics(user.getId(), start, end);
        }
        return orderDao.getUserBookStatistics(user.getId());
    }

    public OrderStatistics getStatistics(Date start, Date end) {
        if (Helpers.hasDateRange(start, end)) {
            return orderDao.getStatistics(start, end);
        }
        return orderDao.getStatistics();
    }
}
