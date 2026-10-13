package bankx;

import java.math.BigDecimal;
import java.util.*;

import bankx.Account.*;

import static bankx.Account.*;

public class Main {


    public static void main(String []args) {

        BankService bank=new BankService();


        Account Shubham=new Account(
                "1001",
                "shubham",
                "9801234567",
                new BigDecimal("5000.00"),
                AccountType.SAVINGS
        );

        Account Rahul=new Account(
                "1002",
                "Rahul",
                "9801234569",
                new BigDecimal("5000.00"),
                AccountType.SAVINGS
        );

        bank.registerAccount(Shubham);
        System.out.println(
                "Account phoneNumber exists: " + bank.registeredPhoneNumbers.contains("9801234569")
        );


        try{
            bank.registerAccount(Rahul);
        }catch(RuntimeException e){
            System.out.println("Registration failed: " + e.getMessage());
        }

        System.out.println(
                "Account phoneNumber exists: " + bank.registeredPhoneNumbers.contains("9801234569")
        );


//        System.out.println(
//                "Account 1001 exists: " + bank.accountMap.containsKey("1001")
//        );




//        Transaction tx=bank.deposit(
//                "1001",new BigDecimal("200.00")
//        );

//        System.out.println("shubham Balance :"+bank.checkBalance("1001"));
//

//        System.out.println(tx.getStatus());
//
//        try{
//            Transaction withdraw=bank.withdraw(
//                    "1001",new BigDecimal("200.00"));
//                    System.out.println(withdraw.getStatus());
//
//        }catch(InsufficientBalanceException e){
//          System.out.println(e);
//        }


//        System.out.println(bank.checkBalance("1001"));
//
//
//
//        try {
//            Transaction transfer = bank.transfer(
//                    "1001",
//                    "1002",
//                    new BigDecimal("20000"));
//        }catch(InsufficientBalanceException e){
//            System.out.println(e);
//        }


//        List<Transaction> historySubham =
//                bank.getAccountTransactionHistory("1001");
//
//        Transaction lastTransaction=historySubham.get(historySubham.size()-1);
//
//        System.out.println("Latest status: " + lastTransaction.getStatus());
//        System.out.println("Balance: " + bank.checkBalance("1001"));
//
//        List<Transaction> historyRahul =
//                bank.getAccountTransactionHistory("1002");
//
//        Transaction last=historyRahul.get(historyRahul.size()-1);
//
//        System.out.println("Latest status: " + last.getStatus());
//        System.out.println("Balance: " + bank.checkBalance("1002"));
    }
}
