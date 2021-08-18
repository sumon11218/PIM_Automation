package ReusableClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtilities extends AbstractReusableClass{

    public static ResultSet getQueryResult(String selectStatement) throws Exception {
        ResultSet res = null;
        try{
            String query = selectStatement;
            // Get the contents from table on DB
            res = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            ReusableMethodsWithLogger.logFail(logger,"Unable to get content from a table: " + e);
        }
        return res;
    }//end of getQueryResult


}//end of java class
