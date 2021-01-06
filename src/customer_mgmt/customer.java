/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_mgmt;

import static customer_mgmt.main.contractList;
import static customer_mgmt.main.customerList;
import java.util.ArrayList;
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
   private boolean eBill;
   private int discount;
  
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
    int noOfContracts=getNoOfContracts(this.custID);
    int over1000mins=checkfor1000mins(this.custID,contractList.size());
    discount=calculateDIscount(noOfContracts>0,noOfContracts,this.custID,this.isStudent,this.isPro,over1000mins,this.paymentMethod,this.eBill);
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

    public void refreshDiscount() {
        int noOfContracts=getNoOfContracts(this.custID);
        int over1000mins=checkfor1000mins(this.custID,contractList.size());
        this.discount =calculateDIscount(noOfContracts>0,noOfContracts,this.custID,this.isStudent,this.isPro,over1000mins,this.paymentMethod,this.eBill); ;
    }

    
    
    public static boolean existingCustomer(int taxID,String IDnum){
        boolean taxIDExists=false;
        boolean IDNumExists=false;
        int i;
        for (i=0; i<customerList.size(); i++){
            int tempTax;
            tempTax=customerList.get(i).getTaxID();
            if (tempTax==taxID){
                taxIDExists=true;
            }
        }
        for (i=0; i<customerList.size(); i++){
            String tempIdNum;
            tempIdNum=customerList.get(i).getIDnum();
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
        num=rand.nextInt(100000);
        return num;
    }
    public int calculateDIscount(boolean IsValid,int noOfContracts,int custID,boolean isStudent,boolean isPro,int over1000MinutesType,int paymentMethod,boolean eBill){
    int contractDiscount=0;
    int propertyDIscount=0;
    int MethodDiscount=0;
    int ebillDiscount=0;
    int totalDiscount=0;
    int minuteDiscount=0;
    if(noOfContracts>3){
        contractDiscount=15;
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
    if(over1000MinutesType==1){
        minuteDiscount=11;
    }
    if(over1000MinutesType==2){
        minuteDiscount=8;
    }
    totalDiscount=contractDiscount+propertyDIscount+MethodDiscount+ebillDiscount+minuteDiscount;
    if (totalDiscount>45){
        totalDiscount=45;
    }
    if (!IsValid){
        totalDiscount=0;
    }
    return totalDiscount;
}
     private int getNoOfContracts(int custID){
         ArrayList <Integer> contractIndexes= new ArrayList<Integer>();
        int i=0;
        for(i=0;i<contractList.size();i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
    }
     return contractIndexes.size();
   
     }
     private int checkfor1000mins (int custID,int size){
         boolean exists;
         int i=0;
         int type=0;
          ArrayList <Integer> contractIndexes= new ArrayList<Integer>();
         for(i=0;i<size;i++){
            if (custID==contractList.get(i).custID){
            contractIndexes.add(i);
        }
     }
         for(i=0;i<contractIndexes.size();i++){
             if(contractList.get(contractIndexes.get(i)).minutes>1000){
                 exists=true;
                 if(contractList.get(contractIndexes.get(i)) instanceof mobile){
                     type=1;
                 }
                 else if(contractList.get(contractIndexes.get(i)) instanceof mobile){
                     type=2;
                 }
             }
         }
         return type;
}

}