package com.bookstore.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse<T> {
    private Long id;
    private T data;
    private String message;
    private boolean read;
}
