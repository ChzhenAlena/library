package controller;
import model.*;
//строка 45!!!
public class Library {
    private final Catalog catalog;
    private final UserBase userBase;
    private final FileManager fileManager;
    private final ConsoleManager console;
    private final EmailSender emailSender;
    public Library(){
        catalog = new Catalog();
        userBase = new UserBase();
        fileManager = new FileManager();
        console = new ConsoleManager();
        emailSender = new EmailSender();
    }

    public void downloadData(){
        fileManager.downloadUsers(userBase);
        fileManager.downloadBooks(catalog);
    }
    public User logIn(){
        User user = console.getUserFromConsole();
        return userBase.logIn(user);
    }
    public User logOut(){
        return null;
    };
    public void register(){
        User user = console.getUserFromConsole();
        userBase.addUser(user);
        fileManager.addUser(user);
        System.out.println("Вы зарегестрированы");
    };
    public void showCatalog(){
        catalog.print();
    };
    public void addBook(){
        Book book = console.getBookFromConsole();
        catalog.addBook(book);
        fileManager.addBook(book);
        emailSender.send("Добавлена новая книга", book.toString(), userBase);
    };
    public void deleteBook(){
        System.out.println("Введите название книги");
        String bookName = console.getString();
        catalog.deleteBook(bookName);
        fileManager.updateBookFile(catalog);
    };
    public void findBook(){
        System.out.println("Введите название книги");
        String bookName = console.getString();
        System.out.println(catalog.findBook(bookName).toString());
    };
    public void offerBook(){
        Book book = console.getBookFromConsole();
        UserBase userBaseAdmins = new UserBase();
        for(User u : userBase.getUserList()){
            if(u.isAdmin())
                userBaseAdmins.addUser(u);
        }
        emailSender.send("Давайте добавим книгу", book.toString(), userBaseAdmins);
    };
}
