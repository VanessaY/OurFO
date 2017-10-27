/**
 * Created by lilliankuhn on 10/26/17.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterController {


    private TextField username = new TextField();
    private TextField password = new TextField();
    private TextField home = new TextField();
    private ComboBox<String> species = new ComboBox<>();

    private Button registerButton(){
        Button register = new Button("Register");
        register.setOnAction(e -> {
            try {

                String user = username.getText();
                String planet = home.getText();
                String type = species.getValue();

                //TODO add user to DB

                Main.setPane(2);
            }
            catch(Exception exc){
                    System.out.println(exc.getMessage());
                }

        });
        return register;
    }

    private void initSpecies(){

        //TODO add query for all different species in DB

        species.getItems().addAll("Martian", "Jupitian", "Mercurtian", "Plutian", "Venutian", "Earthling",
                "Saturtian", "Uranutian", "Neptutian");
    }

    public VBox buildRegisterScreen(){
        VBox screen = new VBox();
        screen.setSpacing(10);
        GridPane userInfo = new GridPane();

        initSpecies();

        userInfo.add(new Label("Username: "), 0, 0);
        userInfo.add(username, 1, 0);
        userInfo.add(new Label("Password: "), 0, 1);
        userInfo.add(password, 1, 1);
        userInfo.add(new Label("Species: "), 0,2);
        userInfo.add(species,1,2);

        screen.getChildren().addAll(userInfo, registerButton());

        return screen;

    }
}
