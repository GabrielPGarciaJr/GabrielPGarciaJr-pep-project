package Controller;


import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.*;

//import java.util.List;
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
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        //app.delete("/messages/{message_id}", this::deleteMessageHandler);
        //app.post("/messages", this::updateMessageHandler);//<- need to check if post or put?
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(),Account.class);
        Account newUser = accountService.addAccount(user);
        
        if(newUser!=null && newUser.getPassword().length() > 4 && newUser.getUsername() != "")
        {
            context.json(mapper.writeValueAsString(newUser));
            context.status(200);
        }else{
            context.status(400);
        }
    }
    
    private void getAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(),Account.class);
        Account ifUser = accountService.getAccount(user);
        if(ifUser != null)
        {
            context.json(mapper.writeValueAsString(ifUser));
            context.status(200);
        }else{
            context.status(401);
        }
    }
    
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);
        Message addMessage = messageService.addMessage(message);
        
         if(addMessage!=null && addMessage.getMessage_text().length() > 0 &&  addMessage.getMessage_text().length() < 255 )
        {
            context.json(mapper.writeValueAsString(addMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }
    
    private void getAllMessageHandler(Context context) throws JsonProcessingException{
        List<Message> manages = messageService.getAllMessage();
        context.json(manages);
        context.status(200);
    }
    
    private void getMessageHandler(Context context) throws JsonProcessingException{

        Message manages = messageService.getMessage(context.pathParam("{message_id}"));
        if(manages!= null)
        {
            context.json(manages); 
            context.status(200);
        }else 
        context.status(200);
    }
    /*    
    
    private void updateMessageHandler(Context context) {
        context.json("sample text");
    }
    private void deleteMessageHandler(Context context) {
        
    }
    */
}