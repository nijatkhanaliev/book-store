package com.bookstore.notification;

import com.bookstore.book.Book;

import java.util.List;

public interface NotificationReader {

    List<NotificationResponse<Book>> getAllBookNotification();

    NotificationResponse<Book> getBookNotificationById(Long notificationId);

    void markAsRead(Long notificationId);

}
