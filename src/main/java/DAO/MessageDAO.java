package DAO;

import Model.Message;

import java.sql.*;
import Util.ConnectionUtil;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
///import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    // adding a message
    public Message addMessage(Message message)
    { 
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());            
            
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
            {
                int generated_Message_id = (int) resultSet.getLong(1);
                return new Message(generated_Message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
 
    //getting all messages
    public List<Message> getAllMessage()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>(); 
        try{            
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message message = new Message
                (
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return messages;
    }
    
    //getting a message using message id
    public Message getMessage(String id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{            
            String sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message message = new Message
                (
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                return message;
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }

    /* 
    
    
    //update a message using message id
    public void updateMessage(){}
    //getting all user's massage using user id
    public List<Message> getAllUserMessage(){List<Message> massage = new ArrayList<>(); return massage;}

       //delete a message using message id
    public void deleteMessage(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message  WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);           
            
            preparedStatement.execute();
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    */
}
