package DAO;

import Model.Message;

import java.sql.*;
import Util.ConnectionUtil;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
///import java.sql.SQLException;
import net.bytebuddy.asm.Advice.OffsetMapping.ForStackManipulation.OfDefaultValue;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    /**
     * Post/add a message
     * @param Message message
     * @return A Message
     */
    public Message postMessage(Message message)
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
 
    /**
     * Get a list of all messages
     * @return List of Messages
     */
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
    

    /**
     * Get a message using message_id
     * @param String id = message_id
     * @return a message
     */
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
    /**
     * Get a user's message using account_id
     * @param String id = account_id
     * @return List of All Message's a Account has
     */
    public List<Message> getUserMessage(String id)
    {
        Connection connection = ConnectionUtil.getConnection();        
        List<Message> messages = new ArrayList<>(); 
        try{            
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";
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
                messages.add(message);
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return messages;
    }


    /**
     * Delete a message useing message_id
     * @param String id = message_id
     * @return the deleted message or null if message was not found
     */
    public Message deleteMessage(String id)
    {        
        Connection connection = ConnectionUtil.getConnection();
        try{
            
            String sql = "DELETE FROM Message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);    
            preparedStatement.setString(1, id);
            
            preparedStatement.execute();
            return getMessage(id);

        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //update a message using message id
    /**
     * PATCH/update a message using message_id
     * @param String text to replace old text
     * @param String id = message_id
     */
    public void updateMessage(Message newMessage, Message oldMessage)
    {
        Connection connection = ConnectionUtil.getConnection();         
        try{            
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, newMessage.getMessage_text());
            preparedStatement.setInt(2, oldMessage.getMessage_id());

            preparedStatement.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }
  
}
