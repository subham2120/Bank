package bankx;

import java.math.BigDecimal;
import java.util.*;

public class Account {
    public enum AccountType {
        SAVINGS,
        CURRENT,
        CHECKING
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
        this.accountNumber=accountNumber;
        this.holderName=holderName;
        this.phoneNumber=phoneNumber;
        if(balance !=null && balance.compareTo(BigDecimal.ZERO)>=0){
            this.balance=balance;
        }else{
            System.out.println("Enter the the valid amount");
        }

        this.accountType=accountType;
    }


    public BigDecimal deposit(BigDecimal amount){
        if(amount !=null && amount.compareTo(BigDecimal.ZERO)>0){
            this.balance=this.balance.add(amount);
        }
        return this.balance;
    }


    public BigDecimal withdraw(BigDecimal amount) throws InsufficientBalanceException{
        if(amount!=null && amount.compareTo(BigDecimal.ZERO)>0){
            if(this.balance.compareTo(amount)>=0){
                this.balance=this.balance.subtract(amount);
            }else {
                throw new InsufficientBalanceException("Insufficient Balance! Current balance: " + this.balance);
            }
        }else{
            throw  new IllegalArgumentException("Invalid number");
        }
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
            System.out.println("Error:Account cannot null");
            return;
        }
        if (accountMap.containsKey(account.getAccountNumber())){
            System.out.println("Error:Account number "+account.getAccountNumber()+"account exists");
        }else{
            accountMap.put(account.getAccountNumber(), account);
            System.out.println("success:"+account.getHolderName());
        }
    }

    public static void addPhoneNumber(Set<String> phoneNumbers,String phoneNumber) {
        if (phoneNumbers==null ||phoneNumber==null) {
            System.out.println("Error: Phone number cannot be null");
            return;
        }
        boolean added = phoneNumbers.add(phoneNumber);
        if (added) {
            System.out.println("Success: Phone number added");
        } else {
            System.out.println("Error: Phone number already exists");
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
