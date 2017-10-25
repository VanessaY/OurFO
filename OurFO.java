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
        System.out.print("Postgres Username (Blank for \"postgres\":");
        String username = sc.nextLine();
        if (username.equals("")){
            username = "postgres";
        }
        System.out.print("Postgres Username (Blank for \"CSCI320\":");
        String password = sc.nextLine();
        if (password.equals("")){
            password = "CSCI320";
        }


		Connection connection= null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/ourfo", username,
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
        



	}

}