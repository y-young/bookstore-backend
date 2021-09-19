package com.yyoung.bookstore.dto.chat;

import com.yyoung.bookstore.constants.MessageType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户加入通知")
public class JoinMessage extends Message {
    private final MessageType type = MessageType.join;

    private String username;
}
