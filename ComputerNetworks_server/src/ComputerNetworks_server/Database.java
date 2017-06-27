
package ComputerNetworks_server;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ibraheem
 */
public class Database {
    
 static final String DB_URL = "jdbc:mysql://localhost:3306/project";
 static final String USER = "root";
   static final String PASS = "0000";
   Connection conn = null;
   Statement stmt = null;
    
    public void conn() {
   
   try{
      Class.forName("com.mysql.jdbc.Driver") ;
     
     System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      }catch(Exception e){
      e.printStackTrace();
   }
   }
      
      
    
    public void EU( String sql1) throws SQLException
    {
    stmt.executeUpdate(sql1);
    }
    public  ResultSet EQ( String sql1) throws SQLException
    {
      ResultSet rs = stmt.executeQuery(sql1);
     
        return rs; 
     
      
    }
  
    
    
    
     
    
      
 
     public void  Close() throws SQLException{
      stmt.close();
      conn.close();

         if(stmt!=null)
            stmt.close();
    
         if(conn!=null)
            conn.close();
     
         
      
  
}
}
