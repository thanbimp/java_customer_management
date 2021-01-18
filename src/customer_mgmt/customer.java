package customer_mgmt;

import static customer_mgmt.Main.contractList;
import static customer_mgmt.Main.customerList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author thanb
 */
public class customer {

   private int custID;
   private String firstName;
   private String lastName;
   private int taxID;
   private boolean isStudent;
   private boolean isPro;
   private String IDnum;
   private int paymentMethod;
   private final boolean eBill;
   private int discount;
   private static int noOfContracts;
   private float totalCost;
   
   public customer(int custID, String firstName,String lastName,int taxID,boolean isStudent,boolean isPro,String IDnum,int paymentMethod,boolean eBill){
    this.custID=custID;
    this.firstName=firstName;
    this.lastName=lastName;
    this.taxID=taxID;
    this.isStudent=isStudent;
    this.isPro=isPro;
    this.IDnum=IDnum;
    this.paymentMethod=paymentMethod;
    this.eBill=eBill;
    }
    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
     
    public int getTaxID() {
        return taxID;
    }

    public void setTaxID(int taxID) {
        this.taxID = taxID;
    }

    public boolean isIsStudent() {
        return isStudent;
    }

    public void setIsStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isIsPro() {
        return isPro;
    }

    public void setIsPro(boolean isPro) {
        this.isPro = isPro;
    }

    public String getIDnum() {
        return IDnum;
    }

    public void setIDnum(String IDnum) {
        this.IDnum = IDnum;
    }

    public int getDiscount() {
        
        return discount;
    }
    
    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void refreshCustStats() {
        totalCost=calcTotalCost(this.custID); //recalculate total monthly cost
        noOfContracts=getNoOfContracts(this.custID); //refresh number of contracts
        this.discount =calculateDIscount(noOfContracts>0,getActivecontracts(custID),this.custID,this.isStudent,this.isPro,checkfor1000MinsMobile(this.custID,contractList.size()),checkfor1000MinsFixed(this.custID,contractList.size()),this.paymentMethod,this.eBill); //refresh discount
    }
 
    private float calcTotalCost(int custID){
        int i;
        float sum=0;
        ArrayList <Integer> contractIndexes= new ArrayList<>();
      for(i=0;i<contractList.size();i++){
            if (custID==contractList.get(i).custID){ //get all indexes of customers contracts from ContractList
            contractIndexes.add(i);
        }
      }
      for(i=0;i<contractIndexes.size();i++){
          if(contractList.get(contractIndexes.get(i)).isActive){ //if contract is active
          sum=sum+contractList.get(contractIndexes.get(i)).cost; //sum up all the costs from all contracts
          }
      }
      return sum;
    }
    
    public static boolean existingCustomer(int taxID,String IDnum){
        boolean taxIDExists=false;
        boolean IDNumExists=false;
        int i;
        for (i=0; i<customerList.size(); i++){
            int tempTax;
            tempTax=customerList.get(i).getTaxID(); //check if tax id number exists in system
            if (tempTax==taxID){ 
                taxIDExists=true;
            }
        }
        for (i=0; i<customerList.size(); i++){
            String tempIdNum;
            tempIdNum=customerList.get(i).getIDnum(); //check if id number exists in system
            if (tempIdNum.equals(IDnum)){
                IDNumExists=true;
            }     
        }
        if (taxIDExists&&IDNumExists){
            System.out.println("Customer with tax id: "+taxID+" and Id Number: "+IDnum+" Already Exists");
            return true;
        }
        if (taxIDExists){
            System.out.println("Customer with tax id: "+taxID+" exists,but with another ID Number");
            return true;
        }
        if (IDNumExists){
            System.out.println("Customer with ID Number: "+IDnum+" exists,but with another tax ID");
            return true;
        }
        return false;
    }
     public static int genCustID(){
        Random rand = new Random();
        int num;
        num=rand.nextInt(100000); //generate random number as custID
        return num;
    }
    public int calculateDIscount(boolean IsValid,int noOfContracts,int custID,boolean isStudent,boolean isPro,int over1000MinutesMobile,int over1000MinutesFixed,int paymentMethod,boolean eBill){
    int contractDiscount;
    int propertyDIscount=0;
    int MethodDiscount=0;
    int ebillDiscount=0;
    int totalDiscount;
    int mobileMinuteDiscount;
    int fixedMinuteDiscount;
    if(noOfContracts>3){
        contractDiscount=15; //apply max contract discount
    }
    else{
        contractDiscount=5*noOfContracts;
    }
    if(isStudent){
        propertyDIscount=15;
    }
    if (isPro){
        propertyDIscount=10;
    }
    if(paymentMethod==2 || paymentMethod==3){
        MethodDiscount=5;
    }
    if(eBill){
        ebillDiscount=2;
    }
  mobileMinuteDiscount=over1000MinutesMobile*11;
  fixedMinuteDiscount=over1000MinutesFixed*8;
    totalDiscount=contractDiscount+propertyDIscount+MethodDiscount+ebillDiscount+mobileMinuteDiscount+fixedMinuteDiscount;
    if (totalDiscount>45){
        totalDiscount=45; //apply max discount 
    }
    if (!IsValid){
        totalDiscount=0; //discard discount if no contracts
    }
    return totalDiscount;
}
    Calendar cal = Calendar.getInstance();
    private int getSystemDay(){
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    private int getSystemMonth(){
        return cal.get(Calendar.MONTH)+1;
    }
    private int getSystemYear(){
        return cal.get(Calendar.YEAR);
    }
     public static int getNoOfContracts(int custID){
         ArrayList <Integer> contractIndexes= new ArrayList<>();
        int i;
        for(i=0;i<contractList.size();i++){
            if (custID==contractList.get(i).custID){ //get indexes of contracts 
            contractIndexes.add(i);
        }
    }
     return contractIndexes.size();
   
     }
     private int checkfor1000MinsMobile (int custID,int size){
         int i;
         int contracts=0;
          ArrayList <Integer> contractIndexes= new ArrayList<>();
         for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){ //get indexes of contracts 
            contractIndexes.add(i);
        }
     }
         for(i=0;i<contractIndexes.size();i++){
             if (contractList.get(contractIndexes.get(i)).isActive){
             if(contractList.get(contractIndexes.get(i)).minutes>1000){
                 if(contractList.get(contractIndexes.get(i)) instanceof mobile){ //if contract has over 1000 minutes and is mobile
                     contracts++;  //increment the counter
                 }
             }
         }
         }
         return contracts;
}
      private int checkfor1000MinsFixed (int custID,int size){
         int i;
         int contracts=0;
          ArrayList <Integer> contractIndexes= new ArrayList<>();
         for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
     }
         for(i=0;i<contractIndexes.size();i++){
             if (contractList.get(contractIndexes.get(i)).isActive){
             if(contractList.get(contractIndexes.get(i)).minutes>1000){
                 if(contractList.get(contractIndexes.get(i)) instanceof fixed){ //if contacts has over 1000 minutes and is fixed
                     contracts++; //increment the counter
                 }
             }
         }
         }
         return contracts;
}
public int getActivecontracts(int CustID){
    int activecontracts=0;
    int i;
    ArrayList <Integer> contractIndexes= new ArrayList<>();
      for(i=0;i<contractList.size();i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
      }
      int SystemDay=getSystemDay();
      int SystemMonth=getSystemMonth();
      int SystemYear=getSystemYear();
      int TotalSystemDate;
      int TotalEndDate;
      int TotalStartDate;
      TotalSystemDate=(SystemYear*365)+(SystemMonth*30)+(SystemDay); //calculate system date as sum of days
      for(i=0;i<contractIndexes.size();i++){
      int EndDay=contractList.get(contractIndexes.get(i)).getEndDay();
      int EndMonth=contractList.get(contractIndexes.get(i)).getEndMonth();
      int EndYear=contractList.get(contractIndexes.get(i)).getEndYear();
      TotalEndDate=(EndYear*365)+(EndMonth*30)+(EndDay);//calculate contract end date as sum of days
      int StartDay=contractList.get(contractIndexes.get(i)).getStartDay();
      int StartMonth=contractList.get(contractIndexes.get(i)).getStartMonth();
      int StartYear=contractList.get(contractIndexes.get(i)).getStartYear();
      TotalStartDate=(StartYear*365)+(StartMonth*30)+(StartDay);//calculate contract start date as sum of days
       if(TotalSystemDate>=TotalStartDate && TotalSystemDate<=TotalEndDate) { //if contract is active on todays date
            activecontracts++; //increment counter used for discount calculation
            contractList.get(contractIndexes.get(i)).setIsActive(true); //set active variable as true
        }
      }
      return activecontracts;
}

}