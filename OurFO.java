import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
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
		ourfo.superUserOptions();
	}		


	private void superUserOptions(){
		System.out.println("\nConnection succeeded! This is a prototype, so you are a superuser. What would you like to do?");
		System.out.println("A: Add something to the database");
		System.out.println("D: Display something from the database");
		System.out.println("U: Act as a user");

		System.out.print("\n>>");
		
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();
	}
}

