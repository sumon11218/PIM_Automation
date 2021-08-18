package PageObjectClasses;

import ReusableClasses.AbstractReusableClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass extends AbstractReusableClass {
    public BaseClass(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }//end of constructor class


}//end of class
