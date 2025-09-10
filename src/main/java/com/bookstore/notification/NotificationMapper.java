package com.bookstore.notification;

import com.bookstore.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    NotificationResponse<Book> toNotificationResponse(Notification<Book> notification);

    List<NotificationResponse<Book>> toNotificationResponses(List<Notification<Book>> allNotification);

}
