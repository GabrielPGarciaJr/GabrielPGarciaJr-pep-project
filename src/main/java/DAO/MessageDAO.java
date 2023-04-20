package DAO;

import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    // adding a message
    public Message addMessage(){return null;}
    //getting all messages
    public List<Message> getAllMessage(){List<Message> massage = new ArrayList<>(); return massage;}
    //getting a message using message id
    public Message getMessage(){return null;}
    //delete a message using message id
    public void deleteMessage(){}
    //update a message using message id
    public void updateMessage(){}
    //getting all user's massage using user id
    public List<Message> getAllUserMessage(){List<Message> massage = new ArrayList<>(); return massage;}
    
}
