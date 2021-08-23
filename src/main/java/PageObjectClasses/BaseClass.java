package PageObjectClasses;

import PageObjectClasses.PIM.CommonClass.LoginPage;
import UtilitiesClass.TestNGUtilites;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass extends TestNGUtilites {
    public BaseClass(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }//end of constructor class

    //object for login page
    public static LoginPage loginPage(){
        LoginPage loginPage = new LoginPage(driver);
        return loginPage;
    }//end of login page object

}//end of class
