package ReusableClasses;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReusableMethodsWithLogger extends AbstractReusableClass{
    //static variable for explicit timeout
    static int timeout = 8;
    public static void logInfo(ExtentTest logger, String message){
        logger.info(MarkupHelper.createLabel(message, ExtentColor.GREY));
    }//end of logInfo
    public static void logPass(ExtentTest logger, String message){
        logger.pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }//end of logPass
    public static void logFail(ExtentTest logger, String message){
        logger.fail(MarkupHelper.createLabel(message, ExtentColor.RED));
    }//end of logFail
    //method to set chromedriver on both local(windows) and jenkins(linux)
    public static WebDriver setDriver() throws InterruptedException, IOException {
        WebDriver driver = null;
        //kill all chrome driver instance
        //Thread.sleep(1000);
        //for windows
        //Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
        Thread.sleep(1000);
         /*
          WebDriverManager command allows you to pick up a driver based on your
          chrome browser version in your system. Don't need to install chrome driver
          anymore and no need to set the property.
         */
            WebDriverManager.chromedriver().setup();

            //set chrome preferences to allows download during headless if needed
            HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
            chromePreferences.put("profile.default_content_settings.popups", 0);
            chromePreferences.put("download.prompt_for_download", "false");
            chromePreferences.put("download.default_directory", AbstractReusableClass.directoryFolder);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
            //chromeOptions.addArguments("headless");
            chromeOptions.addArguments("start-maximized");
            chromeOptions.addArguments("incognito");
            chromeOptions.addArguments("disable-gpu");
            chromeOptions.addArguments("dns-prefetch-disable");
            //set capabilities and arguments
            chromeOptions.setExperimentalOption("prefs", chromePreferences);
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            //handle unexpected alert through desired cap
            cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            ChromeDriverService driverService = ChromeDriverService.createDefaultService();
            //define the driver and set the chromeoptions
            driver = new ChromeDriver(driverService,chromeOptions);
        return driver;
    }//end of setDriver method

    public static WebElement setExplictWaitForElement(WebDriver driver, WebElement locator){
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        element = wait.until(ExpectedConditions.visibilityOf(locator));
        return element;
    }//end of setExplictWaitForElement

    public static void waitForPageToLoadByElementVisibility(WebDriver driver, WebElement locator, String pageName, ExtentTest logger){
        try {
            printStatementInfo("Page", "waitForPageToLoadByElementVisibility", "", pageName);
            setExplictWaitForElement(driver,locator);
            logPass(logger,pageName + " page loaded successfully");
        }catch (Exception e) {
            printStatementFail("Page", "waitForPageToLoadByElementVisibility", "", pageName, e);
            logFail(logger,"Method waitForPageToLoadByElementVisibility: Unable to load page " + pageName);
            getScreenShot(driver,logger,pageName);
        }
    }//end of waitForPageToLoadByElementVisibility

    public static void printStatementInfo(String methodType, String methodName, String userData, String elementName){
            switch (methodType.toLowerCase()) {
                case "input":
                    System.out.println("Method " + methodName + ": Entering test data " + userData + " on " + elementName);
                    break;
                case "select":
                    System.out.println("Method " + methodName + ": Selecting value " + userData + " on " + elementName);
                    break;
                case "click":
                    System.out.println("Method " + methodName + ": Click on " + elementName);
                    break;
                case "hover":
                    System.out.println("Method " + methodName + ": Hover on " + elementName);
                    break;
                case "attribute":
                    System.out.println("Method " + methodName + ": Capturing attribute on " + elementName);
                    break;
                case "text":
                    System.out.println("Method " + methodName + ": Capturing text on " + elementName);
                    break;
                case "value":
                    System.out.println("Method " + methodName + ": captured value " + userData + " from " + elementName);
                    break;
                case "clear":
                    System.out.println("Method " + methodName + ": Clearing test data on " + elementName);
                    break;
                case "page":
                    System.out.println("Method " + methodName + ": Wait for " + elementName + " page to load");
                    break;
                case "navigate":
                    System.out.println("Method " + methodName + ": Navigating to " + elementName + " page: " + userData);
                    break;
                case "count":
                    System.out.println("Method " + methodName + ": " + elementName + " count: " + userData);
                    break;
                case "delete":
                    System.out.println("Method " + methodName + ": Deleting " + userData + " from " +  elementName);
                    break;
                case "verify":
                    System.out.println("Method " + methodName + ": Verifying " + elementName + " for " +  userData);
                    break;
                case "wait":
                    System.out.println("Method " + methodName + ": Waiting " + userData + " for " +  elementName);
                    break;
                case "skip":
                    System.out.println("Method " + methodName + ": Skipping " + elementName);
                    break;
            }//end of switch statement
    }//end of printStatementInfo method

    public static void printStatementPass(String methodType, String methodName, String userData , String elementName){
            switch (methodType.toLowerCase()) {
                case "verification":
                    System.out.println("Method " + methodName + " PASS: " + elementName + " " + userData + " as expected");
                    break;
                case "validate":
                    System.out.println("Method " + methodName + " PASS: " + elementName + " " + userData + " validated as expected");
                    break;
            }//end of switch statement
    }//end of printStatementPass method

    public static void printStatementFail(String methodType, String methodName, String userData, String elementName, Exception e){
            switch (methodType.toLowerCase()) {
                case "input":
                    System.out.println("Method " + methodName + " FAIL: Unable to enter test data " + userData + " on " + elementName + " Exception: " + e);
                    break;
                case "select":
                    System.out.println("Method " + methodName + " FAIL: Unable to select a drop down value " + userData + " on " + elementName + " Exception: " + e);
                    break;
                case "click":
                    System.out.println("Method " + methodName + " FAIL: Unable to Click on " + elementName + " Exception: " + e);
                    break;
                case "hover":
                    System.out.println("Method " + methodName + " FAIL: Unable to Hover on " + elementName + " Exception: " + e);
                    break;
                case "attribute":
                    System.out.println("Method " + methodName + " FAIL: Unable to capture attribute on " + elementName + " Exception: " + e);
                    break;
                case "text":
                    System.out.println("Method " + methodName + " FAIL: Unable to capture text on " + elementName + " Exception: " + e);
                    break;
                case "clear":
                    System.out.println("Method " + methodName + " FAIL: Unable to clear test data on " + elementName + " Exception: " + e);
                    break;
                case "page":
                    System.out.println("Method " + methodName + " FAIL: Unable to load page " + elementName + " Exception: " + e);
                    break;
                case "locate":
                    System.out.println("Method " + methodName + " FAIL: Unable to locate " + elementName + " Exception: " + e);
                    break;
                case "count":
                    System.out.println("Method " + methodName + " FAIL: Unable to get count for " + elementName + " Exception: " + e);
                    break;
                case "verify":
                    System.out.println("Method " + methodName + " FAIL: Unable to verify for " + elementName + " Exception: " + e);
                    break;
                case "navigate":
                    System.out.println("Method " + methodName + " FAIL: Unable to navigate to " + elementName + ". Exception: " + e);
                    break;
                case "execute":
                    System.out.println("Method " + methodName + " FAIL: Unable to complete for " + elementName + " Exception: " + e);
                    break;
            }//end of switch statement
    }//end of printStatementFail method

    public static void printStatementForFailVerification(String methodName, String expectedData, String elementName, String actualData){
            System.out.println("Method " + methodName + " FAIL: For " + elementName + " Expected: " + expectedData + " but Actual: " + actualData);
    }//end of printStatementForFailVerification method

    //verify expected with actual
    public static void verifyMessage(String expected, String actual, String elementName, ExtentTest logger){
        if(expected.contains(actual)){
            ReusableClasses.ReusableMethodsWithLogger.logPass(logger, "For " + elementName + " Expected matches with actual: " + expected);
            printStatementPass("Verification", "verifyMessage", expected + " for " + elementName,"Expected matches with actual: ");
        } else {
            ReusableClasses.ReusableMethodsWithLogger.logFail(logger, "For " + elementName + " Expected: " + expected + " and Actual: " + actual);
            printStatementForFailVerification("verifyMessage", expected, elementName, actual);
        }
    }//end of verifyMessage

    //verify expected with actual
    public static void verifyMessageEqualsto(String expected, String actual, String elementName, ExtentTest logger){
        if(expected.equals(actual)){
            ReusableClasses.ReusableMethodsWithLogger.logPass(logger, "For " + elementName + " Expected matches with actual: " + expected);
            printStatementPass("Verification", "verifyMessageEqualsto", expected + " for " + elementName,"Expected matches with actual: ");
        } else {
            ReusableClasses.ReusableMethodsWithLogger.logFail(logger, "For " + elementName + " Expected: " + expected + " and Actual: " + actual);
            printStatementForFailVerification("verifyMessageEqualsto", expected, elementName, actual);
        }
    }//end of verifyMessageEqualsto

    //method to select a value from a dropdown
    public static void dropDownByVisisbleText(WebDriver driver, WebElement locator, String dropDownValue, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Select", "dropDownByVisisbleText", dropDownValue, elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            Select select = new Select(element);
            select.selectByVisibleText(dropDownValue);
            logPass(logger,"Successfully selected a drop down value " + dropDownValue + " on " + elementName);
        } catch (Exception e) {
            printStatementFail("Select", "dropDownByVisisbleText", dropDownValue, elementName, e);
            logFail(logger,"Method dropDownByVisisbleText: Unable to select drop down value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of sendkeys method

    //method to enter user input on send keys
    public static void userKeys(WebDriver driver, WebElement locator, String userInput, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Input", "userKeys", userInput, elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            Thread.sleep(900);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(userInput);
            logPass(logger,"Successfully entered a user value " + userInput + " on " + elementName);
        } catch (Exception e) {
            printStatementFail("Input", "userKeys", userInput, elementName, e);
            logFail(logger,"Method userKeys: Unable to enter a user value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of sendkeys method

    //method to enter user input on send keys
    public static void enterUserCredentialsForLogin(WebDriver driver, WebElement locator, String userInput, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Input", "enterUserCredentialsForLogin",userInput, elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            Thread.sleep(900);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            element.sendKeys(userInput);
            logPass(logger,"Successfully entered a user value on " + elementName);
        } catch (Exception e) {
            printStatementFail("Input", "enterUserCredentialsForLogin", userInput, elementName, e);
            logFail(logger,"Method userKeys: Unable to enter a user value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of enterUserCredentialsForLogin method

    //method to enter user input on send keys
    public static void userTypeAndHitEnter(WebDriver driver, WebElement locator, String userInput, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Input","userTypeAndHitEnter", userInput, elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.clear();
            Thread.sleep(1000);
            element.sendKeys(userInput);
            element.sendKeys(Keys.ENTER);
            logPass(logger,"Successfully entered a value " + userInput + " on " + elementName);
        } catch (Exception e) {
            printStatementFail("Input", "userTypeAndHitEnter", userInput, elementName, e);
            logFail(logger,"Method userTypeAndHitEnter: Unable to enter value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of userTypeAndHitEnter method

    //method to clear user input then hit enter on the field
    public static void userClearAndHitEnter(WebDriver driver, WebElement locator, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Clear", "userClearAndHitEnter","", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.clear();
            Thread.sleep(1000);
            element.sendKeys(Keys.ENTER);
            logPass(logger,"Successfully cleared a value on " + elementName);
        } catch (Exception e) {
            printStatementFail("Clear", "userClearAndHitEnter","", elementName, e);
            logFail(logger,"Method userClearAndHitEnter: Unable to clear value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of userClearAndHitEnter method

    //method to enter user input on send keys
    public static void clearData(WebDriver driver, WebElement locator, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Clear", "clearData","", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.clear();
            Thread.sleep(900);
            logPass(logger,"Successfully cleared existing data on " + elementName);
        } catch (Exception e) {
            printStatementFail("Clear","clearData", "", elementName, e);
            logFail(logger,"Method clearData: Unable to clear existing data on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of clearData method

    //method to enter user input on send keys
    public static void clearDataWithHittingCtrlA(WebDriver driver, WebElement locator, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Clear", "clearData","", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            Thread.sleep(900);
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            Thread.sleep(500);
            element.sendKeys(Keys.chord(Keys.BACK_SPACE));
            logPass(logger,"Successfully cleared existing data on " + elementName);
        } catch (Exception e) {
            printStatementFail("Clear","clearData", "", elementName, e);
            logFail(logger,"Method clearData: Unable to clear existing data on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of clearData method


    //method to hover over an element
    public static void mouseHover(WebDriver driver, WebElement locator, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Hover", "mouseHover","", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.click();
            logPass(logger,"Successfully mouse hovered on " + elementName);
        } catch (Exception e) {
            printStatementFail("Hover", "mouseHover","", elementName, e);
            logFail(logger,"Method mouseHOver: Unable to mouse hover on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of mouseHover method

    //method to click on an element
    public static void click(WebDriver driver, WebElement locator, String elementName, ExtentTest logger){
        try{
            printStatementInfo("Click", "click","", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.click();
            logPass(logger,"Successfully clicked on " + elementName);
        } catch (Exception e) {
            printStatementFail("Click", "click","", elementName, e);
            logFail(logger,"Method click: Unable to click on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of click method

    public static void clickByJs(WebDriver driver, WebElement locator, String elementName, ExtentTest logger) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Thread.sleep(1500);
        try{
            printStatementInfo("Click","clickByJs", "", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            js.executeScript("arguments[0].click();", element);
            logPass(logger,"Successfully clicked on " + elementName);
        } catch (Exception e) {
            printStatementFail("Click", "clickByJs", "", elementName, e);
            logFail(logger,"Method clickByJs: Unable to click on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of click by js method

    public static void clickForGuests(WebDriver driver, WebElement locator, int numbOfGuest, String elementName, ExtentTest logger) throws InterruptedException {
        try{
            printStatementInfo("Click","clickForGuests", "", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            element.click();
            logPass(logger,"Successfully selected " + numbOfGuest + " " + elementName);
        } catch (Exception e) {
            printStatementFail("Click", "clickForGuests", "", elementName, e);
            logFail(logger,"Method clickForGuests: Unable to click on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of click by js method

    public static void typeUsingJs(WebDriver driver, WebElement locator, String elementName, String userInput,ExtentTest logger) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Thread.sleep(1500);
        try{
            //printStatementInfo("Type","clickByJs", "", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].value='"+userInput+"'", element);
            logPass(logger,"Successfully set a value on " + elementName);
        } catch (Exception e) {
            //printStatementFail("Click", "clickByJs", "", elementName, e);
            logFail(logger,"Method clickByJs: Unable to set value on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
    }//end of click by js method


    //method to return text from an element
    public static String captureText(WebDriver driver, WebElement locator, String elementName, ExtentTest logger) throws InterruptedException {
        String result = null;
        try{
            printStatementInfo("Text", "captureText", "", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            result = element.getText();
            printStatementPass("Verification","captureText","Successfully captured a text " + result, elementName);
            logPass(logger,"Successfully captured a text " + result + " from " + elementName);
        } catch (Exception e) {
            printStatementFail("Text", "captureText", "", elementName, e);
            logFail(logger,"Method captureText: Unable to capture text from " + elementName);
            getScreenShot(driver,logger,elementName);
        }
        return result;
    }//end of captureText method

    //method to return text from an element
    public static String captureByXpathString(WebDriver driver, String locator, String elementName, ExtentTest logger) throws InterruptedException {
        String result = null;
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        try{
            printStatementInfo("Text", "captureText", "", elementName);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
            result = element.getText();
            printStatementPass("Verification","captureText","Successfully captured a text " + result, elementName);
            logPass(logger,"Successfully captured a text " + result + " from " + elementName);
        } catch (Exception e) {
            printStatementFail("Text", "captureText", "", elementName, e);
            logFail(logger,"Method captureText: Unable to capture text from " + elementName);
            getScreenShot(driver,logger,elementName);
        }
        return result;
    }//end of captureText method

    //method to return text from an element
    public static String getAttribute(WebDriver driver, WebElement locator, String attributName, String elementName, ExtentTest logger) throws InterruptedException {
        String result = null;
        try{
            printStatementInfo("Attribute", "getAttribute", "", elementName);
            WebElement element = setExplictWaitForElement(driver,locator);
            result = element.getAttribute(attributName);
            logPass(logger,"Captured attribute for " + attributName + ": " + result);
        } catch (Exception e) {
            printStatementFail("Attribute", "getAttribute", "", elementName, e);
            logFail(logger,"Method getAttribute: Unable to capture attribute on " + elementName);
            getScreenShot(driver,logger,elementName);
        }
        return result;
    }//end of getAttribute method

    //method to capture screenshot when logger fails
    public static void getScreenShot(WebDriver wDriver, ExtentTest logger, String imageName) {
        try {
            String fileName = imageName + ".png";
            String os = System.getProperty("os.name").toLowerCase();
            String directory = null;
            String snPath = null;
            if (runTimeEnv.equalsIgnoreCase("Local")) {
                directory = System.getProperty("user.home")+"//Downloads//HTML//Screenshots//";
                snPath = "Screenshots//";
            } else {
                directory = "HTML_Reports/Screenshots/";
                snPath = "Screenshots/";
            }
            File sourceFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = snPath + fileName;
            logger.fail("Screenshot for above failed step",MediaEntityBuilder.createScreenCaptureFromPath(image).build());
        } catch (Exception e) {
            logFail(logger,"Error Occured while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }//end of screenshot method

    //method to capture screenshot when logger Info
    public static void getScreenShotRegular(WebDriver wDriver, ExtentTest logger, String imageName) {
        try {
            String fileName = imageName + ".png";
            String os = System.getProperty("os.name").toLowerCase();
            String directory = null;
            String snPath = null;
            if (os.contains("win")) {
                directory = System.getProperty("user.home")+"//Downloads//HTML//Screenshots//";
                snPath = "Screenshots//";
            } else {
                directory = "HTML_Reports/Screenshots/";
                snPath = "Screenshots/";
            }
            File sourceFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = snPath + fileName;
            logger.info("Screenshot",MediaEntityBuilder.createScreenCaptureFromPath(image).build());
        } catch (Exception e) {
            logFail(logger, "Error Occured while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }//end of screenshot method

    public static String getDateInFormat(int days){
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = Calendar.getInstance();
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, days);
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        return newDate;
    }//end of get date

    public static String getDateInFormatCalendar(int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, days);
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        return newDate;
    }//end of get date

    //method below will generate random unique id
    public static String generateID(int num) {
        Random rnd = new Random();
        char [] digits = new char[num];
        digits[0] = (char) (rnd.nextInt(9) + '1');
        for(int i=1; i<digits.length; i++) {
            digits[i] = (char) (rnd.nextInt(10) + '0');
        }
        Long val = Long.parseLong(new String(digits));
        return val.toString();
    }//end of get up to 19 digits id

    //switch to a window
    public static void switchTo(int index, WebDriver wDriver) {
        try {
            List<String> win = new ArrayList<String>(wDriver.getWindowHandles());
            wDriver.switchTo().window(win.get(index));
        } catch (IndexOutOfBoundsException e) {
            Reporter.log("Invalid Window Index : " + index, true);
        }
    }//end of switch to method

    public static String getDateTime() {
        SimpleDateFormat sdfDateTime;
        String strDateTime;
        sdfDateTime = new SimpleDateFormat("yyyyMMdd'_'HHmmss'_'SSS");
        Date now = new Date();
        strDateTime = sdfDateTime.format(now);
        return strDateTime;
    }

    //method below allow you to remove leading zeros from string numbers
    public static String removeZero(String str)
    {
        // Count leading zeros
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0')
            i++;

        // Convert str into StringBuffer as Strings
        // are immutable.
        StringBuffer sb = new StringBuffer(str);

        // The  StringBuffer replace function removes
        // i characters from given index (0 here)
        sb.replace(0, i, "");

        return sb.toString();  // return in String
    }//end of removeZero method


}//end of java class