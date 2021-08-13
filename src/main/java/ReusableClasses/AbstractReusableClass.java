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

    /**
     BeforeSuite will set the environment you are running your automation on and
     as well as the sandbox you are executing on
    **/
    @BeforeSuite
    public void defineDriver() throws IOException, InterruptedException, ParseException {
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
    }//end of before suite

    //BeforeMethod will kickoff a new instance of chrome driver right before executing @Test
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
      AfterSuite will quit the current driver/session but don't need this at the moment
      since we are using Runtime.exec on setDriver method to kill all instances
     that were opened previously via automation
     **/
    @AfterSuite
    public void quitSession(){
        //driver.quit();
    }//end of after suite
}//end of java class