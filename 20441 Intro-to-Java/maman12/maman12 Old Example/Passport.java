/**
 * Matala 13 - Using a class to valid passport
 * 
 * @author Israel Israeli
 * @version 2011a
 */

public class Passport
{
    String _name;
    int _number;
    Date _expiryDate;
    
    /**
    * creates a new Passport object  
    * @param name - the passport owner's name
    * @param number - the passport owner's id number
    * @param expiryDate - the passport's expiration date
    */     
    
    public Passport(String name, int number, Date expiryDate)
    {
        _name = new String(name);
        _number = number;
        _expiryDate = new Date(expiryDate);  
   }
    
     /**
    * Copy Constructor
    * @param other to be copied
    */
   
    public Passport(Passport other)
    {
        _name = new String(other._name);
        _number = other._number;
        _expiryDate = new Date(other._expiryDate);        
    }
    
  /**  gets the name
     * @return the name
     */         
    public String getName(){
        return new String (_name);
    }
   
     /**  gets the expiration date
     * @return the expiration date
     */      
    
      public Date getExpiryDate(){
        return new Date (_expiryDate);
    }
    
     /** sets the name
        * @param name the value to be set
     */
    
      public void setName(String name){
       _name = new String(name);
    }
    
     /** sets the expiration date
        * @param newExpDate the value to be set
     */
    
         public void setExpiryDate(Date newExpDate){
        _expiryDate =  new Date (newExpDate);
    }
    
     /**  determines if a passport is valid or not.
      * if the date specified by the parameter dateChecked is on or before the expiration date on the passport 
      * then the passport is valid, otherwise it is not
        * @param dateChecked the date that the passport date is to be compared to
        * @return - true iff the dateChecked is on or before the passport date
     */  
    
    public boolean isValid(Date dateChecked) {
        if ((_expiryDate.getDay() == dateChecked.getDay()) &&(_expiryDate.getMonth() == dateChecked.getMonth()) && 
            _expiryDate.getYear() == dateChecked.getYear())
                return true;
         else 
            return dateChecked.before(getExpiryDate());

        }
        
   /**
    * @return String that represents this passport
    * in the following format:
    * Name: <name>
    * Pass. num: <number>
    * Exp date: <expiry date>
    */  
   
    public String toString(){
        return "Name: " + _name + "\n"  + "Pass. num: " + _number + "\n" + "Exp date: " + _expiryDate;
        
    }
    
}
