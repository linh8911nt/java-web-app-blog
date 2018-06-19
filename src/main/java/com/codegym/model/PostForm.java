package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class PostForm {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private MultipartFile image;
    private String imageUrl;
    private LocalDate createDate;
    private Category category;

    public PostForm() {
    }

    public PostForm(String title, String summary, String content, MultipartFile image, String imageUrl, LocalDate createDate, Category category) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.image = image;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
