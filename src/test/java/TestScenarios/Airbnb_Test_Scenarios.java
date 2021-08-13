package TestScenarios;

import PageObjectClasses.BaseClass;
import ReusableClasses.AbstractReusableClass;
import ReusableClasses.ReusableMethodsWithLogger;
import org.testng.annotations.Test;

public class Airbnb_Test_Scenarios extends AbstractReusableClass {

    @Test(priority = 1)
    public void searchingForATrip() throws InterruptedException {
        logger = reports.createTest("Searching for a trip on airbnb home page");
        node = logger.createNode("Entering a Location, choosing check in and checkout date");
        driver.navigate().to("https://www.airbnb.com");
        Thread.sleep(2200);
        //set location
        BaseClass.homePage().setLocation("Los Angeles");
        //set start date to current day
        BaseClass.homePage().setCheckInDate(0);
        //set end date to current day + 4
        BaseClass.homePage().setCheckOutDate(4);
        node = logger.createNode("Choosing number of guests including adults, children and infants");
        //choose number of guests(adults, children, infants)
        BaseClass.homePage().setGuests(2,2,1);
        //click on search button
        BaseClass.homePage().clickSearch();
    }//end of test 1

    @Test(dependsOnMethods = "searchingForATrip")
    public void choosingAirbnbPlace() throws InterruptedException {
        logger = reports.createTest("Choosing an Airbnb place");
        Thread.sleep(2200);
        node = logger.createNode("Clicking on Type of Place and choosing Entire Place as an option");
        BaseClass.resultsPage().clickOnTypeofPlace();
        BaseClass.resultsPage().chooseEntirePlaceOption();
        BaseClass.resultsPage().clickOnPanelSaveButton();
        Thread.sleep(2200);
        node = logger.createNode("Choose first available airbnb and capture the name of the place");
        BaseClass.resultsPage().clickOnImageByIndex(0);
        BaseClass.resultsPage().captureNameForAirbnbPlace();
    }//end of test 2

}//end of java class
