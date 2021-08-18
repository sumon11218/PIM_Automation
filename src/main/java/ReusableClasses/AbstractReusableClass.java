package ReusableClasses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


public class AbstractReusableClass{
    /**
     you need to set the global variables as public static in order
    to be used on your @test classes
     **/
    public static WebDriver driver = null;
    public static ExtentHtmlReporter htmlReporter = null;
    public static ExtentReports reports = null;
    public static ExtentTest logger = null;
    public static ExtentTest node = null;
    public static String directoryFolder = null;
    public static String runTimeEnv = "";

    /** database connection variables **/
    // Connection object
    static Connection con = null;
    // Statement object
    public static Statement stmt;
    // Constant for Database URL
    public static String DB_URL = "jdbc:mysql://localhost:3306/user";
    // Constant for Database Username
    public static String DB_USER = "root";
    // Constant for Database Password
    public static String DB_PASSWORD = "root";

    /**
     BeforeSuite will set the driver and report for your automation and
     as well as set up the database connection
    **/
    @BeforeSuite
    public void defineDriver() throws IOException, InterruptedException, ParseException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Thread.sleep(900);
        String path = "";
        path = "C:/Users/ksmsu/Downloads/HTML/HTML_Automation_Report_"+ ReusableClasses.ReusableMethodsWithLogger.getDateTime() + ".html";
        //set html report path
        htmlReporter = new ExtentHtmlReporter(path);
        //define the report and attach it to html reporter
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        //set the report theme to dark/standard
        htmlReporter.config().setTheme(Theme.DARK);
        //set the driver
        driver = ReusableClasses.ReusableMethodsWithLogger.setDriver();

        //database setup
       try{
            // Make the database connection
            String dbClass = "com.mysql.jdbc.Driver";
            Class.forName(dbClass).newInstance();
            // Get connection to DB
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // Statement object to send the SQL statement to the Database
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of before suite

    //Before is not needed at the moment
    @BeforeMethod
    public void getMethodName() throws IOException, InterruptedException {
        //start the chrome driver
        //driver = ReusableClasses.ReusableMethodsWithLogger.setDriver();
    }//end of before method


    //AfterMethod writes back the logs to HTML report for an individual @Test
    @AfterMethod
    public void logTest(){
        reports.flush();
    }//end of after method

    /**
      AfterSuite will close the DB connection
     **/
    @AfterSuite
    public void quitSession() throws SQLException {
        //don't need driver.quit at the moment since runtime.exec is used to kill chrome driver
        //driver.quit();

        // Close DB connection
        if (con != null) {
            con.close();
        }
    }//end of after suite
}//end of java class