package com.yyoung.bookstore.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyoung.bookstore.constants.MessageType;
import com.yyoung.bookstore.dto.chat.JoinMessage;
import com.yyoung.bookstore.dto.chat.LeaveMessage;
import com.yyoung.bookstore.dto.chat.Message;
import com.yyoung.bookstore.dto.chat.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.lang.reflect.Type;
import java.util.Map;

@Slf4j
public class MessageDecoder implements Decoder.Text<Message> {
    private static final Gson gson = new Gson();
    private static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Message decode(String message) throws DecodeException {
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        try {
            Map<String, String> result = gson.fromJson(message, mapType);
            if (!result.containsKey("type")) {
                return new Message();
            }
            switch (MessageType.valueOf(MessageType.class, result.get("type"))) {
                case text:
                    return modelMapper.map(result, TextMessage.class);
                case join:
                    return modelMapper.map(result, JoinMessage.class);
                case leave:
                    return modelMapper.map(result, LeaveMessage.class);
            }
        } catch (Exception exception) {
            log.warn("Error decoding message: {}", message);
            exception.printStackTrace();
            return new Message();
        }
        return new Message();
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
