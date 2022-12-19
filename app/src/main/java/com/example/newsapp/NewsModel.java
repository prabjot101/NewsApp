package com.example.newsapp;

public class NewsModel {
    private String title;
    private String author;
    private String image;
    private String description;
    private String publishDate;
    private String source;
    private String category;
//    private boolean isSelected;

    public NewsModel() {
    }



//    public NewsModel(String title, String author, String image, String description, String publishDate, String source, String category, boolean isSelected) {
//        this.title = title;
//        this.author = author;
//        this.image = image;
//        this.description = description;
//        this.publishDate = publishDate;
//        this.source = source;
//        this.category = category;
//        this.isSelected = isSelected;
//    }

    public NewsModel(String title, String author, String image, String description, String publishDate, String source, String category) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.description = description;
        this.publishDate = publishDate;
        this.source = source;
        this.category = category;
    }

    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public String getImage() {
        return image;
    }

    public String getDescription() {return description;}

    public String getPublishDate() {return publishDate;}

    public String getSource() {return source;}

    public String getCategory() {return category;}

//    public boolean isSelected() {return isSelected;}
//
//    public void setSelected(boolean selected) {isSelected = selected;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
