package PageObjectClasses;

import PageObjectClasses.PIM.CommonClass.CatalogPage;
import PageObjectClasses.PIM.CommonClass.LoginPage;
import PageObjectClasses.PIM.CommonClass.QueriesPage;
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

    //object for queries page
    public static QueriesPage queriesPage(){
        QueriesPage queriesPage = new QueriesPage(driver);
        return queriesPage;
    }//end of queries page object

    //object for catalogs page
    public static CatalogPage catalogPage(){
        CatalogPage catalogPage = new CatalogPage(driver);
        return catalogPage;
    }//end of catalogs page object

}//end of class
