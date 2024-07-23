package controller;

import commonModule.utils.User;

public class UserContainer {
    private static User authorizeUser;
    public static void putUser(User user){
        UserContainer.authorizeUser = user;
    }
    public static User getUser(){
        return UserContainer.authorizeUser;
    }
    public static boolean isEmpty(){
        if (UserContainer.authorizeUser != null){
            return false;
        }
        return true;
    }
}
