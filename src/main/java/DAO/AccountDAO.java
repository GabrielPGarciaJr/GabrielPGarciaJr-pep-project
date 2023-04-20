package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    //adding a user
    public Account addUser(Account user)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{

            System.out.println("in in Sql act 0");
            String sql = "INSERT INTO Account (username, password) values (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            
            System.out.println("in in Sql act 1");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            
            System.out.println("in in Sql act 2");
            
            preparedStatement.executeUpdate();

            System.out.println("in in Sql act 3");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            
            System.out.println("in in Sql act 4");
            if(resultSet.next())
            {
                
            System.out.println("in in Sql act 5");
                int generated_account_id = (int) resultSet.getLong(1);
                
            System.out.println("in in Sql act 6");
                return new Account(generated_account_id, user.getUsername(), user.getPassword());
            }
        }catch(SQLException e)
        {
            
            System.out.println("in in Sql act 7");
            System.out.println(e.getMessage());
        }
        
        System.out.println("in in Sql act 8");
        return null;    
    }
    //get a user
    public Account getUser(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{    
            String sql = "SELECT * FROM Account WHERE username = ? && password = ?";
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
     //get all user
     public List<Account> getUsers()
     {
         Connection connection = ConnectionUtil.getConnection();
         List <Account> account = new ArrayList<>();
         try{    
             String sql = "SELECT * FROM Account";
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
 
             ResultSet resultSet = preparedStatement.executeQuery();
             while(resultSet.next())
             {
                 Account user = new Account
                 (
                     resultSet.getInt("account_id"), 
                     resultSet.getString("username"),
                     resultSet.getString("password")
                 );
                 account.add(user);
             }
         }catch(SQLException e)
         {
             System.out.println(e.getMessage());
         }
         return account;    
     }
}
