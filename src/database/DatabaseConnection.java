package database;

import java.sql.*;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:mydatabase.db";
    private static DatabaseConnection instance = null;

    public DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }


    public void clearSessionData() {
        String sql = "UPDATE SessionData SET value = NULL WHERE key = 'currentUser'";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTables() {
        String sqlBuyer = "CREATE TABLE IF NOT EXISTS Buyer (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	username text NOT NULL UNIQUE,\n"
                + "	email text NOT NULL UNIQUE,\n"
                + "	password text NOT NULL\n"
                + ");";

        String sqlSeller = "CREATE TABLE IF NOT EXISTS Seller (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	username text NOT NULL UNIQUE,\n"
                + "	email text NOT NULL UNIQUE,\n"
                + "	password text NOT NULL\n"
                + ");";

        String sqlProduct = "CREATE TABLE IF NOT EXISTS Product (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	price real NOT NULL,\n"
                + "	quantity integer NOT NULL,\n"
                + "	seller_id integer,\n"
                + "	FOREIGN KEY(seller_id) REFERENCES Seller(id)\n"
                + ");";

        String sqlDanusan = "CREATE TABLE IF NOT EXISTS Danusan (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	price real NOT NULL,\n"
                + "	quantity integer NOT NULL,\n"
                + "	seller_id integer,\n"
                + "	FOREIGN KEY(seller_id) REFERENCES Seller(id)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlBuyer);
            stmt.execute(sqlSeller);
            stmt.execute(sqlProduct);
            stmt.execute(sqlDanusan);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(String username, String password, String role, String email) {
        String sql = "INSERT INTO " + role + "(username, password, email) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}