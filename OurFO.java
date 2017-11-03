import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
		System.out.println("Connecting to Postgres Server...");
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
		System.out.println("\nConnection succeeded! This is a prototype, so you are a superuser. What would you like to do?");
		System.out.println("1: Add something to the database");
		System.out.println("2: Display something from the database");
		System.out.println("3: Act as a user");

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
			case "3":
				actAsUser(c);
				break;
			default:
				superUserOptions(c);
				break;
		}
	}

	private void superAdd(Connection c){
		System.out.println("What would you like to do?");
		System.out.println("1: Add planet");
		System.out.println("2: Add ship");
		System.out.println("3: Add ship compatibility");
		System.out.println("4: Add ship models");
		System.out.println("5: Add species");
		System.out.println("6: Add user");
		System.out.println("7: Go back");
		
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();

		switch(option){
			case "1":
				String name = "";
				while (name.equals("")){
					System.out.print("Planet name: ");
					name = sc.nextLine();
				}
				
				try {
					Statement s = c.createStatement();
					String stmt = String.format("INSERT INTO planet (name) VALUES (\'%s\')", name);
					System.out.println(stmt);

					PreparedStatement p = c.prepareStatement(stmt);
					p.executeUpdate();
				} catch (SQLException e){
					System.out.println(e);
				}

				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			case "6":
				break;
			case "7":
				superUserOptions(c);
				break:
			default:
				break;
		}
		superAdd(c);

	}

	private void superDisplay(Connection c){
		System.out.println("What table would you like to display?");
	}
	private void actAsUser(Connection c){
		System.out.println("Hello! This section is currently under progress, so there isn't much to do here.")
		superUserOptions(c);
	}
}

