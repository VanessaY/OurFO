import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
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

        ourfo.exit(connection);
    }

    private void exit(Connection c) {
        try {
            c.close();
            System.out.println("Connection closed!");
            System.out.println("\nThank very much for using oUrFO!\n\n");
            printGoodbye();
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Could not close connection!");
            e.printStackTrace();
            System.exit(0);
        }

    }

	private void superUserOptions(Connection c){


		System.out.println("\nConnection succeeded! You are a superuser. " +
						   "What would you like to do?");
		System.out.println("1: Add something to the database");
		System.out.println("2: Display something from the database");
        System.out.println("3: Let's go on an adventure!");
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
		System.out.println("3: Ship models");
		System.out.println("4: Species");
        System.out.println("5: User");
        System.out.println("6: Station");
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
			
			case "3": //ship model
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
                    
                    stmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "4": //Species
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
			case "5": //User
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
            
            case "6":
                //Planet id
                int planet_id = -1;
                while (planet_id <= 0){
                    displayPlanets(c);
                    System.out.println("Planet ID: ");
                    try {
                        planet_id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid Planet ID");
                        planet_id = -1;
                    }
                }

                //location on planet
                location = -1;
                while (location <= 0){
                    System.out.println("Location on planet: ");
                    try {
                        location = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid Location");
                        planet_id = -1;
                    }
                }

                try {
					PreparedStatement stmt = c.prepareStatement("INSERT INTO station (location, planet_id) " + 
                                                "VALUES (?,?)");
                    stmt.setInt(1, location);
                    stmt.setInt(2, planet_id);
                    System.out.println(stmt);
                    stmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }

                break;    

            case "Q":
            case "q":
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
    private void displayShipsAtStation(Connection c, int station_id){
        try {
            Statement st = c.createStatement();

            String query =  "SELECT ship.id, " + 
                                   "ship_models.name " +
                                   //"AVG(ship_review.rating) " + 
                            "FROM ship "+
                                  "JOIN ship_models ON ship.model = ship_models.id " +
                                  //"JOIN ship_review ON ship_review.written_for = ship.id " +
                            "WHERE ship.dockedAt = " + station_id;  
                            //"GROUP BY ship_models.name, ship.id"; 

            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();


            // Iterate through the data in the result set and display it.

            System.out.println("ID\tModel");
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + "\t"); //Print one element of a row

                }
                //System.out.printf("%.2f",rs.getFloat(columnsNumber));

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

    private int numStationsOnPlanet(Connection c, int planet_id){
        try {
            Statement st = c.createStatement();
            String query = "SELECT COUNT(*) FROM station WHERE planet_id = " + planet_id;

            ResultSet rs = st.executeQuery(query);
            
            int count = 0;
            while (rs.next()){
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e){
            System.out.println(e);
        }

        return -1;
    }

    private int numShipsInStation(Connection c, int station_id){
        try {
            Statement st = c.createStatement();
            String query = "SELECT COUNT(*) FROM ship WHERE dockedAt = " + station_id;

            ResultSet rs = st.executeQuery(query);
            
            int count = 0;
            while (rs.next()){
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException e){
            System.out.println(e);
        }

        return -1;
    }

	private void letsRide(Connection c){
	    try {
            boolean flag = true;

            int location = -1;
            int destination = -1;
            int location_station = -1;
            int destination_station = -1;
            int ship = -1;
            Scanner sc = new Scanner(System.in);

            while (flag) {
                ResultSet rs = null;

                while (location <= 0){
                    displayPlanets(c);
                    System.out.println("Where are you?\nPlease select a planet ID that is displayed above.");
                    System.out.print("\n>> ");
                
                    try {
                        location = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid location");
                        location = -1;
                    }

                    PreparedStatement stmt = c.prepareStatement("SELECT * FROM planet WHERE id = ?");
                    stmt.setInt(1, location);

                    rs = stmt.executeQuery();

                    if (rs.next()) {
                    //IT DOES EXIST
                        flag = false;
                    }
                    else {
                        System.out.println("That planet doesn't exist...    \n");
                        rs = null;
                        location = -1;
                    }

                }

            }

            flag = true;

            while(flag) {

                location_station = -1;
                
                if (numStationsOnPlanet(c, location) < 1) {
                    System.out.println("Sorry, there aren't any stations on that planet! Let's try again.");
                    letsRide(c);
                    return;
                }

                while (location_station <= 0){
                    displayStationsAtPlanet(c, location);
                    System.out.println("Which station from your location do you want to leave from?");
                    System.out.print("\n>> ");

                    try {
                        location_station = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid location");
                        location_station = -1;
                    }

                    PreparedStatement stmt = c.prepareStatement("SELECT * FROM station WHERE id = ? AND planet_id = ?");
                    stmt.setInt(1, location_station);
                    stmt.setInt(2, location);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        //IT DOES EXIST
                        flag = false;
                    } 
                    else {
                        System.out.println("That station doesn't exist...\n");
                        rs = null;
                    }
                }

            }

            flag = true;
            sc = new Scanner(System.in);

            while (flag) {
                ResultSet rs = null;
                while (destination <= 0){
                    displayPlanets(c);
                    System.out.println("Where do you want to go?\nPlease select the planet ID displayed above.");
                    System.out.print("\n>> ");
                
                    try {
                        destination = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid location");
                        destination = -1;
                    }

                    PreparedStatement stmt = c.prepareStatement("SELECT * FROM planet WHERE id = ?");
                    stmt.setInt(1, destination);
                    rs = stmt.executeQuery();

                    if(location == destination){
                        System.out.println("You're silly! You're already here!");
                        destination = -1;
                    }
                    else if (rs.next()) {
                        //IT DOES EXIST
                        flag = false;
                    } else {
                        System.out.println("That planet doesn't exist...\n");
                        rs = null;
                        destination = -1;      
                    }
                }
            }

            flag = true;

            while(flag) {
                if (numStationsOnPlanet(c, destination) < 1){
                    System.out.println("Sorry, there aren't any stations on that planet! Let's try again.");
                    letsRide(c);
                    return;
                }

                destination_station = -1;

                while (destination_station <= 0){
                    displayStationsAtPlanet(c, destination);
                    System.out.println("Which station from your destination do you want to go to?");

                    System.out.print("\n>> ");
                    sc = new Scanner(System.in);

                    try {
                        destination_station = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid location");
                    }
                }
            
                PreparedStatement stmt = c.prepareStatement("SELECT * FROM station WHERE id = ? AND planet_id = ?");
                stmt.setInt(1, destination_station);
                stmt.setInt(2, destination);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    //IT DOES EXIST
                    flag = false;
                } else {
                    System.out.println("That station doesn't exist...\n");
                    rs = null;
                }

            }


            flag = true;

            while (flag) {
                if (numShipsInStation(c, location_station) < 1){
                    System.out.println("Sorry, there aren't any ships in this station. Let's try again!");
                    letsRide(c);
                    return;
                }
                
                ship = -1;

                while (ship <= 0){
                    displayShipsAtStation(c, location_station);
                    System.out.println("Above are the ships available at your location!");
                
                    System.out.println("Which ship do you want?");
                    System.out.print("\n>> ");
                    sc = new Scanner(System.in);
                    
                        
                    try {
                        ship = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e){
                        System.out.println("Invalid ship ID");
                    }

                    PreparedStatement stmt = c.prepareStatement("SELECT * FROM ship WHERE ship.id = ? AND ship.dockedAt = ?");
                    stmt.setInt(1, ship);
                    stmt.setInt(2, location_station);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()){
                        flag = false;
                    } else {
                        System.out.println("This ship isn't here...");
                        rs = null;
                    }
                }

            }


            //Undock
            PreparedStatement stmt = c.prepareStatement("UPDATE ship SET dockedAt = null WHERE ship.id = ?");
            stmt.setInt(1, ship);
            stmt.executeUpdate();

            letsTravelllllll();

            //Dock
            stmt = c.prepareStatement("UPDATE ship SET dockedAt = ? WHERE ship.id = ?");
            stmt.setInt(1, destination_station);
            stmt.setInt(2, ship);
            stmt.executeUpdate();
        }

        catch (SQLException e) {
            System.out.println(e);
        }

        superUserOptions(c);
    }




	private void superDisplay(Connection c){

        System.out.println("\nWhat would you like to display?");
        System.out.println("1: Planet");
        System.out.println("2: Ship");
        System.out.println("3: Ship models");
        System.out.println("4: Species");
        System.out.println("5: User");
        System.out.println("6: Stations");
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


            case "3": // ship models
                displayShipModels(c);
                break;

            case "4": // species
                displaySpecies(c);
                break;

            case "5": // user
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
            
            case "6": //Stations
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
        System.out.println("We're here!");
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