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
                new BigDecimal("5000"),
                AccountType.SAVINGS
        );

        Account Rahul=new Account(
                "1002",
                "Rahul",
                "9876543219",
                new BigDecimal("5000"),
                AccountType.SAVINGS
        );

        bank.registerAccount(Shubham);
        bank.registerAccount(Rahul);


        Transaction tx=bank.deposit(
                "1001",new BigDecimal("5000")
        );



        System.out.println("shubham Balance"+bank.checkBalance("1001"));
        System.out.println("Rahul Balance"+bank.checkBalance("1002"));
        System.out.println(tx.getStatus());

//        try{
//            Transaction withdraw=bank.withdraw(
//                    "1001",new BigDecimal("2000"));
//                    System.out.println(withdraw.getStatus());
//
//        }catch(InsufficientBalanceException e){
//          System.out.println(e);
//        }


//        System.out.println(bank.checkBalance("1001"));



        try {
            Transaction transfer = bank.transfer(
                    "1001",
                    "1002",
                    new BigDecimal("20000"));
        }catch(InsufficientBalanceException e){
            System.out.println(e);
        }


        List<Transaction> historySubham =
                bank.getAccountTransactionHistory("1001");

        Transaction lastTransaction=historySubham.get(historySubham.size()-1);

        System.out.println("Latest status: " + lastTransaction.getStatus());
        System.out.println("Balance: " + bank.checkBalance("1001"));

        List<Transaction> historyRahul =
                bank.getAccountTransactionHistory("1002");

        Transaction last=historyRahul.get(historyRahul.size()-1);

        System.out.println("Latest status: " + last.getStatus());
        System.out.println("Balance: " + bank.checkBalance("1002"));
    }
}
