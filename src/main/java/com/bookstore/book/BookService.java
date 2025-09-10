package com.bookstore.book;

import com.bookstore.common.PageResponse;

public interface BookService {

    void createBook(BookRequest bookRequest);

    PageResponse<BookResponse> getAllShareableBook(int page, int size);

    PageResponse<BookResponse> getAllArchivedBookByOwner(int page, int size);

    void archiveBook(Long bookId);

    void makeBookShareable(Long bookId);
}
