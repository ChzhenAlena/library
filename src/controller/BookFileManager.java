package controller;

import java.io.*;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import model.*;

public class BookFileManager {
    private final File directory;
    private final File bookFile;
    BookFileManager(String directory){
        this.directory = new File(directory);
        bookFile = new File(directory + "/" + "books.txt");
    }
    BookFileManager(){
        this("src/files");
    }


    public void downloadBooks(Catalog catalog){
        if(!bookFile.exists()) {
            try {
                if(bookFile.createNewFile())
                    System.out.println("Файла с книгами не существует. Был создан новый пустой файл.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(Scanner scanner = new Scanner(bookFile))
        {
            while(scanner.hasNext()){
                Book book = new Book();
                book.setType(Book.bookType.valueOf(scanner.next()));
                book.setName(scanner.next());
                book.setAuthor(scanner.next());
                book.setYear(scanner.nextInt());
                scanner.nextLine();
                book.setDescription(scanner.nextLine());
                catalog.addBook(book);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    };

    public void addBook(Book book){
        if(!bookFile.exists()) {
            try
            {
                bookFile.createNewFile();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        try(FileWriter writer = new FileWriter(bookFile, true))
        {
            writer.write(book.getStringForWritingToFile());
            System.out.println("Книга добавлена в файл");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void updateBookFile(Catalog catalog){
        File newFile = new File(directory + "/" + "books2.txt");
        for(Book book : catalog.getBooks()){
            try(FileWriter writer = new FileWriter(newFile, true))
            {
                writer.write(book.getStringForWritingToFile());
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        bookFile.delete();
        newFile.renameTo(bookFile);
    }



}
