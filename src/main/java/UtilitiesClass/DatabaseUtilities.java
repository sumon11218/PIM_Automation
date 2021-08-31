package UtilitiesClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseUtilities extends TestNGUtilites {

    public static void dbSetup(String url, String user, String password){
        //database setup
        try{
            // Make the database connection
            String dbClass = "com.mysql.jdbc.Driver";
            Class.forName(dbClass).newInstance();
            // Get connection to DB
            Connection con = DriverManager.getConnection(url, user, password);
            // Statement object to send the SQL statement to the Database
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of dbSetup

    public static ResultSet getQueryResult(String selectStatement) throws Exception {
        ResultSet res = null;
        try{
            String query = selectStatement;
            // Get the contents from table on DB
            res = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            SeleniumUtilities.logFail(logger,"Unable to get content from a table: " + e);
        }
        return res;
    }//end of getQueryResult


}//end of java class
