

/**
 * Matala 13 - Using a class to represent a given date in the Gregorian Calendar
 * 
 * @author Israel Israeli
 * @version 2011a
 */

public class Date {
    private int _day;
    private int _month;
    private int _year;

    //constructors:
    /**
    * creates a new Date object
    * @param _day the day in the month(1-31)
    * @param _month the month in the year
    * @param _year the year (in 4 digits)
    */

    public Date(int day, int month, int year) {
        _day = day;
        _month = month;
        _year = year;
    }
    
    /**
    * Copy Constructor
    * @param passport to be copied
    */
    public Date(Date date){
        _day = date._day;
        _month = date._month;
        _year = date._year;
    }
    
    /** gets the year */
    public int getYear(){
        return _year;
    }
    
    /** gets the month */
    public int getMonth(){
        return _month;
    }
    
    /** gets the Day */
    public int getDay(){
        return _day;
    }
    
    /** sets the year
     * @param yearToSet the value to be set
     */
    public void setYear(int yearToSet){
        _year = yearToSet;
    }
    
    /** set the month
     * @param monthToSet the value to be set
     */
    public void setMonth(int monthToSet){
        _month = monthToSet;
    }
    
    /** sets the day 
     *  @param dayToSet the value to be set
     */
    public void  setDay(int dayToSet){
        _day = dayToSet;
    }

    /**
    * checks if this date comes before a given date
    * @param date2 the given date
    * @retun true iff this date comes before date2
    */

    public boolean before(Date date2) {
        if (_year < date2._year)
            return true;
        if (_year == date2._year) {
            if (_month < date2._month)
                return true;
            if (_month == date2._month && _day < date2._day)
                return true;
        }
        return false;
    }


    /**
    * @return String that represents this date
    * in the following format:
    * day.month.year (30/9/1917)
    */
    public String toString() {
        return _day +"/" + _month + "/" + _year;
    }
}
