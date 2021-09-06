package PageObjectClasses.PIM.CommonClass;

import UtilitiesClass.SeleniumUtilities;
import UtilitiesClass.TestNGUtilites;
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

public class CatalogPage extends TestNGUtilites{
    ExtentTest logger;
    public CatalogPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
        this.logger = TestNGUtilites.node;
    }//end of constructor class


    //locators
    @FindBy(xpath = "//span[@class='v-nativebutton-caption' and text()='Catalogs']")
    WebElement catalogsTab;

    @FindBy(xpath = "//span[@class='v-nativebutton-caption' and text()='Catalogs']")
        WebElement catalogsTab1;

    @FindBy(xpath = "//*[@class='v-treetable-treespacer']")
    List<WebElement> catalogsList;

    public void chooseACatalogAndItemNumber(String catalogType, String itemNumber) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        SeleniumUtilities.clickByJs(driver,catalogsTab,"Catalogs Tab",logger);
        Thread.sleep(3000);
        if(catalogType.equalsIgnoreCase("US Catalog")){
            SeleniumUtilities.clickByJs(driver,catalogsList.get(0),"Catalog Name",logger);
        } else {
            SeleniumUtilities.clickByJs(driver,catalogsList.get(1),"Catalog Name",logger);
        }
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='"+itemNumber+"']"))).click();
            SeleniumUtilities.logPass(logger,"Succcessfully selected " + catalogType + " and item number " + itemNumber);
            System.out.println("Pass: Succcessfully selected " + catalogType + " and item number " + itemNumber);
        } catch (Exception e) {
            System.out.println("Unable to choose item number from " + catalogType + " " + e);
            SeleniumUtilities.logFail(logger,"Unable to choose item number from " + catalogType);
        }//end of exception
    }//end of method

}//end of class
