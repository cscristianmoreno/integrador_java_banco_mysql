package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private Statement sql;
    protected Connection connection;

    public DBConnection() {
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            final String url = "127.0.0.1:3306";
            final String user = "root";
            // final String password = "";
            final String database = "banco";


            
            String urlConnect = String.format("jdbc:mysql://%s/%s?user=%s", url, database, user);

            this.connection = DriverManager.getConnection(urlConnect);
            
            this.sql = connection.createStatement();
            createTables();
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.printf("Ocurrió un error: %s", e.getMessage());
        }   
    }

    public void createTables() {

        String query;

        try {
            query = 
                "CREATE TABLE IF NOT EXISTS creditscard (" +
                    "id int PRIMARY KEY AUTO_INCREMENT," +
                    "creditCardNumber VARCHAR(128) NOT NULL UNIQUE KEY," +
                    "lastDigit int(3) NOT NULL," + 
                    "idClient int NOT NULL," +
                    "idBank int NOT NULL," +
                    "dateApperture datetime DEFAULT CURRENT_TIMESTAMP," +
                    "useVisa boolean NOT NULL," +
                    "expire varchar(10) NOT NULL" +
                ");";

            this.sql.execute(query);

            query = 
                "CREATE TABLE IF NOT EXISTS founds (" +
                    "id int PRIMARY KEY AUTO_INCREMENT," +
                    "idClient int NOT NULL," +
                    "title varchar(256) NOT NULL," +
                    "invertion int NOT NULL," +
                    "amount int NOT NULL," +
                    "rent int NOT NULL," + 
                    "chanceSuccefully int NOT NULL," +
                    "percentSuccefully int NOT NULL," +
                    "gainWithSuccefully int NOT NULL," +
                    "days int NOT NULL," +
                    "dateApperture datetime DEFAULT CURRENT_TIMESTAMP," +
                    "dateExpire datetime NOT NULL" +
                ");";

            this.sql.execute(query);

            query = 
                "CREATE TABLE IF NOT EXISTS accountsbank (" +
                    "id int PRIMARY KEY AUTO_INCREMENT," +
                    "idClient int NOT NULL," +
                    "accountTypeSaving boolean NOT NULL," + 
                    "accountNumber varchar(128) NOT NULL UNIQUE KEY," +
                    "cbu varchar(32) NOT NULL UNIQUE KEY," +
                    "lastDigit int(3) NOT NULL," +
                    "dateStarted datetime DEFAULT CURRENT_TIMESTAMP," +   
                    "money int(10) NOT NULL," +
                    "moneyIncoming int DEFAULT 0," +
                    "moneyRetired int DEFAULT 0 " +
                ");";

            this.sql.execute(query);

            query = 
                "CREATE TABLE IF NOT EXISTS clients (" +
                    "id int PRIMARY KEY AUTO_INCREMENT," +
                    "name varchar(32) NOT NULL," +   
                    "lastName varchar(32) NOT NULL," +                  
                    "dni varchar(32) NOT NULL UNIQUE KEY," +  
                    "phone varchar(32) NOT NULL," + 
                    "direction varchar(32) NOT NULL," +
                    "dateRegister datetime DEFAULT CURRENT_TIMESTAMP" + 
                ");";

            this.sql.execute(query);

            query = 
                "CREATE TABLE IF NOT EXISTS investments (" +
                    "id int PRIMARY KEY AUTO_INCREMENT," +
                    "idClient int(11) NOT NULL," +
                    "nameValue varchar(32) NOT NULL," +   
                    "numberTitles int(11) NOT NULL," +                  
                    "priceCotization varchar(11) NOT NULL" +
                ");";

            this.sql.execute(query);
        }
        catch (SQLException e) {
            System.out.printf("Error en la consulta: %s", e.getMessage());
        }
    }

    public void dropTables() {
        try {
            this.sql.execute("DROP TABLE IF EXISTS creditscard;"); 
            this.sql.execute("DROP TABLE IF EXISTS accountsbank;");
            this.sql.execute("DROP TABLE IF EXISTS clients;");
            this.sql.execute("DROP TABLE IF EXISTS founds;");
            this.sql.execute("DROP TABLE IF EXISTS investments;");


        }
        catch (SQLException e) {
            System.out.printf("Ocurrió un error en la consulta: %s", e.getMessage());
        }
    }

    public void clearAllTables() {
         try {
            this.sql.execute("TRUNCATE TABLE creditscard;"); 
            this.sql.execute("TRUNCATE TABLE accountsbank;");
            this.sql.execute("TRUNCATE TABLE clients;");
            this.sql.execute("TRUNCATE TABLE founds;");
            this.sql.execute("TRUNCATE TABLE investments;");


        }
        catch (SQLException e) {
            System.out.printf("Ocurrió un error en la consulta: %s", e.getMessage());
        }
    }
}
