package com.yyoung.bookstore.search.dto.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("通用数据响应")
public class DataResponse<T> {
    private T data;

    public DataResponse(T data) {
        this.data = data;
    }
}
