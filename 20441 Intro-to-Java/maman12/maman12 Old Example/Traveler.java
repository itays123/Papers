/**
 * Matala 13 - Using a class to represent a traveler
 * 
 * @author Israel Israeli
 * @version 2011a
 */
public class Traveler
{
    Passport _passport;
    boolean _isPayed;
    
    /**
    * creates a new Traveler object  
    * @param passport - the passport information of the traveler
    * @param isPayed - boolean value representing whether the traveler has already paid for his/her flight
    */       
    public Traveler(Passport passport,  boolean isPayed){
        _passport = new Passport(passport);
        _isPayed = isPayed;
    }
    
    
    
    public boolean checkTravel(Date travelDate){
        return  (isPayed() && _passport.isValid(travelDate));
    }
    
    /** 
     * returns whether the travler has paid or not for his/her trip
     * @return - value of _isPayed
     */  
    public boolean isPayed(){
        return _isPayed;
    }
    
      /**
       *  travler has paid for his/her trip 
    */     
        
    public void pay(){
        _isPayed = true;
    }
    
     /**
    * @return String that represents this traveler
    * in the following format:
    * Name: <name>
    * Pass. num: <number>
    * Exp date: <expiry date>
    */ 
    public String toString(){
        return _passport.toString();
    }
}
