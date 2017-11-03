import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class OurFO {

	public static void main(String[] argv) {

        try{
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC Driver not found");
			e.printStackTrace();
			return;
		}

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
	}		


	private void superUserOptions(Connection c){
		System.out.println("\nConnection succeeded! This is a prototype, so you are a superuser." +
						   "In addition, there will not be constraint checking in this version. " +
						   "What would you like to do?");
		System.out.println("1: Add something to the database");
		System.out.println("2: Display something from the database");
		//System.out.println("3: Act as a user");

		System.out.print("\n>>");
		
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();

		switch(option){
			case "1":
				superAdd(c);
				break;
			case "2":
				superDisplay(c);
				break;
			/*
			case "3":
				actAsUser(c);
				break;
			*/
			default:
				superUserOptions(c);
				break;
		}
	}

	private void superAdd(Connection c){
		System.out.println("What would you like to add?");
		System.out.println("1: Planet");
		System.out.println("2: Ship");
		System.out.println("3: Ship compatibility");
		System.out.println("4: Ship models");
		System.out.println("5: Species");
		System.out.println("6: User");
		System.out.println("7: Go back");

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
                    location = Integer.parseInt(sc.nextLine());
                }

				try {
					Statement s = c.createStatement();
					String stmt = String.format("INSERT INTO planet (name, location) " + 
												"VALUES (\'%s\', \'%s\')", 
												name, location);
					System.out.println(stmt);

					PreparedStatement p = c.prepareStatement(stmt);
					p.executeUpdate();
				} catch (SQLException e){
					System.out.println(e);
				}

				break;
			case "2": //ship
				//ship model (int)
                int s_model_id = -1;
                while (s_model_id <= 0){
                    System.out.print("Ship Model ID: ");
                    s_model_id = Integer.parseInt(sc.nextLine());
                }

                //ship dockedat (integer)
                Integer dockedat = -1;
                while (dockedat <= 0){
                    System.out.print("Ship dockedat (integer): ");
                    dockedat = Integer.parseInt(sc.nextLine());
                }

				try {
					Statement s = c.createStatement();
					String stmt = String.format("INSERT INTO ship (model, dockedat) VALUES (\'%s\', \'%d\')", s_model_id, dockedat);
					System.out.println(stmt);

					PreparedStatement p = c.prepareStatement(stmt);
					p.executeUpdate();
				} catch (SQLException e){
					System.out.println(e);
				}
				break;
			case "3": //ship compatibility
                //ship_compatibility ship_id (integer)
                Integer ship_model_id = -1;
                while (ship_model_id <= 0 ){
                    System.out.print("Ship id: ");
                    ship_model_id = Integer.parseInt(sc.nextLine());
                }

                //ship_compatibility species_id (integer)
                Integer species_id = -1;
                while (species_id <= 0){
                    System.out.print("Species id: ");
                    species_id = Integer.parseInt(sc.nextLine());
                }

                try {
                    Statement s = c.createStatement();
                    String stmt = String.format("INSERT INTO ship (ship_id, species_id) VALUES (\'%d\', \'%d\')", ship_model_id, species_id);
                    System.out.println(stmt);

                    PreparedStatement p = c.prepareStatement(stmt);
                    p.executeUpdate();
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
                    Statement s = c.createStatement();
                    String stmt = String.format("INSERT INTO ship_models (name) VALUES (\'%s\', \'%d\')", sm_name);
                    System.out.println(stmt);

                    PreparedStatement p = c.prepareStatement(stmt);
                    p.executeUpdate();
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
				String world = "";
				while (world.equals("")){
					System.out.println("Species homeworld: ");
					world = sc.nextLine();
				}

                try {
                    Statement s = c.createStatement();
					String stmt = String.format("INSERT INTO species (name, homeworld) " + 
												"VALUES (\'%s\', \'%s\')", 
												s_name, world);
                    System.out.println(stmt);

                    PreparedStatement p = c.prepareStatement(stmt);
                    p.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "6": //User
			    //username (varchar(20))
                String u_name = "";
                while (u_name.equals("")){
                    System.out.print("Ship Model name: ");
                    name = sc.nextLine();
                }

				//Species_id (int)
				int u_id = -1;
				while (u_id <= 0){
					System.out.print("Ship Model ID: ");
					u_id = Integer.parseInt(sc.nextLine());
				}

                try {
                    Statement s = c.createStatement();
					String stmt = String.format("INSERT INTO ship_models (name, species_id) " + 
												"VALUES (\'%s\', \'%d\')", 
												u_name, u_id);
                    System.out.println(stmt);

                    PreparedStatement p = c.prepareStatement(stmt);
                    p.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e);
                }
				break;
			case "7":
				superUserOptions(c);
				break;
			default:
				break;
		}

		superAdd(c);

	}

	private void superDisplay(Connection c){

        System.out.println("What would you like to display?");
        System.out.println("1: Planet");
        System.out.println("2: Ship");
        System.out.println("3: Ship compatibility");
        System.out.println("4: Ship models");
        System.out.println("5: Species");
        System.out.println("6: User");
        System.out.println("7: Go back");

        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();

        switch(option) {
            case "1": //planet
                try {
                    Statement st = c.createStatement();

                    String query = "SELECT id, name FROM planet";

                    ResultSet rs = st.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();


                    // Iterate through the data in the result set and display it.

                    System.out.println("ID Name");
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
	}

	/*
	private void actAsUser(Connection c){
	}
	*/
}

