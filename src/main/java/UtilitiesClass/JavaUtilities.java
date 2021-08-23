package UtilitiesClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class JavaUtilities {

    public static String getDateInFormatCalendar(int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, days);
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        return newDate;
    }//end of get date


    public static String getDateTime() {
        SimpleDateFormat sdfDateTime;
        String strDateTime;
        sdfDateTime = new SimpleDateFormat("yyyyMMdd'_'HHmmss'_'SSS");
        Date now = new Date();
        strDateTime = sdfDateTime.format(now);
        return strDateTime;
    }//end of getDateTime

    //method below allow you to remove leading zeros from string numbers
    public static String removeZero(String str)
    {
        // Count leading zeros
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0')
            i++;

        // Convert str into StringBuffer as Strings
        // are immutable.
        StringBuffer sb = new StringBuffer(str);

        // The  StringBuffer replace function removes
        // i characters from given index (0 here)
        sb.replace(0, i, "");

        return sb.toString();  // return in String
    }//end of removeZero method

    //method below will generate random unique id up to 20 characters
    public static String generateID(int num) {
        Random rnd = new Random();
        char [] digits = new char[num];
        digits[0] = (char) (rnd.nextInt(9) + '1');
        for(int i=1; i<digits.length; i++) {
            digits[i] = (char) (rnd.nextInt(10) + '0');
        }
        Long val = Long.parseLong(new String(digits));
        return val.toString();
    }//end of get up to 19 digits id

    /** method below will get a value from a variable in properties file **/
    public static String getPropValue(String variableName) throws IOException {
        String result = "";
        try{
            Properties prop=new Properties();
            FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
            prop.load(ip);
            result = prop.getProperty(variableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return result;
    }//end of getValueFromProperties


}//end of java class
