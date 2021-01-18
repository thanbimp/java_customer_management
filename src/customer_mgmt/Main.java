package customer_mgmt;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author thanb
 */
public class Main {
   static Scanner myScn=new Scanner(System.in); //initialize scanner
  static ArrayList<customer> customerList = new ArrayList<customer>();  //initialize AraayList with customers
  public static ArrayList<contract> contractList = new ArrayList<contract>(); //initialize ArrayList with contracts
   public static void main(String[] args) {
    //start of test data
   customerList.add(new customer(219148,"Thanasis","Bibas",123456789,true,false,"AB123456",1,true)); 
   customerList.add(new customer(12574,"Georgios","Theodorou",987654321,false,false,"AB454647",3,false));
   contractList.add(new mobile(true,10,150,"6980571234",219148,12,12,2020,12,12,2022));
   contractList.add(new fixed (true,100,550,"2105671234",219148,12,12,2020,12,6,2022));
   contractList.add(new mobile(true,10,150,"6989871234",12574,12,12,2020,12,12,2022));
   customerList.get(findcustIndex(219148)).refreshCustStats(); //refresh discount for test customer 1
   customerList.get(findcustIndex(12574)).refreshCustStats();   //refresh discount for test customer 2
   //end of test data
        boolean run=true; //this boolean changes to false when the exit option is selected
        String selection; //main menu user input
        while(run){
        printMainMenu(); //print main menu options
        
        selection=(myScn.next());
        switch (selection){
            case "1":
                newCustMenu(); //begin new customer addition
                break;
            case "2":
                newContractMenu(0); //begin new contract addition
                break;
            case "3":
                deleteMenu(contractList.size()); //begin contract deletion
                break;
            case "4":
                showContractsMenu(contractList.size()); //begin contract listing
                break;
            case "5":
                statisticsMenu(contractList.size()); // begin statisctics calculation and printing
                break;
            case "6":
            run=false; //terminate program
            break;
            default:
           System.out.println("Wrong Selection!");
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
   boolean isPro=false;
   boolean eBill;
   boolean input;
   String IDnum;
   int paymentMethod = 0;
   custID=customer.genCustID();
   System.out.println("Please insert your tax id:");
   taxID=taxIDInput();
   System.out.println("Please insert your id number:");
   do{
   IDnum=myScn.next();
    if (! (IDnum.length()==8 && Character.isLetter(IDnum.charAt(0)) && Character.isLetter(IDnum.charAt(1)))){
           System.out.println("Wrong ID number,try again!");
       }
   }while(!(IDnum.length()==8 && Character.isLetter(IDnum.charAt(0)) && Character.isLetter(IDnum.charAt(1))));
   if(!customer.existingCustomer(taxID,IDnum)){ //check if customer already in system,if customer not in system proceed with addition
   System.out.println("Please insert your first name");
   firstName=myScn.next();
   System.out.println("Please insert your last name");
   lastName=myScn.next();
   System.out.println("Are you a student (y/n)?");
   isStudent=stringToBool();
   if(!isStudent){ //program asks if customer is a proffesional only if customer is not a student
   System.out.println("Are you a professional? (y/n)");
   isPro=stringToBool();
   }
   System.out.println("Do you want eBill (y/n)?");
   eBill=stringToBool();
   do{if(paymentMethod>3 && paymentMethod<=0){ //only prints if payment method is out of range
       System.out.println("Invalid payment Method!");
   }
   System.out.printf("How will you pay?\n1.Cash\n2.Credit Card\n3.Direct Debit\n");
   paymentMethod=intInput();
   }while(paymentMethod>3 && paymentMethod<=0); //ask again for payment method if input is out of range
   customerList.add(new customer(custID,firstName,lastName,taxID,isStudent,isPro,IDnum,paymentMethod,eBill));
   System.out.println("Would you like to add a contract now?(y/n)");
   input=stringToBool();
   if (input){
       newContractMenu(custID);//if anser is yes,go to add new contract
   }
   }
   }

    
    
    public static void newContractMenu(int custID){
        int input;
        boolean hasInternet;
        float internet=-1;
        int day;
        int year = 0;
        int duration = 0;
        int month = 0;
        int minutes;
        int endYear;
        int endMonth;
        boolean correctDuration=false;
        boolean checkForDate=false;
        boolean proceed=true;
        String phoneNo = null;
        System.out.printf("What kind of contract would you like to add?\n1.Mobile\n2.Fixed\n");
        input=intInput();
        if(custID==0){ //if called from main menu custID is 0,so it has to be found
            if(input==1||input==2){
            System.out.println("Please enter your tax ID");
            custID=findCustID(taxIDInput());//find custID from tax id
            }
        }
        if (custID!=0){ //only proceed if customer found
        if(input==1){ // new mobile contract procedure
            do{
                System.out.println("Please enter the phone number for the contract");
                phoneNo=myScn.next();
            }while(!mobile.validNumber(phoneNo));
            if(contract.numberExists(phoneNo)){ // check if contract with inputted number exists
                System.out.println("Contract for number "+phoneNo+" already exists, new contract must start from: "+contractList.get(getContractIndex(custID,1)).getEndDay()+"/"+contractList.get(getContractIndex(custID,1)).getEndMonth()+"/"+contractList.get(getContractIndex(custID,1)).getEndYear()); // if contract exists print expiration date
                checkForDate=true; //this tells the program that input date must be checked to avoid overlap
            }
             System.out.println("Enter contract starting day");
            do{
            day=intInput();
            if(day>31 || day<0){
                System.out.println("Wrong input! Enter a day between 1 and 31");
            }
            }while(day>31 || day<0);
            System.out.println("Enter contract starting month");
           do{
            month=intInput();
            if(month>12 || month<0){
                System.out.println("Wrong input! Enter a month between 1 and 12");
            }
            }while(month>12 || month<0);
            System.out.println("Enter contract starting year");
            do{
            year=intInput();
            if(year>100000){
                System.out.println("Wrong input! Enter a four digit year");
            }
            }while(year>10000);
            if (checkForDate){ //if true,this starts the overlap check
            proceed=!overlapCheck(day,month,year,custID,1);//if no overlap proceed is true
            }
            if (proceed){ //if no overlap,or not existing contract
            while (!correctDuration){ //break out of loop only when correct duration is inputted
            System.out.println("Please Enter Contract duration(12/18/24)");
            duration=intInput();
            correctDuration=durationCheck(duration);//check if correct input is given 
            }
                System.out.println("Please Enter Amount of Minutes");
                minutes=intInput();
            System.out.println("Do you want internet?(y/n)");
            hasInternet=stringToBool(); 
            if(hasInternet){//runs only if previous question answered positively
                System.out.println("Please enter amount of internet in GB (If less than 1GB,enter as decimal (e.g 0,5)");
                internet=FloatInput();
            }
            endYear=caluclateYear(duration,year,month); //calculate contract end year
            endMonth=caluclateMonth(duration,year,month); //calculate contract end month
           if (minutes<=0 && internet<=0){  //check if contract has atleast one postitive usage parameter
                System.out.println("Contract doesnt have a value over 0,and wont be saved");
            }
            else{
                contractList.add(new mobile(hasInternet,internet,minutes,phoneNo,custID,day,month,year,day,endMonth,endYear)); //if contract is considered valid add it to the arrayList
                 customerList.get(findcustIndex(custID)).refreshCustStats(); //recalculate discount and total cost
            }
            }
            else{
                System.out.println("Contract date overlap,please try again"); //overlap detected error
            }
        }
        if(input==2){// new fixed contract procedure
             do{
                System.out.println("Please enter the phone number for the contract");
                phoneNo=myScn.next();
            }while(!fixed.validNumber(phoneNo));
            if(contract.numberExists(phoneNo)){// check if contract with inputted number exists
                System.out.println("Contract for number "+phoneNo+" already exists, new contract must start from: "+contractList.get(getContractIndex(custID,2)).getEndDay()+"/"+contractList.get(getContractIndex(custID,2)).getEndMonth()+"/"+contractList.get(getContractIndex(custID,2)).getEndYear()); // if contract exists print expiration date
                checkForDate=true;//this tells the program that input date must be checked to avoid overlap
            }
            System.out.println("Enter contract starting day");
            do{
            day=intInput();
            if(day>31 || day<0){
                System.out.println("Wrong input! Enter a day between 1 and 31");
            }
            }while(day>31|| day<0);
            System.out.println("Enter contract starting month");
           do{
            month=intInput();
            if(month>12 || month<0){
                System.out.println("Wrong input! Enter a month between 1 and 12");
            }
            }while(month>12 || month<0);
            System.out.println("Enter contract starting year");
            do{
            year=intInput();
            if(year>100000){
                System.out.println("Wrong input! Enter a four digit year");
            }
            }while(year>10000);
            if (checkForDate){//if true,this starts the overlap check
            proceed=!overlapCheck(day,month,year,custID,2);//if no overlap proceed is true
            }
            if (proceed){//if no overlap,or not existing contract
            while (!correctDuration){//break out of loop only when correct duration is inputted
            System.out.println("Please Enter Contract duration(12/18/24)");
            duration=intInput();
            correctDuration=durationCheck(duration);//check if correct input is given 
            }
                System.out.println("Please Enter Amount of Minutes");
                minutes=intInput();
            System.out.println("Does customer want internet?(y/n)");
            hasInternet=stringToBool();
            if (hasInternet){//runs only if previous question answered positively
                do{
                    if(internet==0){
                        System.out.println("Wrong input,try again!");
                    }
                System.out.println("Please enter internet speed in Mbps");
                internet=FloatInput();
            }while(internet==0);
            }
            endYear=caluclateYear(duration,year,month);//calculate contract end year
            endMonth=caluclateMonth(duration,year,month);//calculate contract end month
            if (minutes<=0 && internet<=0){   //check if contract has atleast one postitive usage parameter
                System.out.println("Contract doesnt have a value over 0,and wont be saved");
            }
            else{
               contractList.add(new fixed(hasInternet,internet,minutes,phoneNo,custID,day,month,year,day,endMonth,endYear));//if contract is considered valid add it to the arrayList
               customerList.get(findcustIndex(custID)).refreshCustStats();//recalculate discount and total cost
            }
            }
            else{
                System.out.println("Contract date overlap,please try again");//overlap detected error
            }
        }
    }
        else{
            System.out.println("Invalid Selection,or customer not found");
        }
        }

    
    public static void deleteMenu(int size){
        ArrayList <Integer> contractIndexes= new ArrayList<>(); //this contains all the indexes in contractList that have contracts with given customer id
        int i;
        int input;
        System.out.println("Please enter your tax ID:");
        int custID=findCustID(taxIDInput()); //find customer id from tax id
        for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){ //if given customer id matches customer id on contract get its index and add it to contractIndexes
            contractIndexes.add(i);
        }
    }
        System.out.println("The Following contracts have been found:");
        for (i=0;i<contractIndexes.size();i++){
            printContract(contractIndexes.get(i),custID); //prints contract with index given from contractIndexes
        }
        if (contractIndexes.size()>=1){
        System.out.println("Please choose which contract to delete");
        do{
       input=intInput(); //gets user input
       if(input<=contractIndexes.size() && input>0){
       int TempIndex=contractIndexes.get(input-1); //gets index from contractIndexes according to user input, -1 is because arraylists start at 0
       contractList.remove(TempIndex); //deletes contract 
       customerList.get(findcustIndex(custID)).refreshCustStats();  //recalculates discount and total cost
       }
       else if (input>contractIndexes.size() || input<=0){
           System.out.println("Invalid Input,choose a number from the list!");
       }
        }while(input>contractIndexes.size() || input <=0);
        }
        else{
            System.out.println("No contracts ,or customer found"); //errror message
        }
       
       
    }
    
    public static void showContractsMenu(int size){
        ArrayList <Integer> contractIndexes= new ArrayList<>();//this contains all the indexes in contractList that have contracts with given customer id
        int i;
        System.out.println("Please enter your tax ID:");
        int custID=findCustID(taxIDInput()); //find customer id from tax id
        customerList.get(findcustIndex(custID)).refreshCustStats();//refresh current customer
        for(i=0;i<size;i++){//if given customer id matches customer id on contract get its index and add it to contractIndexes
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
    }
        if(contractIndexes.size()>0){
            
        System.out.println("The Following contracts have been found:");
        for (i=0;i<contractIndexes.size();i++){
            printContract(contractIndexes.get(i),custID); //print all contracts
        }
         System.out.println("Total monthly cost of active contracts:"+customerList.get(findcustIndex(custID)).getTotalCost()+"€");
         System.out.println("Discount:"+customerList.get(findcustIndex(custID)).getDiscount()+"%"); //show discount
         float discountAmount=(customerList.get(findcustIndex(custID)).getTotalCost())*((float)(customerList.get(findcustIndex(custID)).getDiscount())/(100));
         System.out.println("Total cost after discount:"+(customerList.get(findcustIndex(custID)).getTotalCost()-discountAmount)+"€");
        }
        else{
            System.out.println("No contracts or customer found"); // error message
        }
    }
    
    public static void printMainMenu(){ //prints main menu
        System.out.println("MAIN MENU");
        System.out.println("Please make a selection");
        System.out.println("1.Add New Customer");
        System.out.println("2.Make New Contract");
        System.out.println("3.Delete");
        System.out.println("4.View Contracts");
        System.out.println("5.View Statistics");
        System.out.println("6.Exit");
        System.out.printf("Selection: ");
}
    
     private static void statisticsMenu(int size) {
        int minminutesfixed=999999999; //set to very big number,for first run
        int maxminutesfixed=0; //set to zero,for first run
        int summinutesfixed=0;
        int mininternetfixed=999999999;  //set to very big number,for first run
        int maxinternetfixed=0;//set to zero,for first run
        int suminternetfixed=0;
        int amountfixed=0;//contract counter
        int minminutesmobile=999999999;  //set to very big number,for first run
        int maxminutesmobile=0;//set to zero,for first run
        int summinutesmobile=0;
        float mininternetmobile=999999999;  //set to very big number,for first run
        float maxinternetmobile=0;//set to zero,for first run
        float suminternetmobile=0;
        int amountmobile=0;//contract counter
        int i;
        float mobAvgMinutes;
        float mobAvgInternet;
        float fixAvgMinutes;
        float fixAvgInternet;
        for (i=0;i<size;i++){
            if(contractList.get(i) instanceof mobile){ //check if contract is mobile,in order to calculate for the mobile segment
                amountmobile++; //add to counter,used for average caluclation
                int curContractMinutes=contractList.get(i).getMinutes(); //get contracts minutes
                summinutesmobile=summinutesmobile+curContractMinutes; //add contract minutes to sum,used to calculate average
                if (curContractMinutes<minminutesmobile){ //check if minutes are smaller than minimum
                    minminutesmobile=curContractMinutes;//if smaller than minimum set as the new minimum
                }
                 if (curContractMinutes>maxminutesmobile){//check if minutes are bigger than maximum
                    maxminutesmobile=curContractMinutes;//if bigger than maximum set as the new maximum
                }
                float curContractInternet=contractList.get(i).getInternet();// get contract internet
                suminternetmobile=suminternetmobile+curContractInternet;//add interent amount to sum,used to calculate average
                if (curContractInternet<mininternetmobile){//check if internet is smaller than minimum
                    mininternetmobile=curContractInternet;//if smaller than minimum set as the new minimum
                }
                 if (curContractInternet>maxinternetmobile){//check if internet is bigger than maximum
                    maxinternetmobile=curContractInternet;//if bigger than maximum set as the new maximum
                }
            }
            if(contractList.get(i) instanceof fixed){  //fixed contract calculations, work the same as above
                amountfixed++;
                int curContractMinutes=contractList.get(i).getMinutes();
                summinutesfixed=summinutesfixed+curContractMinutes;
                if (curContractMinutes<minminutesfixed){
                    minminutesfixed=curContractMinutes;
                }
                 if (curContractMinutes>maxminutesfixed){
                    maxminutesfixed=curContractMinutes;
                }
                int curContractInternet=(int)contractList.get(i).getInternet();
                suminternetfixed=suminternetfixed+curContractInternet;
                if (curContractInternet<mininternetfixed){
                    mininternetfixed=curContractInternet;
                }
                 if (curContractInternet>maxinternetfixed){
                    maxinternetfixed=curContractInternet;
                }
            }
        }
        mobAvgInternet=suminternetmobile/amountmobile; //calcute internet mobile average
        mobAvgMinutes=summinutesmobile/amountmobile;//calculate minutes mobile average
        fixAvgInternet=suminternetfixed/amountfixed;//calcute internet fixed average
        fixAvgMinutes=summinutesfixed/amountfixed;//calcute minutes fiixed average
        System.out.println("------------------------------------------------------------");
        System.out.println("MOBILE:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Amount of contracts: "+amountmobile);
        System.out.println("Average minutes of subscribers: "+mobAvgMinutes+" Minimum amount of minutes on a contract: "+minminutesmobile+" Maximum amount of minutes on a contract: "+maxminutesmobile);
        System.out.println("Average internet of subscribers: "+mobAvgInternet+" Minimum amount of internet on a contract: "+mininternetmobile+" Maximum amount of internet on a contract: "+maxinternetmobile);
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("FIXED:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Amount of contracts: "+amountfixed);
        System.out.println("Average minutes of subscribers: "+fixAvgMinutes+" Minimum amount of minutes on a contract: "+minminutesfixed+" Maximum amount of minutes on a contract: "+maxminutesfixed);
        System.out.println("Average internet of subscribers: "+fixAvgInternet+" Minimum amount of internet on a contract: "+mininternetfixed+" Maximum amount of internet on a contract: "+maxinternetfixed);
        System.out.println("------------------------------------------------------------");
    }

    
    
    /* Auxiliary Methods
    */
     public static float FloatInput(){ //make sure input is float,to avoid crashes
         String input;
         Float fl;
         input=myScn.next();
        try {
         fl=Float.parseFloat(input);
    } catch (NumberFormatException nfe) { // catch exception to avoid crash 
        return 0;
    }
    return fl;
}
     public static int intInput(){ //makes sure input is int,and within java limits
         String Input;
         do{
         Input=myScn.next();
          if(!(Input.matches("[0-9]+") && Input.length()<9)){ //must be a number with no more than 9 digits
              System.out.println("Wrong input,please enter a number,or number too big!");
          }
         }while(!(Input.matches("[0-9]+")&& Input.length()<9));
       return Integer.parseInt(Input);
     }
     public static int taxIDInput(){ //makes sure correct tax id is given
         String Input;
         do{
         Input=myScn.next();
          if(!(Input.length()==9 && Input.matches("[0-9]+"))){ //must be a number with excactly
              System.out.println("Wrong tax id,try again!");
          }
         }while(!(Input.length()==9 && Input.matches("[0-9]+")));
       return Integer.parseInt(Input);
     }
    public static boolean stringToBool(){ //converts y/n to true/false boolean
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
    
   
    
    
 public static int  caluclateYear(int duration,int year,int month){ //calculates the year that contract will end
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
 
  public static int caluclateMonth(int duration,int year,int month){ //calculates the month that contract will end
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
  public static int findCustID(int taxID){ //finds customer id by tax id
    int i;
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
  public static boolean durationCheck(int duration){ //checks if durationj input is valid
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
  public static int getContractIndex(int custID,int type){ //used to check if there is a number with a contract,returns index of contract in contractList
      int i;
      int index=0;
      if (type==1){ //look for mobile contracts
      for (i=0; i<contractList.size(); i++){
            int tempCustID;
            tempCustID=contractList.get(i).getCustID();
            if (custID==tempCustID && contractList.get(i) instanceof mobile) {
                index=i;
            }
  }
      }
         if (type==2){ // look for fixed contracts
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
   public static boolean overlapCheck (int day,int month,int year,int custID,int type){ //checks for date overlap
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
   
   public static void printContract(int index,int custID){ //prints one contract 
       System.out.println(contractList.get(index).noOfContract);
       System.out.print("Type:");
       if(contractList.get(index) instanceof mobile){
           System.out.println("Mobile");
       }
       else if(contractList.get(index) instanceof fixed){
           System.out.println("Fixed");
       }
       System.out.println("Phone Number:"+contractList.get(index).phoneNo);
       System.out.println("Minutes:"+contractList.get(index).minutes);
       System.out.println("Start Date:"+contractList.get(index).startDay+"/"+contractList.get(index).startMonth+"/"+contractList.get(index).startYear);
       System.out.println("Expiration Date:"+contractList.get(index).endDay+"/"+contractList.get(index).endMonth+"/"+contractList.get(index).endYear);
       System.out.println("Active: "+contractList.get(index).isActive);
       if(contractList.get(index).hasInternet){
           System.out.print("Internet:");
           if (contractList.get(index) instanceof mobile){
               System.out.println(contractList.get(index).internet+" GB");
           }
           else  if (contractList.get(index) instanceof fixed){
               System.out.println(contractList.get(index).internet+" Mbps");
               System.out.println("Line Type:"+ contractList.get(index).findLineType());
           }
       }
       System.out.println("Cost of Contract:"+contractList.get(index).getCost()+"€");
       }
   public static int findcustIndex(int CustID){ //finds customer index in contractList by customer id
       int i;
       for (i=0;i<customerList.size();i++){
           if (customerList.get(i).getCustID()==CustID){
               return i;
           }
       }
       return 0;
   }
}