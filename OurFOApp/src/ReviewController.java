/**
 * Created by lilliankuhn on 10/26/17.
 */


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ReviewController {

    private TextArea review = new TextArea();
    private ComboBox<String> stars = new ComboBox();


    private Button arriveButton(){
        Button arrive = new Button("Arrived");

        arrive.setOnAction(e -> {

            //TODO update availability of ship

        });

        return arrive;
    }


    private Button rateButton(){
        Button rate = new Button("Rate your ship");

        rate.setOnAction(e -> {
            try{
                //TODO update avg #stars for ship being rated

                int rating = getStars(stars.getValue());


            }
            catch(Exception exc){
                System.out.println(exc.getMessage());
            }
        });

        return rate;
    }

    private void initStars(){
        stars.getItems().addAll("5 --  Out of this world",
                                        "4 -- Stellar",
                                        "3 -- ...ok",
                                        "2 -- Eh",
                                        "1 -- Gross");
    }

    private int getStars(String mssg){

        switch (mssg){
            case "5 --  Out of this world" : return 5;
            case "4 -- Stellar" : return 4;
            case "3 -- ...ok" : return 3;
            case "2 -- Eh" : return 2;
            case "1 -- Gross" : return 1;

            default: return -1;
        }

    }

    public VBox buildReviewScreen(){
        VBox screen = new VBox();
        screen.setSpacing(10);
        screen.setPadding(new Insets(10));

        initStars();

        screen.getChildren().addAll(arriveButton(), stars, rateButton());


        return screen;
    }

}
