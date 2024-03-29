package model;

import java.util.ArrayList;
import java.util.List;

public class UserBase {
    List<User> users;
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
    public List<User> getUserList(){
        return users;
    }
    public User logIn(User user){
        for(User u : users){
            if(user.equals(u)) {
                System.out.println("Вы вошли в систему");
                return u;
            }
        }
        System.out.println("Неверный email или пароль");
        return null;
    }
}
