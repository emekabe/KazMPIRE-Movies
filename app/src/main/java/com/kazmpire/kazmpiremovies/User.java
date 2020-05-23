package com.kazmpire.kazmpiremovies;

import java.util.*;

public class User {

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    private List <String> interests;

    private boolean registered = false;

    private boolean fullyRegistered = false;

    private boolean loggedIn = false;

    public User(){

    }

    public User(String userID, String firstName, String lastName, String username, String password){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        registered = true;
    }

    public User(String userID, String firstName, String lastName, String username, String password, ArrayList <String> interests){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        this.interests = interests;

        registered = true;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public boolean isFullyRegistered() {
        return fullyRegistered;
    }

    public void setFullyRegistered(boolean fullyRegistered) {
        this.fullyRegistered = fullyRegistered;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
