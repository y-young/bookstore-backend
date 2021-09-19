package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.constants.OrderStatus;
import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.NewOrder;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.OrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProcessServiceImpl implements OrderProcessService {
    private final BookDao bookDao;
    private final OrderDao orderDao;

    @JmsListener(destination = "newOrder")
    @Transactional // Important
    public void processOrder(NewOrder newOrder) {
        User user = newOrder.getUser();
        List<OrderItem> items = newOrder.getItems();
        Order order = new Order();
        int total = 0, totalAmount = 0;
        // No matter it'll complete or fail, construct and save the order as a feedback
        for (OrderItem item :
                items) {
            Book book = bookDao.findById(item.getBook().getId());
            item.setBook(book);
            order.addItem(item);
            Integer amount = item.getAmount();
            totalAmount += amount;
            total += book.getPrice() * amount;
        }
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setTotal(total);
        // Validate the order and set status accordingly
        try {
            updateStocks(order);
            order.setStatus(OrderStatus.completed);
        } catch (BusinessLogicException exception) {
            order.setStatus(OrderStatus.failed);
            order.setFailedReason(exception.getReason());
        }
        orderDao.addOrder(order);
    }

    @Transactional
    public Order updateStocks(Order order) {
        for (OrderItem item :
                order.getItems()) {
            Book book = item.getBook();
            Integer amount = item.getAmount();
            if (book.getDeleted()) {
                throw new BusinessLogicException("商品已下架");
            }
            if (book.getStock() < item.getAmount()) {
                throw new BusinessLogicException("库存不足");
            }
            book.setStock(book.getStock() - amount);
            bookDao.updateOne(book);
            item.setBook(book);
        }
        return order;
    }
}
