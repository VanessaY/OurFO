

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    static AnchorPane root;
    static List<VBox> screens = new ArrayList<>();
    private SignInController SI = new SignInController();
    private RegisterController Reg = new RegisterController();
    private ChooseAndGoController Choose = new ChooseAndGoController();
    private Location_DestinationController LocatDest = new Location_DestinationController();
    private ReviewController Review = new ReviewController();
    private static int currInd;


    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            root = new AnchorPane();

            screens.add(SI.buildSignInScreen());              //0
            screens.add(Reg.buildRegisterScreen());           //1
            screens.add(LocatDest.buildLocationScreen());     //2
            screens.add(Choose.buildGoScreen());              //3
            screens.add(Review.buildReviewScreen());          //4

            root.getChildren().add(screens.get(0));



        }
        catch (Exception e){
            e.printStackTrace();
        }
        primaryStage.setTitle("oUrFO");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void setPane(int ind){
        root.getChildren().remove(screens.get(currInd));
        root.getChildren().add(screens.get(ind));
        currInd = ind;
    }

//    @Override
//    public void update(Observable o, Object arg) {
//
//    }


//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("oUrFO");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }


    public static void main(String[] args) {

        Application.launch(args);
    }
}
