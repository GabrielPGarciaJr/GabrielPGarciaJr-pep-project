package Service;


import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    
    //no args constructor
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    //constructor
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    
}
