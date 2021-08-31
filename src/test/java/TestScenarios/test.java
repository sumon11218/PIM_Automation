package TestScenarios;

import PageObjectClasses.BaseClass;
import UtilitiesClass.JavaUtilities;
import UtilitiesClass.TestNGUtilites;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import static UtilitiesClass.JavaUtilities.getPropValue;

public class test extends TestNGUtilites{

    //test
    @Test
    public void Choos() throws IOException, InterruptedException {
        logger = reports.createTest("Selecting Dental for US Catalog");
        node = logger.createNode("Choosing Dental Dashboard from Queries tab");
        BaseClass.loginPage().login(getPropValue("pim_username"),getPropValue("pim_password"));
        BaseClass.queriesPage().selectDashboardFromItem("Dental");
        node = logger.createNode("Selecting US Catalog and clicking on specific Item number associated with Dental from Catalogs tab");
        BaseClass.catalogPage().chooseACatalogAndItemNumber("US Catalog","1000002");
    }//end of test

}//end of class
