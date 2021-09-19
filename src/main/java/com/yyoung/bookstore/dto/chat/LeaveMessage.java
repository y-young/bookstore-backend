package com.yyoung.bookstore.dto.chat;

import com.yyoung.bookstore.constants.MessageType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户离开通知")
public class LeaveMessage extends Message {
    private final MessageType type = MessageType.leave;

    private String username;
}
