package PageObjectClasses;

import UtilitiesClass.TestNGUtilites;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass extends TestNGUtilites {
    public BaseClass(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }//end of constructor class


}//end of class
