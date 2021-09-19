package com.yyoung.bookstore.dto.chat;

import com.yyoung.bookstore.constants.MessageType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@ApiModel("用户列表通告")
public class UserListMessage extends Message {
    private final MessageType type = MessageType.userList;

    private List<String> users;
}
