package com.example.bookstoreapp.ds;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookDto {

    private Integer id;
    private String title;
    private double price;
    @Min(value = 1)
    @Max(value = 5)
    private int quantity=1;
    private LocalDate yearPublished;
    private String description;
    private String imgUrl;
    private int categoryId;
    private int authorId;

    private List<Integer> bookNumberList=
            new LinkedList<>();

    public BookDto(){

    }

    public BookDto(Integer id, String title, double price, LocalDate yearPublished, String description, String imgUrl, int categoryId, int authorId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.yearPublished = yearPublished;
        this.description = description;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
        this.authorId = authorId;
    }
}