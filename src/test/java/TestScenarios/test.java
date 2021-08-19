package TestScenarios;

import UtilitiesClass.JavaUtilities;
import UtilitiesClass.TestNGUtilites;
import org.testng.annotations.Test;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        System.out.println(JavaUtilities.getPropValue("db_url"));

    }//end of test

}//end of class
