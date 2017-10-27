/**
 * Created by lilliankuhn on 10/26/17.
 */


import javafx.scene.control.*;
import javafx.scene.layout.*;



public class Location_DestinationController {

    // maybe these are textfields instead and we find nearest ships?
    private ComboBox<String> location = new ComboBox<>();
    private ComboBox<String> destination = new ComboBox<>();

    private Button findShip(){
        Button find = new Button("Find Ship");

        find.setOnAction(e -> {
            try{

                String locat = location.getValue();
                String dest = destination.getValue();

                //TODO add query to find available ships

                Main.setPane(3);
            }
            catch(Exception exc){
                System.out.println(exc.getMessage());
            }
        });

        return find;
    }

    private void initLocation(){

        //TODO add query to populate possible locations

        location.getItems().addAll("Mercury","Venus", "Earth", "Mars", "Jupiter", "Saturn",
                                                "Uranus", "Neptune","Pluto");
    }

    private void initDestination(){

        //TODO add query to populate possible locations

        location.getItems().addAll("Mercury","Venus", "Earth", "Mars", "Jupiter", "Saturn",
                "Uranus", "Neptune","Pluto");
    }


    public VBox buildLocationScreen(){
        VBox screen = new VBox();
        screen.setSpacing(10);

        initLocation();
        initDestination();

        screen.getChildren().addAll(new Label("Location: "), location,
                                    new Label("Destination: "), destination,
                                    findShip());

        return screen;
    }
}
