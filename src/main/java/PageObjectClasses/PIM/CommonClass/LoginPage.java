package PageObjectClasses.PIM.CommonClass;

import UtilitiesClass.JavaUtilities;
import UtilitiesClass.SeleniumUtilities;
import UtilitiesClass.TestNGUtilites;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static UtilitiesClass.JavaUtilities.getPropValue;

public class LoginPage extends TestNGUtilites {
ExtentTest logger;
    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
        this.logger = TestNGUtilites.node;
    }//end of constructor class

    @FindBy(xpath = "//*[@id='userName']")
    WebElement usernameField;
    @FindBy(xpath = "//*[@id='password']")
    WebElement passwordField;
    @FindBy(xpath = "//*[@id='login-button']")
    WebElement loginButton;

    public void login(String username, String password) throws IOException, InterruptedException {
        driver.navigate().to(getPropValue("pim_url"));
        Thread.sleep(2500);
        SeleniumUtilities.userKeys(driver,usernameField,username,"Username",logger);
        SeleniumUtilities.userKeys(driver,passwordField,password,"Password",logger);
        SeleniumUtilities.click(driver,loginButton,"Login Button",logger);
    }//end of login

}//end of login page
