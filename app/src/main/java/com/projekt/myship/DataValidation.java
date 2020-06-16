package com.projekt.myship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

///\brief DataValidation Class
///\details Using regex to check user input data
public class DataValidation {
    private String UserName;    ///Login UserName
    private String Password;    ///Login Password
    private String RFname;      ///Register First Name
    private String RLname;      ///Register Last Name
    private String RPassword;   ///Register Password
    private String Number;      ///Sending Package Number/Name
    private String ID;          ///Sending Package ID
    private String Sender;      ///Sending Package Sender


    ///Setter Login UserName
     public void setUserName(String UserName) {
        this.UserName = UserName;
    }



    public void setPassword(String Password) {
        this.Password = Password;
    } ///Setter Login Password



    public void setRFname(String RFname) {
        this.RFname = RFname;
    } ///Setter Register FirstName



    public void setRLname(String RLname) {
        this.RLname = RLname;
    } ///Setter Register LastName



    public void setRPassword(String RPassword) { this.RPassword = RPassword; }   ///Setter Register Password



    public void setNumber(String Number) {
        this.Number = Number;
    } ///Setter Number


    public void setID(String ID) {
        this.ID = ID;
    } ///Setter ID



    public void setSender(String Sender) {
        this.Sender = Sender;
    }  ///Setter Sender


    ///\brief Using Regular Expression to check Data from Login Fragment
    ///\return Result True if both text inputs are true
    boolean LoginDataCheck() {
        String pattern = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression pattern
        String pattern2 = "^[a-zA-Z]{3,15}"; ///Regular Expression  pattern
        Pattern patcomp = Pattern.compile(pattern); ///Compiling Patter with RegEX  pattern
        Pattern patcomp2 = Pattern.compile(pattern2); ///Compiling Patter with RegEX  pattern
        Matcher matcher = patcomp2.matcher(UserName); /// Matcher object for user Input
        Matcher matcher1 = patcomp.matcher(Password); /// Matcher object for user Input
        return matcher.matches() && matcher1.matches();
    }


    ///\brief Using Regular Expression to check Data from Register Fragment
    ///\return Result True if all text inputs are true
    boolean RegisterDataCheck() {
        String patternName = "^[a-zA-Z]{3,15}"; ///Regular Expression  pattern
        String patternPass = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression  pattern
        Pattern patName = Pattern.compile(patternName); ///Compiling Patter with RegEX pattern
        Pattern patPass = Pattern.compile(patternPass); ///Compiling Patter with RegEX pattern
        Matcher matcherF = patName.matcher(RFname); /// Matcher object for user Input
        Matcher matcherL = patName.matcher(RLname); /// Matcher object for user Input
        Matcher matcherP = patPass.matcher(RPassword); /// Matcher object for user Input
        return matcherF.matches() && matcherL.matches() && matcherP.matches();
    }


    ///\brief Using Regular Expression to check Data from Sending Fragment(Need to be Fixed)
    ///\return Result True if all text inputs are true
    boolean SendingCheck() {
        String NumberPattern = "^[0-9]{10}"; ///Regular Expression pattern
        String SenderPattern = "^[a-zA-Z0-9]{3,15}"; ///Regular Expression pattern
        String IDPattern = "^[0-9]{1,2}"; ///Regular Expression pattern
        Pattern patNum = Pattern.compile(NumberPattern); ///Compiling Patter with RegEX pattern
        Pattern patSend = Pattern.compile(SenderPattern); ///Compiling Patter with RegEX pattern
        Pattern patID = Pattern.compile(IDPattern); //Compiling Patter with RegEX pattern
        Matcher matcherF = patNum.matcher(Number); /// Matcher object for user Input
        Matcher matcherL = patID.matcher(ID); /// Matcher object for user Input
        Matcher matcherP = patSend.matcher(Sender); /// Matcher object for user Input
        return matcherF.matches() && matcherL.matches() && matcherP.matches();
    }

}
