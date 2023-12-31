package controller;
import model.*;
//строка 45!!!
public class Library {
    private Catalog catalog;
    private UserBase userBase;
    private FileManager fileManager;
    private ConsoleManager console;
    private EmailSender emailSender;
    public Library(){
        catalog = new Catalog();
        userBase = new UserBase();
        fileManager = new FileManager();
        console = new ConsoleManager();
        emailSender = new EmailSender("mail", "pass");
        //Encryptor.initEncryptor();
    }

    public void downloadData(){
        fileManager.downloadUsers(userBase);
        fileManager.downloadBooks(catalog);
    }
    public User logIn(){
        System.out.println("Введите email");
        String email = console.getString();
        System.out.println("Введите пароль");
        String password = console.getString();
        return userBase.logIn(email, password);
    }
    public User logOut(){
        return null;
    };
    public void register(){
        System.out.println("Введите email");
        String email = console.getString();
        System.out.println("Введите пароль");
        String password = console.getString();
        System.out.println("Вы админ? 0 - нет, 1 - да");
        int isAdmin = console.getNumber();
        User user = null;
        if(isAdmin == 0)
            user = new RegularUser(email, password);
        if(isAdmin == 1)
            user = new Admin(email, password);
        userBase.addUser(user);
        fileManager.addUser(user);
        System.out.println("Вы зарегестрированы");
    };
    public void showCatalog(){
        catalog.print();
    };
    public void showUserBase(){
        userBase.print();
    };
    public void addBook(){
        System.out.println("Введите тип (Paper/Electronic)");
        Book.bookType type = Book.bookType.valueOf(console.getString());
        System.out.println("Введите название");
        String name = console.getString();
        System.out.println("Введите автора");
        String author = console.getString();
        System.out.println("Введите год");
        int year = console.getNumber();
        System.out.println("Введите описание");
        String description = console.getString();
        Book book = new Book(type, name, author, year, description);
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
        System.out.println("Введите тип (Paper/Electronic)");
        Book.bookType type = Book.bookType.valueOf(console.getString());
        System.out.println("Введите название");
        String name = console.getString();
        System.out.println("Введите автора");
        String author = console.getString();
        System.out.println("Введите год");
        int year = console.getNumber();
        System.out.println("Введите описание");
        String description = console.getString();
        Book book = new Book(type, name, author, year, description);
        UserBase userBaseAdmins = new UserBase();
        for(User u : userBase.getUserList()){
            if(u instanceof Admin)
                userBaseAdmins.addUser(u);
        }
        emailSender.send("Давайте добавим книгу", book.toString(), userBaseAdmins);
    };
}
