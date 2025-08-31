package com.model;

public class TransactionCategory {

    private Integer id;
    private String categoryName;
    private String categoryColor;

    public TransactionCategory(){

    }

    public TransactionCategory(Integer id, String categoryName, String categoryColor) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
