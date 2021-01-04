/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it219148;

import static it219148.it219148.customerList;
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
  
   public customer(int custID, String firstName,String lastName,int taxID,boolean isStudent,boolean isPro,String IDnum,int paymentMethod,boolean eBill){
    this.custID=custID;
    this.firstName=firstName;
    this.lastName=lastName;
    this.taxID=taxID;
    this.isStudent=isStudent;
    this.isPro=isPro;
    this.IDnum=IDnum;
    this.paymentMethod=paymentMethod;
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
     
}
