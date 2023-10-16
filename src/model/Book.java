package model;

import java.util.ArrayList;

public class Book {
    public enum bookType {Paper, Electronic};
    private bookType type;
    private String name;
    private String author;
    private int year;
    private String description;
    public Book(bookType type, String name, String author, int year, String description){
        this.type = type;
        this.name = name;
        this.author = author;
        this.year = year;
        this.description = description;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getType(){
        return type.toString();
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public int getYear(){
        return year;
    }
    public String getDescription(){
        return description;
    }
    public String toString() {
        String str = "Name: " + name + ", author: " + author + ", year:" + year + ", description: " + description;
        return str;
    }
}
