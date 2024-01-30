package model;

import java.util.ArrayList;

public class Catalog {
    ArrayList<Book> books;
    public Catalog(){
        books = new ArrayList<>();
    }
    public boolean addBook(Book book){
        for(Book b : books)
            if(b.getName().equals(book.getName()))
                return false;
        books.add(book);
        return true;
    }
    public void deleteBook(String bookName){
        books.remove(findBook(bookName));
    }
    public void print(){
        for(Book b : books)
            System.out.println(b.toString());
    }

    public Book findBook(String name){
        for(Book b : books){
            if(b.getName().equals(name))
                return b;
        }
        return null;
    }
    public ArrayList<Book> getBooks(){
        return books;
    }

}
