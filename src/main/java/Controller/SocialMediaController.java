package Controller;


import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.*;

import javax.lang.model.util.ElementScanner6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::getAccountHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.get("/accounts/{accounts_id}/messages", this::getUserMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);        
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        
        return app;
    }

   
    /**
     * Post/Create a new account 
     * @param context
     * @throws JsonProcessingException
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(),Account.class);
        //check if there is an existing account username
        if(accountService.checkAccountExist(user.getUsername()))
        {
            Account newUser = accountService.postAccount(user);
            //if a account was made, and password is grater then 4, and username not blank.
            if(newUser!=null && newUser.getPassword().length() > 4 && newUser.getUsername() != "")
            {
                context.json(mapper.writeValueAsString(newUser));
                context.status(200);
            }else{
                context.status(400);
            }
        }else{
            context.status(400);
        }     
    }
    
    /**
     * Post/Get User Account
     * @param context
     * @throws JsonProcessingException
     */
    private void getAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account login = mapper.readValue(context.body(),Account.class);
        //get account
        Account user = accountService.getAccount(login);
        //check if account exist
        if(user != null)
        {
            context.json(mapper.writeValueAsString(user));
            context.status(200);
        }else{
            context.status(401);
        }
    }
  
    /**
     * Get all messages
     * @param context
     * @throws JsonProcessingException
     */
    private void getAllMessageHandler(Context context) throws JsonProcessingException{
        List<Message> manages = messageService.getAllMessage();
        context.json(manages);
        context.status(200);
    }

    /**
     * Post/Add a new Message
     * @param context
     * @throws JsonProcessingException
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(context.body(),Message.class);
        //check if account user exist
        if(accountService.checkAccountExist(newMessage.getPosted_by()+""))
        {
            Message postMessage = messageService.postMessage(newMessage);
            //if postMessage exist and message is not blank and lessthen 255
            if(postMessage!=null && postMessage.getMessage_text().length() > 0 &&  postMessage.getMessage_text().length() < 255 )
            {
                context.json(mapper.writeValueAsString(postMessage));
                context.status(200);
            }else{
                context.status(400);
            }
        }else{
            context.status(400);
        }        
    }
    
    /**
     * Delete a Message
     * @param context
     * @throws JsonProcessingException
     */  
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        //save the message to be deleted if it exist
        Message message = messageService.getMessage(context.pathParam("{message_id}"));
        //if message does not exist return a empty json
        if(message == null)
            context.json("");
        else 
        {
            //if message exist then delete
            Message deletedmessage = messageService.deleteMessage(message.getMessage_id()+"");
            //check if message was deleted
            if(deletedmessage == null)
                context.json(message);
        }
        context.status(200);        
    }

    /**
     * Get a user's Message
     * @param context
     * @throws JsonProcessingException
     */
    private void getUserMessageHandler(Context context) throws JsonProcessingException{

        List<Message> messages = messageService.getUserMessage(context.pathParam("{accounts_id}"));
        if(messages!= null)
            context.json(messages); 
        else 
            context.json(""); 

        context.status(200);
    }
    /**
     * Get a Message using it's id
     * @param context
     * @throws JsonProcessingException
     */
    private void getMessageHandler(Context context) throws JsonProcessingException{
        Message menages = messageService.getMessage(context.pathParam("{message_id}"));
        if(menages!= null)
            context.json(menages); 
        else 
            context.json(""); 

        context.status(200);
    }
    /**
     * PATCH/update a message using message's id
     * @param context
     * @throws JsonProcessingException
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        //get the new message
        Message newMessage = mapper.readValue(context.body(), Message.class);
        //get the message to be updated
        Message oldMessage = messageService.getMessage(context.pathParam("{message_id}"));
        //check if the old message exist
        if(oldMessage!=null)
        {
            //check if new message is not blank and below 255
            if(newMessage.getMessage_text().length() > 0 && newMessage.getMessage_text().length() < 255)
            {
                //update message
                messageService.updateMessage(newMessage, oldMessage);
                //check if message was updated
                if(messageService.getMessage(context.pathParam("{message_id}")).getMessage_text() == newMessage.getMessage_text())
                {                    
                    context.json(messageService.getMessage(context.pathParam("{message_id}")));
                    context.status(200);

                }else context.status(400);
            }else context.status(400);
        }else context.status(400);
    }
}