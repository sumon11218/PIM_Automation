package PageObjectClasses;

import ReusableClasses.AbstractReusableClass;
import ReusableClasses.ReusableMethodsWithLogger;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResultsPage extends AbstractReusableClass{
    ExtentTest logger;
    public ResultsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.logger = AbstractReusableClass.node;
    }//end of constructor class

    //using @FindBy to locate elements
    @FindBy(xpath = "//button[@aria-label='Type of place']")
    WebElement typeOfPlaceButton;
    //@FindBy(xpath = "//input[@name='Entire place']")
    @FindBy(xpath = "//*[@data-testid='filterItem-room_type-checkbox-room_types-Entire_home_apt']")
    WebElement entirePlaceCheckbox;
    @FindBy(xpath = "//*[@data-testid='filter-panel-save-button']")
    WebElement panelSaveButton;
    @FindBy(xpath = "//*[@class='_ttw0d']")
    List<WebElement> imageLink;
    @FindBy(xpath = "//*[@class='_1n81at5']")
    WebElement airBnbPlaceName;

    public void clickOnTypeofPlace() throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,typeOfPlaceButton,"Type Of Place",logger);
    }//end of clickOnTypeofPlace

    public void chooseEntirePlaceOption() throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,entirePlaceCheckbox,"Entire Place Checkbox",logger);
    }//end of chooseEntirePlaceOption

    public void clickOnPanelSaveButton() throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,panelSaveButton,"Panel Save Button",logger);
    }//end of clickOnPanelSaveButton

    public void clickOnImageByIndex(int index) throws InterruptedException {
        ReusableMethodsWithLogger.click(driver,imageLink.get(index),"Image Link",logger);
    }//end of clickOnImageByIndex

    public void captureNameForAirbnbPlace() throws InterruptedException {
        ReusableMethodsWithLogger.switchTo(1,driver);
        ReusableMethodsWithLogger.captureText(driver,airBnbPlaceName,"Airbnb Place Name",logger);
    }//end of captureNameForAirbnbPlace


}//end of class
