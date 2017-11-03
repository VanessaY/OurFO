/**
 * Created by lilliankuhn on 10/26/17.
 */


import javafx.scene.control.*;
import javafx.scene.layout.*;




public class ChooseAndGoController {

    private ComboBox<String> ships = new ComboBox<>();


    private Button goButton(){
        Button go = new Button("GO!");
        go.setOnAction(e -> {

            String ship = ships.getValue();

            //TODO update availability of ship

            Main.setPane(4);
        });

        return go;
    }

    private void initShips(){

        //TODO add query for all available ships

        ships.getItems().addAll("Enterprise", "Atlantis", "Friendship", "Elysium", "Millenium Falcon");
    }

    public VBox buildGoScreen(){
        VBox screen = new VBox();
        screen.setSpacing(10);

        screen.getChildren().addAll(new Label("Choose ship"), ships, goButton());

        return screen;
    }
}
