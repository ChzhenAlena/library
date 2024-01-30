package model;

import java.util.Objects;

public class User {
    private String email;
    private String password;
    private boolean isAdmin;
    public User(String email, String password, boolean isAdmin){
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public String toString(){
        String str = "Email: " + email + ", password: " + password + ", class: " + getClass();
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
