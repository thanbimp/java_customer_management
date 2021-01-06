/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_mgmt;
import java.util.*;

/**
 *
 * @author thanb
 */
public class main {
   static Scanner myScn=new Scanner(System.in);
  static ArrayList<customer> customerList = new ArrayList<customer>();
  public static ArrayList<contract> contractList = new ArrayList<contract>();
   public static void main(String[] args) {
    //start of test data
   customerList.add(new customer(219148,"Thanasis","Bibas",123456789,true,false,"AB123456",1,true)); 
   customerList.add(new customer(12574,"Georgios","Theodorou",987654321,false,false,"AB454647",3,false));
   contractList.add(new mobile(true,10,150,"6980571234",219148,12,12,2022));
   contractList.add(new fixed (true,100,550,"2105671234",219148,12,6,2022));
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
                deleteMenu(contractList.size());
                break;
            case 4:
                showContractsMenu(contractList.size());
                break;
            case 5:
                run=false;
                break;
            case 6:
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
           if (minutes<=0 && internet<=0){  
                System.out.println("Contract doesnt have a value over 0,and wont be saved");
            }
            else{
                contractList.add(new mobile(hasInternet,internet,minutes,phoneNo,custID,day,month,year));
            }
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
            if (minutes<=0 && internet<=0){  
                System.out.println("Contract doesnt have a value over 0,and wont be saved");
            }
            else{
                contractList.add(new fixed(hasInternet,internet,minutes,phoneNo,custID,day,month,year));
            }
            }
            else{
                System.out.println("Contract date overlap,please try again");
            }
        }
        }
        else{
        System.out.println("Customer not found");
    }
    }
    
    
    public static void deleteMenu(int size){
        ArrayList <Integer> contractIndexes= new ArrayList<Integer>();
        int i=0;
        System.out.println("Please Enter customers tax ID:");
        int custID=findCustID(myScn.nextInt());
        for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
    }
        System.out.println("The Following contracts have been found:");
        for (i=0;i<contractIndexes.size();i++){
            printContract(contractIndexes.get(i),i,custID);
        }
        if (contractIndexes.size()>=1){
        System.out.println("Please choose which contract to delete");
       int input=myScn.nextInt();
       int TempIndex=contractIndexes.get(input-1);
       contractList.remove(TempIndex);
        }
        else{
            System.out.println("No contracts found");
        }
       
       
    }
    
    public static void showContractsMenu(int size){
         ArrayList <Integer> contractIndexes= new ArrayList<Integer>();
        int i=0;
        System.out.println("Please Enter customers tax ID:");
        int custID=findCustID(myScn.nextInt());
        for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
    }
        if(contractIndexes.size()>0){
            
        System.out.println("The Following contracts have been found:");
        for (i=0;i<contractIndexes.size();i++){
            printContract(contractIndexes.get(i),i,custID);
        }
        customerList.get(findcustIndex(custID)).refreshDiscount();
         System.out.println("Additionally,selected customer has a total discount of:"+customerList.get(findcustIndex(custID)).getDiscount()+"%");
        }
        else{
            System.out.println("No contracts found");
            System.out.println("Additionally,selected customer has a total discount of: "+"0%"+" (No active contracts)");
        }
    }
    
    public static void printMainMenu(){
        System.out.println("MAIN MENU");
        System.out.println("Please make a selection");
        System.out.println("1.Add New Customer");
        System.out.println("2.Make New Contract");
        System.out.println("3.Delete");
        System.out.println("4.View Active Contracts");
        System.out.println("5.View Statistics");
        System.out.println("6.Exit");
        System.out.printf("Selection: ");
}
    
    
    
    
    /* Auxiliary Methods
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
   
   public static void printContract(int index,int currRun,int custID){
       System.out.printf("%d.\n",currRun+1);
       System.out.print("Type:");
       if(contractList.get(index) instanceof mobile){
           System.out.println("Mobile");
       }
       else if(contractList.get(index) instanceof fixed){
           System.out.println("Fixed");
       }
       System.out.println("Phone Number:"+contractList.get(index).phoneNo);
       System.out.println("Minutes:"+contractList.get(index).minutes);
       System.out.println("Expiration Date:"+contractList.get(index).endDay+"/"+contractList.get(index).endMonth+"/"+contractList.get(index).endYear);
       if(contractList.get(index).hasInternet){
           System.out.print("Internet:");
           if (contractList.get(index) instanceof mobile){
               System.out.println(contractList.get(index).internet+" GB");
           }
           else  if (contractList.get(index) instanceof fixed){
               System.out.println(contractList.get(index).internet+" Mbps");
           }
       }
       }
   public static int findcustIndex(int CustID){
       int i;
       for (i=0;i<customerList.size();i++){
           if (customerList.get(i).getCustID()==CustID){
               return i;
           }
       }
       return 0;
   }

}