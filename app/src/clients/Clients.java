package clients;
import mysql.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import createNumberFormat.CreateNumberFormat;

public class Clients {

    private String name;
    private String lastName;
    private String dni;
    private String phone;
    private String direction;
    private CreateNumberFormat format = new CreateNumberFormat();
    
    public Clients() {

    }

    public Clients(String name, String lastName, String dni, String phone, String direction) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.phone = phone;
        this.direction = direction;
    }

    public void createClient() {
        int dniToInteger = Integer.parseInt(this.dni);

        new Query("INSERT INTO clients (name, lastName, dni, phone, direction) VALUES (?, ?, ?, ?, ?)").setQuery(
            name,
            lastName,
            dni,
            phone,
            direction
        );

        System.out.println();
        System.out.printf("El cliente %s %s con DNI %s se ha registrado con éxito", name, lastName, this.format.setFormat(dniToInteger));
    } 

    public Integer getLastClientID() {
         try {
            ResultSet result = new Query("SELECT MAX(id) AS userId FROM clients").getQuery();
            
            if (result.next()) {
                return result.getInt("userId");
            }
        }
        catch (SQLException e) {
            e.getStackTrace();
            return null;
        }

        return null;
    }

    public ResultSet getClient(String client) {
        ResultSet result = new Query(
            "SELECT * FROM clients AS cl LEFT JOIN accountsbank AS acc ON acc.idClient = cl.id LEFT JOIN founds AS fo ON fo.idClient = cl.id LEFT JOIN creditscard AS cr ON cr.idClient = cl.id WHERE cl.dni = ? OR cl.name LIKE ?"
        )
        .getQuery(
            client,
            "%" + client + "%"
        );

        return result;
    }

    public boolean getClientExist(String dni) {
        try {

            ResultSet result = new Query("SELECT id FROM clients WHERE dni = ?").getQuery(dni);
            
            if (result.next()) {
                return true;
            }
            
        }
        catch (SQLException e) {

        }
        
        return false;
    }

    public ResultSet getAllClients() {
        ResultSet result = new Query("SELECT * FROM clients").getQuery();
        return result;
    }

    public void deleteClient(int idClient) {
        new Query("DELETE FROM clients WHERE id = ?").setQuery(idClient);
    }
}
