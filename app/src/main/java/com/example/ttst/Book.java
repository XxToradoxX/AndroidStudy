package com.example.ttst;

public class Book {
    private int coverRes;
    private String bookName;
    private String author;

    public Book(int coverRes, String bookName, String author) {
        this.coverRes = coverRes;
        this.bookName = bookName;
        this.author = author;
    }

    public int getCoverRes() { return coverRes; }
    public String getBookName() { return bookName; }
    public String getAuthor() { return author; }
}
