package bankx;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    public enum Status{
        PENDING,SUCCESS,FAILED,REVERSED
    }
    public enum TransactionType{
        TRANSFER,DEPOSIT,WITHDRAWAL
    }
    private String transactionId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private Status status;
    private TransactionType transactionType;

    public Transaction(String senderAccountNumber,
                       String receiverAccountNumber,
                       BigDecimal amount,
                       TransactionType transactionType){

        if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Enter the valid amount greater than Zero");
        }
        if(transactionType==null){
            throw new IllegalArgumentException("Transaction type is required");
        }

        validationAccountRequirement(senderAccountNumber, receiverAccountNumber, transactionType);

        this.senderAccountNumber=senderAccountNumber;
        this.receiverAccountNumber=receiverAccountNumber;
        this.amount=amount;
        this.transactionType=transactionType;

        this.transactionId= UUID.randomUUID().toString();
        this.transactionTime=LocalDateTime.now();
        this.status=Status.PENDING;
    }

    public void validationAccountRequirement(String senderAccountNumber,String receiverAccountNumber,TransactionType transactionType){
        switch (transactionType){
            case DEPOSIT -> {
                if(isNullOrEmpty(receiverAccountNumber)){
                    throw new IllegalArgumentException("receiver account is required to deposit");
                }
            }

            case TRANSFER -> {
                if(isNullOrEmpty(senderAccountNumber)||isNullOrEmpty(receiverAccountNumber)){
                    throw new IllegalArgumentException("Both sender and receiver accounts are required for a TRANSFER");
                }
            }

            case WITHDRAWAL -> {
                if(isNullOrEmpty(senderAccountNumber)){
                    throw new IllegalArgumentException("sender account is required to withdrawal");
                }
            }
        }
    }

    private boolean isNullOrEmpty(String str){
        return str == null || str.trim().isEmpty();
    }

    public void markAsSuccess(){
        if(this.status!=Status.PENDING){
            throw new IllegalArgumentException("Only PENDING transactions can be marked as SUCCESS. Current status:/" + this.status);
        }
        this.status=Status.SUCCESS;
    }

    public void markAsFailed(){
        if(this.status!=Status.PENDING){
            throw new IllegalArgumentException("Only PENDING transactions can be marked as FAILED. Current status:/" + this.status);
        }
        this.status=Status.FAILED;
    }

    public String getTransactionId() {
        return transactionId;
    }
    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    public Status getStatus() {
        return status;
    }
    public TransactionType getTransactionType() {
        return transactionType;
    }
}
