package bankx;

import java.math.BigDecimal;
import java.util.*;

public class Account {
    public enum AccountType {
        SAVINGS, CURRENT, CHECKING
    }
    private String accountNumber;
    private String holderName;
    private String phoneNumber;
    private BigDecimal balance;
    private AccountType accountType;
    public String getAccountNumber() {
        return this.accountNumber;
    }
    public String getHolderName(){
        return this.holderName;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public Account(String accountNumber,
                   String holderName,
                   String phoneNumber,
                   BigDecimal balance,
                   AccountType accountType ){
        if(accountNumber ==null || accountNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }
        this.accountNumber=accountNumber;
        this.holderName=holderName;
        if(phoneNumber==null || phoneNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Phone Number cannot be null or blank");
        }
        this.phoneNumber=phoneNumber;
        if(balance !=null && balance.compareTo(BigDecimal.ZERO)>=0){
            this.balance=balance;
        }else{
            throw new IllegalArgumentException("Enter Valid amount ");
        }
        this.accountType=accountType;
    }


    public BigDecimal deposit(BigDecimal amount){
        if(amount ==null || amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Enter the valid amount Greater than Zero");
        }
        this.balance=this.balance.add(amount);
        return this.balance;
    }


    public BigDecimal withdraw(BigDecimal amount) throws InsufficientBalanceException{
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
            throw  new IllegalArgumentException("Invalid number");
        }
        if(this.balance.compareTo(amount)<0){
            throw new InsufficientBalanceException("Insufficient Balance! Current balance: " + this.balance);
        }
        this.balance=this.balance.subtract(amount);
        return this.balance;
    }

    public BigDecimal checkBalance(){
        return this.balance;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ",holderName='" + holderName + '\'' +
                ",balance=" + balance +
                ",accountType=" + accountType +
                '}';
    }
    public  static void  addAccount(Map<String,Account>accountMap,Account account){
        if(accountMap ==null || account==null){
            throw new IllegalArgumentException("Error:Account cannot null");
        }
        String  accountNumber=account.getAccountNumber();
        if(accountNumber==null || accountNumber.trim().isEmpty()){
           throw new IllegalArgumentException("Account Number Cannot be null or blank");
        }
        if (accountMap.containsKey(accountNumber)){
            throw new IllegalStateException("Error:Account number "+accountNumber+"account exists");
        }
        accountMap.put(accountNumber, account);
        System.out.println("success:"+account.getHolderName()+"register");
    }


    public static void addPhoneNumber(Set<String> phoneNumbers,String phoneNumber) {
        if (phoneNumbers==null) {
            throw  new IllegalArgumentException("Phone number cannot be null ");
        }
        if(phoneNumber==null ||phoneNumber.trim().isEmpty()){
            throw new IllegalArgumentException("phone number cannot be blank");
        }
        String cleanPhoneNumber=phoneNumber.trim();
        boolean added = phoneNumbers.add(cleanPhoneNumber);
        if (!added) {
           throw new IllegalArgumentException("Error: Phone number \"" + cleanPhoneNumber + "\" already exists in the system");
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj==null||getClass()!=obj.getClass()) return false;
        Account account=(Account)obj;
        return Objects.equals(accountNumber,account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    public static Account findAccountByNumber(Map<String,Account> accountMap,String searchAccountNumber){
        return accountMap.get(searchAccountNumber);
    }

}
