package UtilitiesClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static UtilitiesClass.JavaUtilities.getDateTime;
import static UtilitiesClass.JavaUtilities.getPropValue;


public class TestNGUtilites {
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

    /**
     BeforeSuite will set the driver and report for your automation and
     as well as set up the database connection
    **/
    @BeforeSuite
    public void defineDriver() throws IOException, InterruptedException, ParseException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Thread.sleep(900);
        String path = "";
        path = "C:/Users/ksmsu/Downloads/HTML/HTML_Automation_Report_"+getDateTime() + ".html";
        //set html report path
        htmlReporter = new ExtentHtmlReporter(path);
        //define the report and attach it to html reporter
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        //set the report theme to dark/standard
        htmlReporter.config().setTheme(Theme.DARK);
        //set the driver
        driver = SeleniumUtilities.setDriver();
        //database setup
        DatabaseUtilities.dbSetup(getPropValue("db_url"), getPropValue("db_user"), getPropValue("db_password"));
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