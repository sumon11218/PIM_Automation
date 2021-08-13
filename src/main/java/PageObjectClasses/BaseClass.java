package PageObjectClasses;

import ReusableClasses.AbstractReusableClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass extends AbstractReusableClass {
    public BaseClass(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }//end of constructor class

    //object for home page
    public static HomePage homePage(){
        HomePage homePage = new HomePage(driver);
        return homePage;
    }//end of of home page object

    //object for results page
    public static ResultsPage resultsPage(){
        ResultsPage resultsPage = new ResultsPage(driver);
        return resultsPage;
    }//end of of results page object

}//end of class
