package mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query extends DBConnection {

    String query;
    private PreparedStatement prepareQuery;

    public Query(String query) {
        this.query = query;
    }

    public ResultSet getQuery(Object... args) {
        try {

            this.prepareQuery = connection.prepareStatement(this.query);
            
            if (args.length > 0) {
                 for (int i = 0; i < args.length; i++) {
                    this.prepareQuery.setString((i + 1), args[i].toString());
                }
            }

            ResultSet result = this.prepareQuery.executeQuery();
            return result;
        }
        catch (SQLException e) {
            generateError(e.getMessage());
            return null;
        }
    }

    public Integer setQuery(Object... args) {

        try {
            this.prepareQuery = connection.prepareStatement(this.query);

            for (int i = 0; i < args.length; i++) {
                this.prepareQuery.setString((i + 1), args[i].toString());
            }

            int result = this.prepareQuery.executeUpdate();
            return result;
        }
        catch (SQLException e) {
            generateError(e.getMessage());
        }

        return null;
    }

    public void clearTables() {
        clearAllTables();
        System.out.println("Todos los registros fueron eliminados");
    }

    public void deleteTables() {
        dropTables();
        System.out.println("Todas las tablas fueron eliminadas");
    }

    public void createAllTables() {
        createTables();
        System.out.println("Todas las tablas fueron creadas");
    }

    private static void generateError(String error) {
        System.out.printf("Ocurrió un error en la consulta: %s", error);
    }
}
