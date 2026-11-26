import java.util.Date;
public class Account {
    private int accNo;
    private String accName;
    private double balance;
    private Date dateCreated;
    private int pinNo;
    public Account()
    {
        
    }
    public Account(int accNo, String accName,double balance,Date dateCreated,int pinNo)
    {
        this.accNo = accNo;
        this.accName = accName;
        this.balance = balance;
        this.dateCreated = dateCreated;
        this.pinNo = pinNo;
    }
    public int getAccNo()
    {
        return accNo;
    }
    public String accName()
    {
        return accName;
    }
    public double getBalance()
    {
        return balance;
    }
    public Date getDateCreated()
    {
        return dateCreated;
    }
    public int getPinNo()
    {
        return pinNo;
    }
    public void setAccNo(int accNo)
    {
        
    }
            
    public void setAccName(String accName)
    {
        
    }
    public void setBalance(double balance)
    {
        
    }
    public void setPinNo(int pinNo)
    {
        
    }
    public void deposit(double amount)
    {
        
    }
    public void withdraw(double amount)
    {
        
    }
    public String toString()
    {
        return " " ;
    }
}
