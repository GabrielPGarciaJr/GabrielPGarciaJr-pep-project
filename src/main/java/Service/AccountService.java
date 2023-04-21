package Service;

import Model.Account;
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

    //add user
    public Account addAccount(Account user)
    {
        return accountDAO.addUser(user);        
    }
    //get user
    public Account getAccount(Account user)
    {
        return accountDAO.getUser(user.getUsername(), user.getPassword());
    }
    
}
