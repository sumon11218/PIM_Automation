package PageObjectClasses.PIM.CommonClass;

import UtilitiesClass.SeleniumUtilities;
import UtilitiesClass.TestNGUtilites;
import com.aventstack.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QueriesPage extends TestNGUtilites {
    ExtentTest logger;
    public QueriesPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
        this.logger = TestNGUtilites.node;
    }//end of constructor class

    //locators
    @FindBy(xpath = "//*[@class='v-nativebutton-caption' and text()='Queries']")
    WebElement queriesTab;
    @FindBy(xpath = "//*[@id='search-select-menu-%entity.Article.name']")
    WebElement itemDropdown;

    public void selectDashboardFromItem(String itemType) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        SeleniumUtilities.clickByJs(driver,queriesTab,"Queries Tab",logger);
        SeleniumUtilities.click(driver,itemDropdown,"Item Dropdown",logger);
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='v-menubar-menuitem-caption' and text()='"+itemType+"']"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='v-menubar-menuitem-caption' and text()='"+itemType+" Dashboard']"))).click();
            SeleniumUtilities.logPass(logger,"Succcessfully selected " + itemType + " Dashboard");
            System.out.println("Pass: Succcessfully selected " + itemType + " Dashboard");
        } catch (Exception e) {
            System.out.println("Unable to select value from item dropdown " + e);
            SeleniumUtilities.logFail(logger,"Unable to select value for item " + itemType);
        }//end of exception
    }//end of method

}//end of java class
