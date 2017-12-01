import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import java.util.*;

public class OurFO {

	public static void main(String[] argv) {

	    //connect to postgresql driver
        try{
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC Driver not found");
			e.printStackTrace();
			return;
		}

		//username and password
        Scanner sc = new Scanner(System.in);
        System.out.print("Postgres Username: ");
		String username = sc.nextLine();
		if (username.equals("")){
			username = "p32003a";
		}
        
        System.out.print("Postgres Password: ");
        String password = sc.nextLine();
        if (password.equals("")){
			password = "changeTHISplease";
		}

		Connection connection= null;

        //connect to database
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://reddwarf.cs.rit.edu:5432/p32003a", username,
					password);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection == null) {
            System.out.println("Failed to make connection!");
            System.exit(0);
        }
	 
		OurFO ourfo = new OurFO();
        ourfo.superUserOptions(connection);

        System.out.println("\nThank you very much for using oUrFO!\n\n");
        printGoodbye();
        ourfo.exit(connection);
    }

    private void exit(Connection c) {
        try {
            c.close();
            System.out.println("Connection closed!");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Could not close connection!");
            e.printStackTrace();
            System.exit(0);
        }

    }
	private void superUserOptions(Connection c){
		System.out.println("\nConnection succeeded! This is a prototype, so you are a superuser." +
						   "In addition, there will not be constraint checking in this version. " +
						   "What would you like to do?");
		System.out.println("1: Add something to the database");
		System.out.println("2: Display something from the database");
        System.out.println("3: Let's go on an adventure!");
		//System.out.println("4: Act as a user");

		System.out.print("\n>>");
		//System.out.println("4: Act as a user");
		System.out.println("Q: Quit");
		System.out.print("\n>> ");

		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();

		switch(option){
			case "1":
				superAdd(c);
				break;
			case "2":
				superDisplay(c);
				break;

            case "3":
                letsRide(c);
                break;

			/*
			case "4":
				actAsUser(c);
				break;
			*/
            case "Q":
                exit(c);
				break;

            default:
				superUserOptions(c);
				break;
		}
	}

	private void superAdd(Connection c){
		System.out.println("\nWhat would you like to add?");
		System.out.println("1: Planet");
		System.out.println("2: Ship");
		System.out.println("3: Ship compatibility");
		System.out.println("4: Ship models");
		System.out.println("5: Species");
		System.out.println("6: User");
		System.out.println("Q: Go back");

        System.out.print("\n>> ");

		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();

		switch(option){
			case "1": //planet
			    //planet name (varchar(20))
				String name = "";
				while (name.equals("")){
					System.out.print("Planet name: ");
					name = sc.nextLine();
				}

				//planet location (integer)
                Integer location = -1;
                while (location <= 0){
                    System.out.print("Planet location: ");
                    try {
                        location = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Location");
                        location = -1;
                    }
                }


				try {
					PreparedStatement stmt = c.prepareStatement("INSERT INTO planet (name, location) " +
                                                "VALUES (?, ?)");
                    stmt.setString(1, name);
                    stmt.setInt(2, location);
					System.out.println(stmt);

					stmt.executeUpdate();
				} catch (SQLException e){
					System.out.println(e);
				}

				break;
			case "2": //ship
				//ship model (int)
                int s_model_id = -1;
                while (s_model_id <= 0){
                    displayShipModels(c);
                    System.out.print("Ship Model ID: ");
                    try {
                        s_model_id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID");
                        s_model_id = -1;
                    }
                }

                //ship dockedat (integer)
                Integer dockedat = -1;
                while (dockedat <= 0){
                    displayStations(c);
                    System.out.print("Ship dockedat (integer): ");

                    try {
                        dockedat = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID");
                        dockedat = -1;
                    }
                }

				try {
                    PreparedStatement stmt = c.prepareStatement("INSERT INTO ship (model, dockedat) " +
                                                "VALUES (?, ?)");
                    stmt.setInt(1, s_model_id);
                    stmt.setInt(2, dockedat);
					System.out.println(stmt);

					stmt.executeUpdate();
				} catch (SQLException e){
					System.out.println(e);
				}
				break;
			case "3": //ship compatibility
                //ship_compatibility ship_id (integer)

                Integer ship_model_id = -1;
                while (ship_model_id <= 0 ){
                    displayShipModels(c);
                    System.out.print("Ship model id: ");
                    try {
                        ship_model_id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid Ship Model ID");
                        ship_model_id = -1;
                    }
                }

                //ship_compatibility species_id (integer)
                Integer species_id = -1;
                while (species_id <= 0){
                    displaySpecies(c);
                    System.out.print("Species id: ");
                    try {
                        species_id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Species ID");
                        species_id = -1;
                    }
                }

                try {
                    PreparedStatement stmt = c.prepareStatement("INSERT INTO ship_compatibility (ship_model_id, species_id) " +
                                    "VALUES (?, ?)");
                    stmt.setInt(1, ship_model_id);
                    stmt.setInt(2, species_id);
                    System.out.println(stmt);

                    stmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "4": //ship model
                //ship_models name (varchar(20))
                String sm_name = "";
                while (sm_name.equals("")){
                    System.out.print("Ship Model name: ");
                    sm_name = sc.nextLine();
                }

                try {
                    PreparedStatement stmt = c.prepareStatement("INSERT INTO ship_models (name) VALUES (?)");
                    stmt.setString(1, sm_name);
                    System.out.println(stmt);
                    
                    stmt.execute();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "5": //Species
			    //species name (varchar(20))
                String s_name = "";
                while (s_name.equals("")){
                    System.out.print("Species name: ");
                    s_name = sc.nextLine();
                }

				//Species homeworld (varchar(20))
				int world = -1;
				while (world <= 0){
                    displayPlanets(c);
					System.out.println("Species homeworld ID: ");
					try {
                        world = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid homeworld ID");
                        world = -1;
                    }
				}

                try {
					PreparedStatement stmt = c.prepareStatement("INSERT INTO species (name, homeworld) " + 
												"VALUES (?, ?)");
                    stmt.setString(1, s_name);
                    stmt.setInt(2, world);
                    System.out.println(stmt);

                    stmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "6": //User
			    //username (varchar(20))
                String u_name = "";
                while (u_name.equals("")){
                    System.out.print("Username: ");
                    u_name = sc.nextLine();
                }

				//Species_id (int)
				int u_id = -1;
				while (u_id <= 0){
                    displaySpecies(c);
                    System.out.print("Species ID: ");
                    try {
                        u_id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid species ID");
                        u_id = -1;
                    }
				}

                try {
					PreparedStatement stmt = c.prepareStatement("INSERT INTO \"User\" (username, species_id) " + 
                                                "VALUES (?,?)");
                    stmt.setString(1, u_name);
                    stmt.setInt(2, u_id);
                    System.out.println(stmt);
                    stmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "Q":
				superUserOptions(c);
				break;
            default:
                superAdd(c);
				break;
		}

		superAdd(c);

	}

	//helper function to display planets
	private void displayPlanets(Connection c){
        try {
            Statement st = c.createStatement();

            String query = "SELECT id, name FROM planet";

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("ID\tName");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //helper function to print ships dockedAt a planet
    private void displayShipsAtPlanet(Connection c, Integer planet_id){
        try {
            Statement st = c.createStatement();

            String query =  "SELECT ship_models.name FROM " +
                    "ship JOIN ship_models ON ship.dockedAt = ship_models.id " +
                    "WHERE ship.dockedAt = " + planet_id;

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("Ship Model");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i)); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //helper function to print stations that belong to a planet
    private void displayStationsAtPlanet(Connection c, Integer planet_id){
        try {
            Statement st = c.createStatement();

            String query = "SELECT id FROM station WHERE planet_id = " + planet_id;

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("Station ID");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + " "); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //helper function to print all the ship models
    private void displayShipModels(Connection c){
        try {
            Statement st = c.createStatement();

            String query = "SELECT id, name FROM ship_models";

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("ID\tModel Name");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void displaySpecies(Connection c){
        try {
            Statement st = c.createStatement();

            String query = "SELECT species.id, planet.name, species.name FROM " +
                            "species JOIN planet ON species.homeworld = planet.id";

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("ID\tHome\tSpecies");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void displayStations(Connection c){
        try {
            Statement st = c.createStatement();

              String query = "SELECT station.id, planet.name FROM station JOIN planet " +
                              "ON station.planet_id = planet.id";
          
              ResultSet rs = st.executeQuery(query);
              ResultSetMetaData rsmd = rs.getMetaData();

              int columnsNumber = rsmd.getColumnCount();


              // Iterate through the data in the result set and display it.

              System.out.println("ID\tPlanet");
              while (rs.next()) {
                  //Print one row
                  for (int i = 1; i <= columnsNumber; i++) {

                      System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                  }

                  System.out.println();//Move to the next line to print the next row.

              }

          } catch (SQLException e) {
              System.out.println(e);
          }
    }
	private void letsRide(Connection c){
	    try {
            boolean flag = true;

            System.out.println("******************");
            displayPlanets(c);
            System.out.println("******************");
            Integer location = -1;
            Integer destination = -1;

            while (flag) {
                System.out.println("Where are you?\nPlease select a planet ID displayed above.");
                System.out.print("\n>> ");
                Scanner sc = new Scanner(System.in);
                location = Integer.parseInt(sc.nextLine());

                Statement st = c.createStatement();
                String queryCheck = "SELECT * FROM planet WHERE id = " + location;
                ResultSet rs = st.executeQuery(queryCheck);

                if (rs.next()) {
                    //IT DOES EXIST
                    flag = false;
                }
                else{
                    System.out.println("That planet doesn't exist...\n");
                }
            }

            flag = true;

            System.out.println("******************");
            displayStationsAtPlanet(c, location);
            System.out.println("******************");

            while(flag) {
                System.out.println("Which station from your location do you want to leave from?");

                System.out.print("\n>> ");
                Scanner sc = new Scanner(System.in);
                Integer location_station = Integer.parseInt(sc.nextLine());

                Statement st = c.createStatement();
                String queryCheck = "SELECT * FROM station WHERE id = " + location_station + " AND planet_id = " + location;
                ResultSet rs = st.executeQuery(queryCheck);

                if (rs.next()) {
                    //IT DOES EXIST
                    flag = false;
                }
            }





            flag = true;

            System.out.println("******************");
            displayPlanets(c);
            System.out.println("******************");

            while (flag) {
                System.out.println("Where do you want to go?\nPlease select the planet ID displayed above.");
                System.out.print("\n>> ");
                Scanner sc = new Scanner(System.in);
                destination = Integer.parseInt(sc.nextLine());

                Statement st = c.createStatement();
                String queryCheck = "SELECT * FROM planet WHERE id = " + destination;
                ResultSet rs = st.executeQuery(queryCheck);

                if(location == destination){
                    System.out.println("You're silly! You're already there!");
                }
                else if (rs.next()) {
                    //IT DOES EXIST
                    flag = false;
                }
            }

            flag = true;

            System.out.println("******************");
            displayStationsAtPlanet(c, destination);
            System.out.println("******************");
            while(flag) {
                System.out.println("Which station from your destination do you want to leave from?");

                System.out.print("\n>> ");
                Scanner sc = new Scanner(System.in);
                Integer destination_station = Integer.parseInt(sc.nextLine());

                Statement st = c.createStatement();
                String queryCheck = "SELECT * FROM station WHERE id = " + destination_station + " AND planet_id = " + destination;
                ResultSet rs = st.executeQuery(queryCheck);

                if (rs.next()) {
                    //IT DOES EXIST
                    flag = false;
                }
            }

            //location and destination are selected. They are the planet IDs.
            System.out.println("******************");
            displayShipsAtPlanet(c, location);
            System.out.println("******************\n");
            System.out.println("Above are the ships available at your location!");

            flag = true;

            while (flag) {
                System.out.println("Which ship do you want?");
                System.out.print("\n>> ");
                Scanner sc = new Scanner(System.in);
                String ship_model = sc.nextLine();

//                Statement st = c.createStatement();
//                String queryCheck =  "CAN YOU FIGURE THIS SHIT OUT?"; //TODO CHECK IF THE SHIP MODEL (ship_model) IS DOCKED AT THE LOCATION (location)
//                ResultSet rs = st.executeQuery(queryCheck);
//
//                if (rs.next()) {
//                    //IT DOES EXIST
//                    flag = false;
//                }
                flag = false;
            }

            //variables we have at this point
            //location
            //location_station
            //destination
            //destination_station
            //ship_model

            //TIME TO TRAVEL
            letsTravelllllll();


        }

        catch (SQLException e) {
            System.out.println(e);
        }



    }




	private void superDisplay(Connection c){

        System.out.println("\nWhat would you like to display?");
        System.out.println("1: Planet");
        System.out.println("2: Ship");
        System.out.println("3: Ship compatibility");
        System.out.println("4: Ship models");
        System.out.println("5: Species");
        System.out.println("6: User");
        System.out.println("7: Stations");
        System.out.println("Q: Go back");

        System.out.print("\n>> ");

        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();

        switch(option) {
            case "1": //planet
                displayPlanets(c);
                break;
            case "2": // ships
                try {

                    Statement st = c.createStatement();

                    String query = "SELECT ship.id, station.id, ship_models.name " +
                                    "FROM ship JOIN ship_models ON " +
                                    "ship.model = ship_models.id " +
                                    "JOIN station ON ship.dockedAt = station.id ";

                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();


                    // Iterate through the data in the result set and display it.

                    System.out.println("ID\tStation\tShip Model");
                    while (rs.next()) {
                        //Print one row
                        for (int i = 1; i <= columnsNumber; i++) {

                            System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                        }

                        System.out.println();//Move to the next line to print the next row.

                }

                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;

            case "3" : // ship compatabilities
                try {
                    Statement st = c.createStatement();

                    //String query = "SELECT ship_model_id, species_id FROM ship_compatability";
                    String query = "SELECT ship_models.name, species.name FROM "+
                                    "ship_compatibility JOIN species ON " +
                                    "ship_compatibility.species_id = species.id " +
                                    "JOIN ship_models ON " +
                                    "ship_compatibility.ship_model_id = ship_models.id";

                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();


                    // Iterate through the data in the result set and display it.

                    System.out.println("Model\tSpecies");
                    while (rs.next()) {
                        //Print one row
                        for (int i = 1; i <= columnsNumber; i++) {

                            System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                        }

                        System.out.println();//Move to the next line to print the next row.

                    }

                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;

            case "4": // ship models
                displayShipModels(c);
                break;

            case "5": // species
                displaySpecies(c);
                break;

            case "6": // user
                try {
                    Statement st = c.createStatement();

                    String query = "SELECT id, username FROM \"User\"";

                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();


                    // Iterate through the data in the result set and display it.

                    System.out.println("ID\tUsername");
                    while (rs.next()) {
                        //Print one row
                        for (int i = 1; i <= columnsNumber; i++) {

                            System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                        }

                        System.out.println();//Move to the next line to print the next row.

                    }

                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            
            case "7": //Stations
                displayStations(c);
                break;
            case "Q": // go back
                superUserOptions(c);
                break;
            default:
                superDisplay(c);
                break;
        }

        superDisplay(c);
	}

	/*
	private void actAsUser(Connection c){
	}
	*/


    private void letsTravelllllll()
    {
        ProgressBar bar = new ProgressBar();

        System.out.println("LETS GOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!");

        bar.update(0, 1000);
        for(int i=0;i<1000;i++) {

            for(int j=0;j<1000000;j++)
                for(int p=0;p<1000000;p++);

            bar.update(i, 1000);
        }
        System.out.println("OMG WE ARE HERE!");
    }

    private static void printGoodbye()
    {
        ArrayList<String> lines = new ArrayList<String>();

        lines.add("             .-\"\"\"\"-.        .-\"\"\"\"-.        .-\"\"\"\"-.");
        lines.add("            /        \\      /        \\      /        \\");
        lines.add("           /_        _\\    /_        _\\    /_        _\\");
        lines.add("          // \\      / \\\\  // \\      / \\\\  // \\      / \\\\");
        lines.add("          |\\__\\    /__/|  |\\__\\    /__/|  |\\__\\    /__/|");
        lines.add("           \\    ||    /    \\    ||    /    \\    ||    /");
        lines.add("            \\        /      \\        /      \\        /");
        lines.add("             \\  __  /        \\  __  /        \\  __  /");
        lines.add("              '.__.'          '.__.'          '.__.'");
        lines.add("               |  |            |  |            |  |");
        lines.add("               |  |            |  |            |  |");
        lines.add("               |  |            |  |            |  |");
        lines.add("              Abraham         Lillian         Vanessa");
        lines.add("");
        lines.add("*********************** TOTALLY NOT ALIENS ***********************");

        try
        {
            for (int i = 0; i < lines.size(); i++) {
                System.out.println(lines.get(i));
                Thread.sleep(250);
            }
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Ascii progress meter. On completion this will reset itself,
 * so it can be reused
 * <br /><br />
 * 100% ################################################## |
 */
class ProgressBar {
    private StringBuilder progress;
    public ProgressBar() {
        init();
    }
    public void update(int done, int total) {
        char[] workchars = {'|', '/', '-', '\\'};
        String format = "\r%3d%% %s %c";

        int percent = (++done * 100) / total;
        int extrachars = (percent / 2) - this.progress.length();

        while (extrachars-- > 0) {
            progress.append('#');
        }

        System.out.printf(format, percent, progress,
                workchars[done % workchars.length]);

        if (done == total) {
            System.out.flush();
            System.out.println();
            init();
        }
    }

    private void init() {
        this.progress = new StringBuilder(60);
    }
}