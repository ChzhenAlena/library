package model;

import java.util.ArrayList;

public class UserBase {
    ArrayList<User> users;
    public UserBase(){
        users = new ArrayList<>();
    }
    public void addUser(User user){
        users.add(user);
    }
    public void print(){
        for(User u : users)
            System.out.println(u.toString());
    }
    public User logIn(String email, String password){
        for(User u : users){
            if(u.getEmail().equals(email) && u.getPassword().equals(password)) {
                System.out.println("Вы вошли в систему");
                return u;
            }
        }
        System.out.println("Неверный email или пароль");
        return null;
    }
}
