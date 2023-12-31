package controller;

import java.io.*;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import model.*;

public class FileManager {
    private File directory;
    private File bookFile;
    private File usersFile;
    FileManager(String directory){
        this.directory = new File(directory);
        bookFile = new File(directory + "/" + "books.txt");
        usersFile = new File(directory + "/" + "users.txt");
        Encryptor.initEncryptor();
    }
    FileManager(){
        this("src/files");
    }

    public void downloadUsers(UserBase userBase){
        if(!usersFile.exists()) {
            System.out.println("Файла пользователей не существует");
        }
        try(Scanner scanner = new Scanner(usersFile))
        {
            String em;
            String pass;
            int isAdmin;
            User user;
            while(scanner.hasNext()){
                em = scanner.next();
                int a = 0;
                byte b = 0;
                ArrayList<Byte> passwordBytes = new ArrayList<Byte>();
                while(scanner.hasNextInt()) {
                    a = scanner.nextInt();
                    b = Byte.valueOf(String.valueOf(a));
                    passwordBytes.add(b);
                }
                passwordBytes.remove((passwordBytes.size()-1));
                byte[] encryptedPass = new byte[passwordBytes.size()];
                for(int i = 0; i < passwordBytes.size(); i++){
                    encryptedPass[i] = Byte.valueOf(passwordBytes.get(i));
                }
                pass = Encryptor.decrypt(encryptedPass);
                isAdmin = a;
                if(isAdmin == 1)
                    user = new Admin(em, pass);
                else
                    user = new RegularUser(em, pass);
                userBase.addUser(user);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    };
    public void downloadBooks(Catalog catalog){
        if(!bookFile.exists()) {
            System.out.println("Файла книг не существует");
        }
        try(Scanner scanner = new Scanner(bookFile))
        {
            String type;
            String name;
            String author;
            int year;
            String description;
            Book book;
            while(scanner.hasNext()){
                type = scanner.next();
                name = scanner.next();
                author = scanner.next();
                year = scanner.nextInt();
                scanner.nextLine();
                description = scanner.nextLine();
                book = new Book(Book.bookType.valueOf(type) ,name, author, year, description);
                catalog.addBook(book);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    };
    public void addUser(User user){
        if(!usersFile.exists()) {
            try
            {
                usersFile.createNewFile();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        try(FileWriter writer = new FileWriter(usersFile, true))
        {
            if(usersFile.length() != 0L)
                writer.append('\n');
            writer.write(user.getEmail());
            writer.append(' ');
            byte[] encryptedPassword = Encryptor.encrypt(user.getPassword());
            for(byte b : encryptedPassword) {
                writer.write(String.valueOf(b));
                writer.append(' ');
            }
            writer.append(' ');
            if(user instanceof RegularUser)
                writer.append('0');
            else
                writer.append('1');
            //writer.append(" ;");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
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
            writer.append('\n');
            writer.write(book.getType());
            writer.append(' ');
            writer.write(book.getName());
            writer.append(' ');
            writer.write(book.getAuthor());
            writer.append(' ');
            writer.write(String.valueOf(book.getYear()));
            writer.append('\n');
            writer.write(book.getDescription());
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
                writer.write(book.getType());
                writer.append(' ');
                writer.write(book.getName());
                writer.append(' ');
                writer.write(book.getAuthor());
                writer.append(' ');
                writer.write(String.valueOf(book.getYear()));
                writer.append('\n');
                writer.write(book.getDescription());
                writer.append('\n');
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        bookFile.delete();
        newFile.renameTo(bookFile);
    }



}
