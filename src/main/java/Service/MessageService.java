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
    /**
     * Post/Add a message
     * @param Message message
     * @return Message message
     */
    public Message postMessage(Message message)
    {
        return messageDAO.postMessage(message); 
    }

    /**
     * Get all messages
     * @return a lists of Messages
     */
    public List<Message> getAllMessage()
    {
      return messageDAO.getAllMessage();
    }
    
    /**
     * Get a Message using message_id 
     * @param String id = message_id
     * @return a Message if it exist else null
     */
    public Message getMessage(String id)
    {
      return messageDAO.getMessage(id);
    }
    
    /**
     * Get a Message using account_id
     * @param String id = account_id
     * @return a Message if it exist else null
     */
    public List<Message> getUserMessage(String id)
    {
      return messageDAO.getUserMessage(id);
    }
    
    /**
     * Delete a message
     * @param String id = message_id
     * @return a null if Message does not exist
     */
    public Message deleteMessage(String id)
    {
      return messageDAO.deleteMessage(id);
    }
    
    /**
     * PATCH/Update a message using message_id
     * @param String text to be updated 
     * @param String id= message_id
     */
    public void updateMessage(Message newMessage, Message oldMessage)
    {
      messageDAO.updateMessage(newMessage, oldMessage);
    }
}
