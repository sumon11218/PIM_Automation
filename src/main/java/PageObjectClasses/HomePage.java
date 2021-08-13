package PageObjectClasses;

import ReusableClasses.AbstractReusableClass;
import ReusableClasses.ReusableMethodsWithLogger;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage extends AbstractReusableClass {
    ExtentTest logger;
    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.logger = AbstractReusableClass.node;
    }//end of constructor class

    //using @FindBy to locate elements
    @FindBy(xpath = "//*[@id='bigsearch-query-detached-query-input']")
    WebElement locationField;
    @FindBy(xpath = "//*[text()='Check in']")
    WebElement checkIn;
    @FindBy(xpath = "//*[text()='Check out']")
    WebElement checkOut;
    @FindBy(xpath = "//*[text()='Guests']")
    WebElement guests;
    @FindBy(xpath = "//*[@data-testid='stepper-adults-increase-button']")
    WebElement increaseAdultsButton;
    @FindBy(xpath = "//*[@data-testid='stepper-children-increase-button']")
    WebElement increaseChildrensButton;
    @FindBy(xpath = "//*[@data-testid='stepper-infants-increase-button']")
    WebElement increaseInfantsButton;
    @FindBy(xpath = "//*[@data-testid='structured-search-input-search-button']")
    WebElement searchButton;


    public void setLocation(String userInput){
        ReusableMethodsWithLogger.userKeys(driver,locationField,userInput,"Location Field",logger);
    }//end of location field

    public void setCheckInDate(int numOfDay) throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,checkIn,"Check In",logger);
        WebDriverWait wait = new WebDriverWait(driver,5);
        String date = ReusableMethodsWithLogger.getDateInFormatCalendar(numOfDay);
        try{
            System.out.println("Choose a check in date");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@data-testid,'-"+date+"')]")));
            element.click();
            ReusableMethodsWithLogger.logPass(logger,"Successfully set check in date " + date);
            //ReusableMethodsWithLogger.click(driver,element,"Check In",logger);
        }catch (Exception e){
            System.out.println("Unable to select a check in date: " + e);
            ReusableMethodsWithLogger.logFail(logger,"Unable to select a check in date");
            ReusableMethodsWithLogger.getScreenShot(driver,logger,"Checkin Date");
        }//end of exception
    }//end of checkin date

    public void setCheckOutDate(int numOfDay) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,5);
        String date = ReusableMethodsWithLogger.getDateInFormatCalendar(numOfDay);
        try{
            System.out.println("Choose a check out date");
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-testid='datepicker-day-"+date+"']")));
            element.click();
            ReusableMethodsWithLogger.logPass(logger,"Successfully set check out date " + date);
        }catch (Exception e){
            System.out.println("Unable to select a check out date: " + e);
            ReusableMethodsWithLogger.logFail(logger,"Unable to select a check out date");
            ReusableMethodsWithLogger.getScreenShot(driver,logger,"Checkout Date");
        }//end of exception
    }//end of checkout date

    public void setGuests(int numOfAdts, int numOfChlds, int numbOfInfnts) throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,guests,"Guests",logger);
        Thread.sleep(800);
        if(numOfAdts > 0){
            for(int i = 1; i <= numOfAdts;i++){
                ReusableMethodsWithLogger.clickForGuests(driver,increaseAdultsButton,i,"# of Adults",logger);
            }
        }//end of condition 1
        if(numOfChlds > 0){
            for(int i = 1; i <= numOfChlds;i++){
                ReusableMethodsWithLogger.clickForGuests(driver,increaseChildrensButton,i,"# of Children",logger);
            }
        }//end of condition 2
        if(numbOfInfnts > 0){
            for(int i = 1; i <= numbOfInfnts;i++){
                ReusableMethodsWithLogger.clickForGuests(driver,increaseInfantsButton,i,"# of Infants",logger);
            }
        }//end of condition 3
    }//end of setGuests date

    public void clickSearch() throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,searchButton,"Search Button",logger);
    }//end of search button


}//end of class
