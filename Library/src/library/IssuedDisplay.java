/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author rndis
 */
public class IssuedDisplay {
    private String issuedID = null;
    private String memberName = null;
    private String bookName = null;
    private String period = null;
    private String issuedDate = null;
    private String returnDate = null;
    
    public IssuedDisplay(){}
    public IssuedDisplay(String issued_ID,String member_Name, String book_Name, String Period, String Issued_Date, String Return_Date){
        this.issuedID = issued_ID;
        this.memberName = member_Name;
        this.bookName = book_Name;
        this.period = Period;
        this.issuedDate = Issued_Date;
        this.returnDate = Return_Date;
    }
    
    // GETTERS
    public String getIssuedID(){
        return issuedID;
    }
    public String getMemberName(){
        return memberName;
    }
    public String getBookName(){
        return bookName;
    }
    public String getPeriod(){
        return period;
    }
     public String getIssuedDate(){
        return issuedDate;
    }
     public String getReturnDate(){
         return returnDate;
     }
    
    // SETTERS
     public String setIssuedID(){
         return issuedID;
     }
    public String setMemberName(){
        return memberName;
    }
    public String setBookName(){
        return bookName;
    }
    public String setPeriod(){
        return period;
    }
    public String setIssuedDate(){
        return issuedDate;
    }
    public String setReturnDate(){
        return returnDate;
    }
    
}
