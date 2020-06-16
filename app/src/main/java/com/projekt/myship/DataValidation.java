package com.projekt.myship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataValidation {
    private String UserName;
    private String Password;
    private String RFname;
    private String RLname;
    private String RPassword;
    private String Number;
    private String ID;
    private String Sender;


    ///Setter Login UserName
    void setUserName(String UserName) {
        this.UserName = UserName;
    }


    ///Setter Login Password
    void setPassword(String Password) {
        this.Password = Password;
    }


    ///Setter Register FirstName
    void setRFname(String RFname) {
        this.RFname = RFname;
    }


    ///Setter Register LastName
    void setRLname(String RLname) {
        this.RLname = RLname;
    }


    ///Setter Register Password
    void setRPassword(String RPassword) {
        this.RPassword = RPassword;
    }


    ///Setter Number
    void setNumber(String Number) {
        this.Number = Number;
    }


    void setID(String ID) {
        this.ID = ID;
    }


    ///Setter Sender
    void setSender(String Sender) {
        this.Sender = Sender;
    }


    /// Using Regular Expression to check Data from Login Fragment
    /// Result True if both text inputs are true
    boolean LoginDataCheck() {
        String pattern = "^[a-zA-Z0-9]{3,15}";
        String pattern2 = "^[a-zA-Z]{3,15}";
        Pattern patcomp = Pattern.compile(pattern);
        Pattern patcomp2 = Pattern.compile(pattern2);
        Matcher matcher = patcomp2.matcher(UserName);
        Matcher matcher1 = patcomp.matcher(Password);
        return matcher.matches() && matcher1.matches();
    }


    /// Using Regular Expression to check Data from Register Fragment
    /// Result True if all text inputs are true
    boolean RegisterDataCheck() {
        String patternName = "^[a-zA-Z]{3,15}";
        String patternPass = "^[a-zA-Z0-9]{3,15}";
        Pattern patName = Pattern.compile(patternName);
        Pattern patPass = Pattern.compile(patternPass);
        Matcher matcherF = patName.matcher(RFname);
        Matcher matcherL = patName.matcher(RLname);
        Matcher matcherP = patPass.matcher(RPassword);
        return matcherF.matches() && matcherL.matches() && matcherP.matches();
    }



    /// Using Regular Expression to check Data from Sending Fragment
    /// Result True if all text inputs are true
    /// Need to be Fixed
    boolean SendingCheck() {
        String NumberPattern = "^[0-9]{10}";
        String SenderPattern = "^[a-zA-Z0-9]{3,15}";
        String IDPattern = "^[0-9]{1,2}";
        Pattern patNum = Pattern.compile(NumberPattern);
        Pattern patSend = Pattern.compile(SenderPattern);
        Pattern patID = Pattern.compile(IDPattern);
        Matcher matcherF = patNum.matcher(Number);
        Matcher matcherL = patID.matcher(ID);
        Matcher matcherP = patSend.matcher(Sender);
        return matcherF.matches() && matcherL.matches() && matcherP.matches();
    }

}
