package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.OrderService;
import com.yyoung.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final BookDao bookDao;
    private final OrderDao orderDao;
    private final UserService userService;

    @Transactional
    public Order placeOrder(List<OrderItem> items) {
        User user = userService.getCurrentUser();
        Order order = new Order();
        int total = 0;
        for (OrderItem item :
                items) {
            Book book = bookDao.findById(item.getBook().getId());
            Integer amount = item.getAmount();
            if (book.getStock() < item.getAmount()) {
                throw new BusinessLogicException("库存不足");
            }
            bookDao.deductStock(book, amount);
            total += book.getPrice() * amount;
            item.setBook(book);
            order.addItem(item);
        }
        order.setUser(user);
        order.setTotal(total);
        return orderDao.addOrder(order);
    }

    public Order viewOrder(Integer orderId) {
        User user = userService.getCurrentUser();
        return orderDao.getOrder(orderId, user.getId());
    }

    public List<Order> viewMyOrders() {
        User user = userService.getCurrentUser();
        return orderDao.getUserOrders(user.getId());
    }
}
