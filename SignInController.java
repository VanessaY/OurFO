

import javafx.scene.layout.*;
import javafx.scene.control.*;


public class SignInController {

    private TextField username = new TextField();
    private TextField password = new TextField();

    private Button SignInButton(){
        Button signIn = new Button("Sign In");
        signIn.setOnAction(e -> {
            try{
                String uname = username.getText();
                String pword = password.getText();

                //TODO use uname and pword to access user's data

                Main.setPane(2);
            }
            catch(Exception exc){
                System.out.println(exc.getMessage());
            }

        });
        return signIn;
    }

    private Button RegisterButton(){
        Button register = new Button("Register");
        register.setOnAction(e -> {

            Main.setPane(1);

        });
        return register;
    }

    public VBox buildSignInScreen(){

        VBox screen = new VBox();
        screen.setSpacing(10);

        GridPane userInfo = new GridPane();

        userInfo.add(new Label("Username: "), 0, 0);
        userInfo.add(username, 1, 0);
        userInfo.add(new Label("Password: "), 0, 1);
        userInfo.add(password, 1, 1);

        screen.getChildren().addAll( userInfo, SignInButton(), RegisterButton());

        return screen;

    }

}
