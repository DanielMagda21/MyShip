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

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setRFname(String RFname) {
        this.RFname = RFname;
    }


    public void setRLname(String RLname) {
        this.RLname = RLname;
    }


    public void setRPassword(String RPassword) {
        this.RPassword = RPassword;
    }


    public void setNumber(String Number) {
        this.Number = Number;
    }


    public void setID(String ID) {
        this.ID = ID;
    }


    public void setSender(String Sender) {
        this.Sender = Sender;
    }


    boolean LoginDataCheck() {
        String pattern = "^[a-zA-Z0-9]{3,15}";
        String pattern2 = "^[a-zA-Z]{3,15}";
        Pattern patcomp = Pattern.compile(pattern);
        Pattern patcomp2 = Pattern.compile(pattern2);
        Matcher matcher = patcomp2.matcher(UserName);
        Matcher matcher1 = patcomp.matcher(Password);
        return matcher.matches() && matcher1.matches();
    }

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
