package model;

public abstract class User {
    private String email;
    private String password;
    User(String email, String password){
        this.email = email;
        this.password = password;
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

    /*public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj == null || (obj.getClass() != this.getClass()))
            return false;
        User u = (User) obj;
        return this.email == u.getEmail();//и password
    }*/
}
