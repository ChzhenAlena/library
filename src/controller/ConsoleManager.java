package controller;

import java.util.Scanner;

public class ConsoleManager {
    private Scanner scanner;
    public ConsoleManager(){
        scanner = new Scanner(System.in);
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
