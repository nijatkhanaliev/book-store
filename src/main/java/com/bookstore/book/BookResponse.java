package com.bookstore.book;

import com.bookstore.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private Double price;
    private String language;
    private String description;
    private String coverImage;
    private Category category;
}
