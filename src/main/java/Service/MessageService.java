package Service;


import Model.Message;

import java.util.*;

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
    //add Message
    public Message addMessage(Message message)
    {
        return messageDAO.addMessage(message); 
    }

    //public void deleteMessage(int id)
    //{ messageDAO.deleteMessage(id);}

      //get all messages
      public List<Message> getAllMessage()
      {
        return messageDAO.getAllMessage();
      }
      //get messages
      public Message getMessage(String id)
      {
        return messageDAO.getMessage(id);
      }
}
