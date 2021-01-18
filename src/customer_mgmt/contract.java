package customer_mgmt;

import static customer_mgmt.Main.contractList;

/**
 *
 * @author thanb
 */
public class contract{
    float internet;
    boolean hasInternet;
    int minutes;
    String phoneNo;
    int custID;
    int endDay;
    int endMonth;
    int endYear;
    int startDay;
    int startMonth;
    int startYear;
    boolean isActive;
    float cost;
    int noOfContract;
    

    public contract (boolean hasInternet,float internet,int minutes,String phoneNo,int custID,int startDay,int startMonth,int startYear,int endDay,int endMonth,int endYear){
    this.isActive = false;
    this.hasInternet=hasInternet;
    this.internet=internet;
    this.minutes=minutes;
    this.phoneNo=phoneNo;
    this.custID=custID;
    this.endDay=endDay;
    this.endMonth=endMonth;
    this.endYear=endYear;
    this.startDay=startDay;
    this.startMonth=startMonth;
    this.startYear=startYear;
    cost=calulateCost();
    noOfContract=customer.getNoOfContracts(custID)+1;//offset number of contracts by one,because cintract count starts from zero
    }

    public float getCost() {
        return cost;
    }


    public float  getInternet() {
        return internet;
    }


    public void setInternet(int internet) {
        this.internet = internet;
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public int getMinutes() {
        return minutes;
    }


    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public String getPhoneNo() {
        return phoneNo;
    }


    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public int getCustID() {
        return custID;
    }


    public void setCustID(int custID) {
        this.custID = custID;
    }

 
    public static boolean numberExists(String phoneNo){ //check if number exists
        int i;
        for (i=0; i<contractList.size(); i++){
           String tempPhNo;
            tempPhNo=contractList.get(i).getPhoneNo();
            if (tempPhNo.equalsIgnoreCase(phoneNo)){
                return true;
            }
            }
        return false;
    }

  
    public int getEndDay() {
        return endDay;
    }


    public  int getEndMonth() {
        return endMonth;
    }


    public int getEndYear() {
        return endYear;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }
      
    private float calulateCost(){
        float costOfMins;
        float MinutePrice = 0.02f;
        float costOfInternet = 0;
        float GBPrice = 1.5f;
        float MBPSPrice=0.3f;
        costOfMins=this.minutes*MinutePrice;
        if(this instanceof mobile){ //if mobile use gigabyte cost
        costOfInternet=this.internet*GBPrice;
        }
        if (this instanceof fixed){
            costOfInternet=internet*MBPSPrice; //if fixed use mbps cost
        }
        return costOfMins+costOfInternet;
    }
    public String findLineType() {
       return null; //placeholder
    }
}

    
   
class fixed extends contract{
        
    public fixed(boolean hasInternet, float internet, int minutes, String phoneNo,int custID,int startDay,int startMonth,int startYear,int endDay,int endMonth,int endYear) {
        super(hasInternet, internet, minutes, phoneNo, custID,startDay,startMonth,startYear, endDay,endMonth,endYear);
    } String LineType=findLineType();
        public static  boolean validNumber(String phoneNo){ //checks if number has ten digits and starts from 2
    if ((phoneNo.startsWith("2")) && (phoneNo.length()==10)){
        return true;
    }else{
        System.out.println("Number is wrong,try again!");
    }
    return false;
}
    @Override
    public String findLineType() { //gets internet speed and returns line type
        String temp="";
        if(this.internet<=24){
            temp="ADSL";
        }
        else if (this.internet <=200){
            temp="VDSL";
        }
        else if (this.internet >200){
            temp="Fiber to the home";
        }
        return temp;
    }


    }

class mobile extends contract{
   
   public mobile(boolean hasInternet, float internet, int minutes, String phoneNo,int custID,int startDay,int startMonth,int startYear,int endDay,int endMonth,int endYear) {
        super(hasInternet, internet, minutes, phoneNo, custID,startDay,startMonth,startYear, endDay,endMonth,endYear);
    }
    public static  boolean validNumber(String phoneNo){ //checks if number is 10 digits and starts with 69
    if ((phoneNo.startsWith("69")) && (phoneNo.length()==10)){
        return true;
    }else{
        System.out.println("Number is wrong,try again!");
    }
    return false;
}
    }