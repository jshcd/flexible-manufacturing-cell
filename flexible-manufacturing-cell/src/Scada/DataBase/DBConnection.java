
/*
 * This class is in charge of connecting and accepting connections from different terminals 
 * (other object of this class) and passing and receiving parameter sets.
 */
package Scada.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;	
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class DBConnection{

   private  Connection _connection; 
    
    public DBConnection() {}    
    
    public void connect() {
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            _connection = DriverManager.getConnection(Auxiliar.Constants.DATABASE_LOCATION, "", "");
        }
        catch(SQLException e){}
        catch(ClassNotFoundException e){}
  }
    
    public void disconnect(){
        try{
            _connection.close();
        }catch(SQLException e){}
    }

    public Statement executeQuery(String query){
        Statement s = null;
        try{
            s = _connection.createStatement();
            s.execute(query);
        }catch(SQLException e){
            System.out.println(e);
        }
        return s;
    }
    
    public ResultSet executeSelect(String selectQuery){
        Statement s = executeQuery(selectQuery);
        ResultSet rs = null;
        
        try{
            rs = s.getResultSet();
        }catch(SQLException e){
            System.out.println(e);
        }
        
        return rs;
    }
}