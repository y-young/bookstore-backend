package com.yyoung.bookstore.dto.chat;

import com.yyoung.bookstore.constants.MessageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("聊天消息")
public class TextMessage extends Message {
    private final MessageType type = MessageType.text;

    @ApiModelProperty("发送者")
    private String sender;

    @ApiModelProperty("内容")
    private String content;
}
