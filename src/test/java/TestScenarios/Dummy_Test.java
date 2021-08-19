package TestScenarios;

import UtilitiesClass.JavaUtilities;
import UtilitiesClass.TestNGUtilites;
import UtilitiesClass.DatabaseUtilities;
import org.testng.annotations.Test;

import java.sql.ResultSet;

public class Dummy_Test extends TestNGUtilites {

    @Test
    public void databaseTest() throws Exception {
        logger = reports.createTest("Database Test");
        node = logger.createNode("Get columns from userinfo table");
        String query = "select * from userinfo";
        //calling  the query method to get the result
       // ResultSet res = DatabaseUtilities.getQueryResult(query);
        // res.next() returns true if there is any next record else returns false
        /* while (res.next()) {
            System.out.print(res.getString("UserName"));
            System.out.print("\t" + res.getString(2));
            System.out.print("\t" + res.getString(3));
            System.out.println("\t" + res.getString(4));
        }//end of while

         */
        System.out.println(JavaUtilities.getPropValue("db_user"));

    }//end of test

}//end of class
