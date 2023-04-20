package Controller;


import Model.Account;
import Service.AccountService;
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
    public SocialMediaController()
    {
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        //app.get("/login", this::getAccountHandler);
        //app.post("/messages", this::postMessageHandler);
        //app.get("/messages", this::getAllMessageHandler);
        //app.get("/messages/{message_id}", this::getMessageHandler);
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
        System.out.println("in newUser act 0");
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(context.body(),Account.class);
        Account newUser = accountService.addAccount(user);
        
        System.out.println("in newUser act 1");
        if(newUser!=null && newUser.getPassword().length() > 4 && newUser.getUsername() != "")
        {
            System.out.println("in newUser act 2");
            context.json(mapper.writeValueAsString(newUser));
            context.status(200);
        }else{
            
            System.out.println("in newUser act 3");
            context.status(400);
        }
    }
    /* 
    private void getAccountHandler(Context context) throws JsonMappingException, JsonProcessingException {
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
    private void postMessageHandler(Context context) {
        context.json("sample text");
    }
    private void getAllMessageHandler(Context context) {
        context.json("sample text");
    }
    private void getMessageHandler(Context context) {
        context.json("sample text");
    }
    private void deleteMessageHandler(Context context) {
        context.json("sample text");
    }
    private void updateMessageHandler(Context context) {
        context.json("sample text");
    }
    */
}