package com.yyoung.bookstore.utils;

import javax.validation.ValidationException;
import java.util.Date;

public class Helpers {
    public static boolean hasDateRange(Date start, Date end) {
        boolean hasStart = start != null, hasEnd = end != null;
        // If there's a date range, it must be bounded
        if (hasStart && !hasEnd || !hasStart && hasEnd) {
            throw new ValidationException("请选择日期范围");
        }
        if (hasStart && start.after(end)) {
            throw new ValidationException("结束日期必须早于起始日期");
        }
        return hasStart;
    }
}
