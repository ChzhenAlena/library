package model;

import java.util.ArrayList;

public class Catalog {
    ArrayList<Book> books;
    public Catalog(){
        books = new ArrayList<>();
    }
    public void addBook(Book book){
        for(Book b : books)
            if(b.getName().equals(book.getName())){
                System.out.println("Книга с таким названием уже существует");
                return;
            }
        books.add(book);
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
