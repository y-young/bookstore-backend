package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.service.StatisticsService;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private static long pageView = 0L;

    public synchronized long updatePageView() {
        return ++StatisticsServiceImpl.pageView;
    }
}
