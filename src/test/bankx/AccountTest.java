package bankx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

class AccountTest{

    @Test
    void depositShouldIncreaseBalance(){
        Account account =new Account(
                "1001",
                "shubham",
                "9801234567",
                new BigDecimal("1000.00"),
                Account.AccountType.SAVINGS
        );

        account.deposit(new BigDecimal("500.00"));

       BigDecimal totalBalance= account.checkBalance();

       Assertions.assertEquals(new BigDecimal("1500.00"),totalBalance);

    }

    @Test
    void depositZeroShouldThrowException(){
        Account account =new Account(
                "1001",
                "shubham",
                "9801234567",
                new BigDecimal("1000.00"),
                Account.AccountType.SAVINGS
        );

        IllegalArgumentException exception=Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> account.deposit(BigDecimal.ZERO)
        );

        Assertions.assertEquals(new BigDecimal("1000.00"),account.checkBalance());

        Assertions.assertEquals("Enter the valid amount Greater than Zero",exception.getMessage());
    }


    @Test
    void withdrawShouldDecreaseBalance() throws InsufficientBalanceException{
        Account account=new Account(
                "1001",
                "subham",
                "98011234567",
                new BigDecimal("1000.00"),
                Account.AccountType.SAVINGS
        );

        BigDecimal totalBalance=account.withdraw(new BigDecimal("300.00"));

        Assertions.assertEquals(new BigDecimal("700.00"),totalBalance);

    }

}