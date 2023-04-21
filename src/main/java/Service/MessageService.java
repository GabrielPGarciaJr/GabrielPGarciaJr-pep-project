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
      public List<Message> getUserMessage(String id)
      {
        return messageDAO.getUserMessage(id);
      }
      public Message deleteMessage(String id)
      {
        return messageDAO.deleteMessage(id);
      }
      public Message updateMessage(String text, String id)
      {
        return messageDAO.updateMessage(text, id);
      }
}
