package com.luizfoli.literarbff.model;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private LeituraStatus readStatus;
    private String googleBooksApiId;
    private String title;
    private String author;
    private String description;
    private int rating;
    private String publisher;
    private String publishedDate;
    private int qtyPages;
    private int averageRating;
    private String imageLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public LeituraStatus getReadStatus() {
//        return readStatus;
//    }
//
//    public void setReadStatus(LeituraStatus readStatus) {
//        this.readStatus = readStatus;
//    }

    public String getGoogleBooksApiId() {
        return googleBooksApiId;
    }

    public void setGoogleBooksApiId(String googleBooksApiId) {
        this.googleBooksApiId = googleBooksApiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getQtyPages() {
        return qtyPages;
    }

    public void setQtyPages(int qtyPages) {
        this.qtyPages = qtyPages;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}