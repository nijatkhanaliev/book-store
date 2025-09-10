package com.bookstore.notification;

import com.bookstore.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification<Book>, Long> {
}
