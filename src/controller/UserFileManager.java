package controller;

import java.io.*;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import model.*;

public class UserFileManager {
    private final File directory;
    private final File usersFile;
    UserFileManager(String directory){
        this.directory = new File(directory);
        usersFile = new File(directory + "/" + "users.txt");
        Encryptor.initEncryptor();
    }
    UserFileManager(){
        this("src/files");
    }

    public void downloadUsers(UserBase userBase){
        if(!usersFile.exists()) {
            try {
                if(usersFile.createNewFile())
                    System.out.println("Файла с пользователями не существует. Был создан новый пустой файл.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try(Scanner scanner = new Scanner(usersFile))
        {
            while(scanner.hasNext()){
                User user = new User();
                user.setEmail(scanner.next());
                user.setAdmin(Boolean.parseBoolean(scanner.next()));
                user.setPassword(getPassword(scanner));
                userBase.addUser(user);
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }
    private String getPassword(Scanner scanner){
        List<Byte> passwordBytes = new ArrayList<>();
        while(scanner.hasNextInt()) {
            passwordBytes.add(Byte.valueOf(scanner.next()));
        }
        byte[] encryptedPass = new byte[passwordBytes.size()];
        for(int i = 0; i < passwordBytes.size(); i++){
            encryptedPass[i] = passwordBytes.get(i);
        }
        return Encryptor.decrypt(encryptedPass);
    }

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
            writer.write(user.getEmail() + " ");
            if(!user.isAdmin())
                writer.write("false ");
            else
                writer.write("true ");
            byte[] encryptedPassword = Encryptor.encrypt(user.getPassword());
            for(byte b : encryptedPassword) {
                writer.write(String.valueOf(b) + " ");
            }
            writer.append('\n');
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
