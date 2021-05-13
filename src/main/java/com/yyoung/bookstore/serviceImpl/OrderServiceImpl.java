package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dao.OrderDao;
import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.OrderService;
import com.yyoung.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Transactional
    public OrderDto placeOrder(List<OrderItem> items) {
        User user = userService.getCurrentUser();
        OrderDto order = new OrderDto();
        float total = 0.0F;
        for (OrderItem item :
                items) {
            Book book = bookDao.findById(item.getId());
            Integer amount = item.getAmount();
            if (book.getStock() < item.getAmount()) {
                throw new BusinessLogicException("库存不足");
            }
            bookDao.deductStock(book, amount);
            total += book.getPrice() * amount;
            modelMapper.map(book, item);
            order.addItem(item);
        }
        Integer orderId = orderDao.addOrder(items, total, user);
        order.setId(orderId);
        return order;
    }

    public OrderDto viewOrder(Integer orderId) {
        User user = userService.getCurrentUser();
        return orderDao.getOrder(orderId, user.getId());
    }

    public List<OrderDto> viewMyOrders() {
        User user = userService.getCurrentUser();
        return orderDao.getUserOrders(user.getId());
    }
}
