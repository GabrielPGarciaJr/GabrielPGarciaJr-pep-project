package Service;

import Model.Account;
//import javafx.beans.binding.ListExpression;

//import java.util.*;

//import javax.sound.sampled.LineUnavailableException;

import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    
    //no args constructor
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    //constructor
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Post/add a account
     * @param Account user
     * @return Account user
     */
    public Account postAccount(Account user)
    {
        return accountDAO.postAccount(user);        
    }

    /**
     * Get account
     * @param Account user for username and password
     * @return Account we are trying to retrieve, if no account found returns null
     */
    public Account getAccount(Account user)
    {
        return accountDAO.getUserAccount(user.getUsername(), user.getPassword());
    }

    /**
     * Check if a account exist or not
     * @param username
     * @return true if a account does not exist, else false
     */
    public Boolean checkAccountExist(String username)
    {
        return !accountDAO.checkAccountExist(username);
    }
    
}
