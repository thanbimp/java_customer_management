/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it219148;
import java.util.*;

/**
 *
 * @author thanb
 */
public class it219148 {
   static Scanner myScn=new Scanner(System.in);
   static ArrayList<customer> customerList = new ArrayList<customer>();
   static ArrayList<contract> contractList = new ArrayList<contract>();
   public static void main(String[] args) {
    //start of test data
   customerList.add(new customer(219148,"Thanasis","Bibas",123456789,true,false,"AB123456",1,true)); 
   customerList.add(new customer(12574,"Georgios","Theodorou",987654321,false,false,"AB454647",3,false));
   contractList.add(new mobile(true,10,150,"6980571234",219148,12,12,2022));
   contractList.add(new fixed (true,100,550,"21045671234",219148,12,6,2022));
   //end of test data
        boolean run=true;
        int selection;
        while(run){
        printMainMenu();
        selection=myScn.nextInt();
        switch (selection){
            case 1:
                newCustMenu();
                break;
            case 2:
                newContractMenu(0);
                break;
            case 3:
                deleteMenu();
                break;
            case 4:
                infoMenu();
                break;
            case 5:
                run=false;
                break;
        }
    }
}
   
   
   
   
   /*MENUS
   
   
   */
    public static void newCustMenu(){
   int custID;
   String firstName;
   String lastName;
   int taxID;
   boolean isStudent;
   boolean isPro;
   boolean eBill;
   boolean input;
   String IDnum;
   int paymentMethod;
   custID=customer.genCustID();
   System.out.println("Please Insert Customer's tax id");
   taxID=myScn.nextInt();
   System.out.println("Give customers id number;");
   IDnum=myScn.next();
   if(!customer.existingCustomer(taxID,IDnum)){
   System.out.println("Please Insert customer's first name");
   firstName=myScn.next();
   System.out.println("Please Insert Customer's last name");
   lastName=myScn.next();
   System.out.println("Is customer a student? (y/n)?");
   isStudent=stringToBool();
   System.out.println("Is customer a professional? (y/n)");
   isPro=stringToBool();
   System.out.println("Does customer want eBill (y/n)?");
   eBill=stringToBool();
   System.out.printf("How will Customer pay?\n1.Cash\n2.Credit Card\n3.Direct Debit\n");
   paymentMethod=myScn.nextInt();
   customerList.add(new customer(custID,firstName,lastName,taxID,isStudent,isPro,IDnum,paymentMethod,eBill));
   System.out.println("Would you like to add a contract to this customer now?(y/n)");
   input=stringToBool();
   if (input){
       newContractMenu(custID);
   }
   }
   }

    
    
    public static void newContractMenu(int custID){
        int input;
        boolean hasInternet;
        float internet=0;
        int day;
        int year = 0;
        int duration = 0;
        int month = 0;
        int minutes=0;
        boolean correctDuration=false;
        boolean checkForDate=false;
        boolean proceed=true;
        String phoneNo = null;
        System.out.printf("What kind of contract would you like to add?\n1.Mobile\n2.Fixed\n");
        input=myScn.nextInt();
        if(custID==0){
            System.out.println("Please enter customers tax ID");
            custID=findCustID(myScn.nextInt());
        }
        if (custID!=0){
        if(input==1){
            do{
                System.out.println("Please enter the phone number for the contract");
                phoneNo=myScn.next();
            }while(!mobile.validNumber(phoneNo));
            if(contract.numberExists(phoneNo)){
                System.out.println("Contract for number "+phoneNo+" already exists, new contract must start from: "+contractList.get(getContractIndex(custID,1)).getEndDay()+"/"+contractList.get(getContractIndex(custID,1)).getEndMonth()+"/"+contractList.get(getContractIndex(custID,1)).getEndYear());
                checkForDate=true;
            }
            System.out.println("Enter contract starting day");
            day=myScn.nextInt();
            System.out.println("Enter contract starting month");
            month=myScn.nextInt();
            System.out.println("Enter contract starting year");
            year=myScn.nextInt();
            if (checkForDate){
            proceed=!overlapCheck(day,month,year,custID,1);
            }
            if (proceed){
            while (!correctDuration){
            System.out.println("Please Enter Contract duration(12/18/24)");
            duration=myScn.nextInt();
            correctDuration=durationCheck(duration);
            }
                System.out.println("Please Enter Amount of Minutes");
                minutes=myScn.nextInt();
            System.out.println("Does customer want internet?(y/n)");
            hasInternet=stringToBool();
            if (hasInternet){
                System.out.println("Please enter amount of internet in GB (If less than 1GB,enter as decimal (e.g 0,5))");
                internet=myScn.nextFloat();
            }
            year=caluclateYear(duration,year,month);
            month=caluclateMonth(duration,year,month);
            contractList.add(new mobile(hasInternet,internet,minutes,phoneNo,custID,day,month,year));
            }
            else{
                System.out.println("Contract date overlap,please try again");
            }
        }
       if(input==2){
            do{
                System.out.println("Please enter the phone number for the contract");
                phoneNo=myScn.next();
            }while(!fixed.validNumber(phoneNo));
            if(contract.numberExists(phoneNo)){
                System.out.println("Contract for number "+phoneNo+" already exists, new contract must start from: "+contractList.get(getContractIndex(custID,2)).getEndDay()+"/"+contractList.get(getContractIndex(custID,2)).getEndMonth()+"/"+contractList.get(getContractIndex(custID,2)).getEndYear());
                checkForDate=true;
            }
            System.out.println("Enter contract starting day");
            day=myScn.nextInt();
            System.out.println("Enter contract starting month");
            month=myScn.nextInt();
            System.out.println("Enter contract starting year");
            year=myScn.nextInt();
            if (checkForDate){
            proceed=!overlapCheck(day,month,year,custID,2);
            }
            if (proceed){
            while (!correctDuration){
            System.out.println("Please Enter Contract duration(12/18/24)");
            duration=myScn.nextInt();
            correctDuration=durationCheck(duration);
            }
                System.out.println("Please Enter Amount of Minutes");
                minutes=myScn.nextInt();
            System.out.println("Does customer want internet?(y/n)");
            hasInternet=stringToBool();
            if (hasInternet){
                System.out.println("Please enter internet speed in Mbps");
                internet=myScn.nextFloat();
            }
            year=caluclateYear(duration,year,month);
            month=caluclateMonth(duration,year,month);
            contractList.add(new fixed(hasInternet,internet,minutes,phoneNo,custID,day,month,year));
            }
            else{
                System.out.println("Contract date overlap,please try again");
            }
        }
        }
        System.out.println("Customer not found");
    }
    
    
    public static void deleteMenu(){
        
    }
    
    public static void infoMenu(){
        
    }
    
    public static void printMainMenu(){
        System.out.println("MAIN MENU");
        System.out.println("Please make a selection");
        System.out.println("1.Add New Customer");
        System.out.println("2.Make New Contract");
        System.out.println("3.Delete");
        System.out.println("4.View Active Contracts & Statistics");
        System.out.println("5.Exit");
        System.out.printf("Selection: ");
}
    
    
    
    
    /* Βοηθητικές μεθόδοι
    */
    public static boolean stringToBool(){
        boolean answer=false;
        boolean correctans=false;
                while(!correctans){
        String input=myScn.next();
        if (input.equalsIgnoreCase("y")){
            answer=true;
            correctans=true;
        }
        else if(input.equalsIgnoreCase("n")){
            correctans=true;
        }
        else{
        System.out.println("Wrong Input! Please try again");
        correctans=false;
    }
                }
        return answer;
    }
    
   
    
    
 public static int  caluclateYear(int duration,int year,int month){
    if (duration ==24){
        year=year+2;
        return year;
    }
    if (duration ==18){
        if (month+6>12){
            year=year+2;
            return year;
        }
        else{
            year=year+1;
            return year;
        }
    }
    if (duration == 12){
        year=year+1;
        return year;
    }
    return 0;
}
 
  public static int caluclateMonth(int duration,int year,int month){
      if (duration ==18){
        if (month+6>12){
            month=month-6;
            return month;
        }
        month=month+6;
        return month;
      }
      return month;
  }
  public static int findCustID(int taxID){
    int i=0;
    int tempCustID=0;
    for (i=0; i<customerList.size(); i++){
            int tempTaxID;
            tempTaxID=customerList.get(i).getTaxID();
            if (taxID==tempTaxID){
                tempCustID=customerList.get(i).getCustID();
            }
}
    return tempCustID;
}
  public static boolean durationCheck(int duration){
      if (duration == 24){
          return true;
      }
      if (duration == 18){
          return true;
      }
      if (duration == 12){
          return true;
      }
      else{
          System.out.println("Wrong Duration,Try Again!");
      }
      return false;
  }
  public static int getContractIndex(int custID,int type){
      int i;
      int index=0;
      if (type==1){
      for (i=0; i<contractList.size(); i++){
            int tempCustID;
            tempCustID=contractList.get(i).getCustID();
            if (custID==tempCustID && contractList.get(i) instanceof mobile) {
                index=i;
            }
  }
      }
         if (type==2){
      for (i=0; i<contractList.size(); i++){
            int tempCustID;
            tempCustID=contractList.get(i).getCustID();
            if (custID==tempCustID && contractList.get(i) instanceof fixed) {
                index=i;
            }
  }
      }
      return index;
}
   public static boolean overlapCheck (int day,int month,int year,int custID,int type){
  boolean overlap=false;
  boolean dayViolation=false;
  boolean yearViolation=false;
  boolean monthViolation=false;
  if (year<contractList.get(getContractIndex(custID,type)).getEndYear()){
       yearViolation=true;
   }
  if (month<contractList.get(getContractIndex(custID,type)).getEndMonth() && yearViolation){
      monthViolation=true;
  }
  if (day<=contractList.get(getContractIndex(custID,type)).getEndDay() && monthViolation){
      dayViolation=true;
  }
  if (dayViolation || monthViolation || yearViolation){
      overlap=true;
  }
  return overlap;
  }
}