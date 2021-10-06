package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.UpdateStocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// Extra service for test purpose
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateStocksServiceImpl implements UpdateStocksService {
    private final BookDao bookDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void updateStocks(Order order) {
        for (OrderItem item :
                order.getItems()) {
            Book book = item.getBook();
            Integer amount = item.getAmount();
            if (book.getDeleted()) {
                throw new BusinessLogicException("商品已下架");
            }
            if (book.getStock() < amount) {
                throw new BusinessLogicException("库存不足");
            }
            bookDao.reduceStock(book.getId(), amount);
        }
    }
}
