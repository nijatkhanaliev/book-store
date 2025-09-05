package com.bookstore.book;

import com.bookstore.category.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;
    @Min(value = 1, message = "Book must contain at least 1 page")
    private Integer pages;
    private Double price;
    @NotBlank
    private String language;
    @NotBlank
    private String description;
    @NotNull
    private Category category;
}
