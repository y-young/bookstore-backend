package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("文件上传结果")
public class UploadResult {
    @ApiModelProperty("文件ID")
    private String id;
}
