package com.bookstore.book;

import com.bookstore.common.PageResponse;

public interface BookService {
    PageResponse<BookResponse> getAllSharableBook(int page, int size);

    PageResponse<BookResponse> getAllArchivedBookByOwner(int page, int size);

    void archiveBook(Long bookId);

    void makeBookShareable(Long bookId);
}
