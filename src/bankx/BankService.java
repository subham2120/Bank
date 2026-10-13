package bankx;

import java.math.BigDecimal;
import java.util.*;


public class BankService {
     Map<String, Account>accountMap=new HashMap<>();
    Set<String> registeredPhoneNumbers= new HashSet<>();
    private final List<Transaction> transactionList=new ArrayList<>();


    public synchronized void registerAccount(Account account){
        if(account == null){
            throw new IllegalArgumentException("Account detail cannot be null");
        }
        String accNum= account.getAccountNumber();
        String phone=account.getPhoneNumber();
        if(phone==null || phone.trim().isEmpty()){
            throw new IllegalArgumentException("Phone cannot be null or blank");
        }
        String formattedPhone=phone.trim();
        if(this.registeredPhoneNumbers.contains(formattedPhone)){
            throw new IllegalStateException("Registration failed: Phone number \"" + formattedPhone + "\" already exists");
        }
        if(this.accountMap.containsKey(accNum)){
            throw new IllegalStateException("Registration failed: Account number \"" + accNum + "\" already exists");
        }
        Account.addPhoneNumber(registeredPhoneNumbers,formattedPhone);
        try{
            Account.addAccount(accountMap,account);
        }catch(IllegalStateException e){
            registeredPhoneNumbers.remove(formattedPhone);
            throw e;
        }
    }

    public Transaction deposit(String accountNumber,BigDecimal amount){
        Transaction tx= new Transaction(null,accountNumber,amount,Transaction.TransactionType.DEPOSIT);
        transactionList.add(tx);
        synchronized(this){
            Account account= Account.findAccountByNumber(accountMap,accountNumber);
            if(account==null){
                tx.markAsFailed();
                throw new IllegalArgumentException("Deposit Failed: Acoount doesn't exist");
            }
            if(amount == null || amount.compareTo(BigDecimal.ZERO)<=0){
                tx.markAsFailed();
                throw new IllegalArgumentException("Deposit Failed:Invalid amount");
            }
            account.deposit(amount);
            tx.markAsSuccess();
        }
        return tx;
    }

    public Transaction withdraw(String accountNumer,BigDecimal amount) throws InsufficientBalanceException{
        Transaction tx=new Transaction(accountNumer,null,amount,Transaction.TransactionType.WITHDRAWAL);
        transactionList.add(tx);
        synchronized (this){
            Account account=Account.findAccountByNumber(accountMap,accountNumer);
            if(account==null){
                tx.markAsFailed();
                throw new IllegalArgumentException("withdrawal Failed:Account doesn't Exist");
            }
            try{
                account.withdraw(amount);
                tx.markAsSuccess();
            }catch(InsufficientBalanceException | IllegalArgumentException e){
                tx.markAsFailed();
                throw e;
            }
        }
        return tx;
    }

    public Transaction transfer( String senderAccountNumber ,String receiverAccountNumber,BigDecimal amount)
        throws InsufficientBalanceException{
        if(senderAccountNumber ==null || receiverAccountNumber ==null){
            throw  new IllegalArgumentException("Transfer Failed:sender and Receiver cannot be null");
        }
        if(senderAccountNumber.equals(receiverAccountNumber)){
            throw new IllegalArgumentException("Transfer Failed:Sender and Receiver cannot be identical");
        }
        Transaction tx=new Transaction(
                senderAccountNumber,
                receiverAccountNumber,
                amount,
                Transaction.TransactionType.TRANSFER
        );
        transactionList.add(tx);
        synchronized (this){
            Account sender=Account.findAccountByNumber(accountMap,senderAccountNumber);
            Account receiver=Account.findAccountByNumber(accountMap,receiverAccountNumber);
            if(sender == null){
                tx.markAsFailed();
                throw new IllegalArgumentException("Sender doesn't exist");
            }
            if(receiver == null){
                tx.markAsFailed();
                throw new IllegalArgumentException("Receiver doesn't exist");
            }
            boolean isWithdrawn=false;
            try{
                sender.withdraw(amount);
                isWithdrawn=true;
                receiver.deposit(amount);
                tx.markAsSuccess();
            }catch(InsufficientBalanceException | IllegalArgumentException  e){
                tx.markAsFailed();
                if(isWithdrawn){
                    sender.deposit(amount);
                }
                throw e;
            }
        }
        return tx;
    }

    public BigDecimal checkBalance(String accountNumber){
        Account account=Account.findAccountByNumber(accountMap,accountNumber);

        if(account == null){
            throw new IllegalArgumentException("Account doesn't  exist");
        }
        return account.checkBalance();
    }


    public List<Transaction> getAccountTransactionHistory(String accountNumber){
        List<Transaction> history=new ArrayList<>();
        for (Transaction tx:transactionList){
            if(accountNumber.equals(tx.getSenderAccountNumber())||accountNumber.equals(tx.getReceiverAccountNumber())){
                history.add(tx);
            }
        }
        return history;
    }

    public List<Transaction> getTransactionList(){
        return new ArrayList<>(transactionList);
    }
}
