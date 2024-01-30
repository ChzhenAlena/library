package controller;

import model.Book;
import model.User;

import java.util.Scanner;

public class ConsoleManager {
    private Scanner scanner;
    public ConsoleManager(){
        scanner = new Scanner(System.in);
    }
    public User getUserFromConsole(){
        System.out.println("Введите email");
        String email = getString();
        System.out.println("Введите пароль");
        String password = getString();
        return new User(email, password, false);
    }
    public Book getBookFromConsole(){
        System.out.println("Введите тип: Paper/Electronic");
        Book.bookType type = Book.bookType.valueOf(getBookType());
        System.out.println("Введите название");
        String name = getString();
        System.out.println("Введите автора");
        String author = getString();
        System.out.println("Введите год");
        int year = getYear();
        System.out.println("Введите описание");
        String description = getString();
        return new Book(type, name, author, year, description);
    }
    public String getString(){
        String line = scanner.nextLine();
        return line;
    }
    public int getNumber(){
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }
    private String getBookType(){
        String line = scanner.nextLine();
        while(!line.equals("Paper") && !line.equals("Electronic")){
            System.out.println("Введите Paper или Electronic");
            line = scanner.nextLine();
        }
        return line;
    }
    private int getYear(){
        int number = scanner.nextInt();
        scanner.nextLine();
        while(number > java.time.Year.now().getValue()){
            System.out.println("Введенный год больше текущего года. Введите корректный год: ");
            number = scanner.nextInt();
            scanner.nextLine();
        }
        return number;
    }

    public void printStartMenu(){
        String menu = "1. Войти в систему\n" +
                "2. Регистрация\n" +
                "3. Завершить";
        System.out.println(menu);
    }
    public void printUserMenu(){
        String menu = "1. Просмотреть каталог\n"+
                "2. Найти книгу\n" +
                "3. Предложить новую книгу\n" +
                "4. Выйти из системы";
        System.out.println(menu);
    }
    public void printAdminMenu(){
        String menu = "1. Просмотреть каталог\n"+
                "2. Добавить книгу\n" +
                "3. Удалить книгу\n" +
                "4. Выйти из системы";
        System.out.println(menu);
    }

}
