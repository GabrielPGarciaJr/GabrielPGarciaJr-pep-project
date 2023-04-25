package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

public class AccountDAO {
    /**
     * Post/Add Account
     * @param Account user
     * @return Account 
     */
    public Account postAccount(Account user)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "INSERT INTO Account (username, password) values (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            
            
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
            {
                int generated_account_id = (int) resultSet.getLong(1);
                return new Account(generated_account_id, user.getUsername(), user.getPassword());
            }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;    
    }
    /**
     * Get the user account 
     * @param username
     * @param password
     * @return A account
     */
    public Account getUserAccount(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{    
            //get account only if username and password are found 
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                
                Account user = new Account
                (
                    resultSet.getInt("account_id"), 
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
                return user;
            }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;    
    }

    /**
     * Check if a user exist 
     * @param username
     * @return boolean if account exist or not.
     */
    public Boolean checkAccountExist(String username)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{    
            String sql = "SELECT * FROM Account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                
                Account user = new Account
                (
                    resultSet.getInt("account_id"), 
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
                if(user!=null)
                    return true;
            }
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;  
    }
     
}
