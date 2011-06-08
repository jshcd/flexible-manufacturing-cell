package Scada.DataBase;

/* Imports */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This class specifies the routines to gather and save data to the database
 * @author Echoplex
 */
public class DBConnection {

    /**
     * Connection type instance to the database
     */
    private Connection _connection;

    /**
     * Constructor
     */
    public DBConnection() {
    }

    /**
     * Connects the database connection to the database location
     * @see Auxiliar.Constants
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void connect() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            _connection = DriverManager.getConnection(Auxiliar.Constants.DATABASE_LOCATION, "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the current connection
     * @throws SQLException
     */
    public void disconnect() {
        try {
            _connection.close();
        } catch (SQLException e) {
        }
    }

    /**
     * Executes a query throgh the database connection
     * @param query String that defines the query to execute
     * @return New Statement as result of the query execution
     * @throws SQLException
     */
    public Statement executeQuery(String query) {
        Statement s = null;
        try {
            s = _connection.createStatement();
            s.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(query);
        }
        return s;
    }

    /**
     * Executes a SELECT query through the database connection
     * @param selectQuery String that defines the SELECT query
     * @return Result Set as result from the database select execution
     */
    public ResultSet executeSelect(String selectQuery) {
        Statement s = executeQuery(selectQuery);
        ResultSet rs = null;

        try {
            rs = s.getResultSet();
            rs.next();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return rs;
    }
}
