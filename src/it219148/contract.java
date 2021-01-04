/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it219148;

import static it219148.it219148.contractList;

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
    
    /**
     *
     * @param hasInternet
     * @param internet
     * @param minutes
     * @param phoneNo
     * @param custID
     * @param endDay
     * @param endMonth
     * @param endYear
     */
    public contract (boolean hasInternet,float internet,int minutes,String phoneNo,int custID,int endDay,int endMonth,int endYear){
    this.hasInternet=hasInternet;
    this.internet=internet;
    this.minutes=minutes;
    this.phoneNo=phoneNo;
    this.custID=custID;
    this.endDay=endDay;
    this.endMonth=endMonth;
    this.endYear=endYear;
    }

    /**
     *
     * @return
     */
    public float  getInternet() {
        return internet;
    }

    /**
     *
     * @param internet
     */
    public void setInternet(int internet) {
        this.internet = internet;
    }

    /**
     *
     * @return
     */
    public boolean isHasInternet() {
        return hasInternet;
    }

    /**
     *
     * @param hasInternet
     */
    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    /**
     *
     * @return
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     *
     * @param minutes
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     *
     * @return
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     */
    public int getCustID() {
        return custID;
    }

    /**
     *
     * @param custID
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     *
     * @param phoneNo
     * @return
     */
    public static boolean numberExists(String phoneNo){
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

    /**
     *
     * @return
     */
    public int getEndDay() {
        return endDay;
    }

    /**
     *
     * @return
     */
    public  int getEndMonth() {
        return endMonth;
    }

    /**
     *
     * @return
     */
    public int getEndYear() {
        return endYear;
    }
   
}

    
   
class fixed extends contract{
        
    public fixed(boolean hasInternet, float internet, int minutes, String phoneNo, int custID,int endDay,int endMonth,int endYear) {
        super(hasInternet, internet, minutes, phoneNo, custID, endDay,endMonth,endYear);
    }
        public static  boolean validNumber(String phoneNo){
    if ((phoneNo.startsWith("2")) && (phoneNo.length()==10)){
        return true;
    }else{
        System.out.println("Number is wrong,try again!");
    }
    return false;
}
    }

class mobile extends contract{
   
    public mobile(boolean hasInternet, float internet, int minutes, String phoneNo, int custID,int endDay,int endMonth,int endYear) {
        super(hasInternet, internet, minutes, phoneNo, custID,endDay,endMonth,endYear);
    }
    public static  boolean validNumber(String phoneNo){
    if ((phoneNo.startsWith("69")) && (phoneNo.length()==10)){
        return true;
    }else{
        System.out.println("Number is wrong,try again!");
    }
    return false;
}

    }