package oop1.ex;

public class Account {

    int balance;

    // 입금
    public void deposit(int amount) {
        balance += amount;
    }

    // 출금
    public void withdraw(int amount) {
        if(balance >= amount){
            balance -= amount;
        }
        else{
            System.out.println("잔액 부족");
        }
    }
}
