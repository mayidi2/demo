import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// ==================== 交易记录类（去掉public）====================
class Transaction {
    private Date time;
    private String type;
    private double amount;
    
    public Transaction(String type, double amount) {
        this.time = new Date();
        this.type = type;
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return time + " | " + type + " | " + amount + "元";
    }
}

// ==================== 客户类（去掉public）====================
class Customer {
    private String name;
    private String customerId;
    private String phone;
    
    public Customer(String name, String customerId, String phone) {
        this.name = name;
        this.customerId = customerId;
        this.phone = phone;
    }
    
    public String getName() { return name; }
    public String getCustomerId() { return customerId; }
}

// ==================== 银行账户类（去掉public）====================
class BankAccount {
    private int accountNumber;
    private String password;
    private double balance;
    private Customer holder;
    private List<Transaction> transactions = new ArrayList<>();
    
    public BankAccount(int accountNumber, String password, Customer holder) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.holder = holder;
    }
    
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }
    
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("存款", amount));
        System.out.println("存款成功");
    }
    
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("取款", amount));
            System.out.println("取款成功");
            return true;
        }
        System.out.println("余额不足");
        return false;
    }
    
    public boolean transfer(BankAccount target, double amount) {
        if (withdraw(amount)) {
            target.deposit(amount);
            transactions.add(new Transaction("转出", amount));
            return true;
        }
        return false;
    }
    
    public void printRecentTransactions() {
        int count = Math.min(5, transactions.size());
        System.out.println("\n=== 最近 " + count + " 笔交易 ===");
        for (int i = transactions.size() - 1; i >= transactions.size() - count; i--) {
            System.out.println(transactions.get(i));
        }
    }
    
    public double getBalance() { return balance; }
    public int getAccountNumber() { return accountNumber; }
}

// ==================== ATM类（去掉public）====================
class ATM {
    private BankAccount[] accounts;
    private BankAccount currentAccount;
    private Scanner scanner = new Scanner(System.in);
    
    public ATM(BankAccount[] accounts) {
        this.accounts = accounts;
    }
    
    public void start() {
        System.out.println("欢迎使用银行ATM系统");
        if (!login()) {
            System.out.println("登录失败，退出系统");
            return;
        }
        
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: queryBalance(); break;
                case 2: withdraw(); break;
                case 3: deposit(); break;
                case 4: transfer(); break;
                case 5: printTransactions(); break;
                case 6: running = false; break;
                default: System.out.println("无效选项");
            }
        }
        System.out.println("感谢使用，再见！");
    }
    
    private boolean login() {
        System.out.print("请输入卡号：");
        int cardNum = scanner.nextInt();
        System.out.print("请输入密码：");
        String pwd = scanner.next();
        
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == cardNum && acc.verifyPassword(pwd)) {
                currentAccount = acc;
                System.out.println("登录成功！");
                return true;
            }
        }
        return false;
    }
    
    private void showMenu() {
        System.out.println("\n1.查询余额 2.取款 3.存款 4.转账 5.交易记录 6.退出");
        System.out.print("请选择：");
    }
    
    private void queryBalance() {
        System.out.println("当前余额：" + currentAccount.getBalance());
    }
    
    private void withdraw() {
        System.out.print("请输入取款金额：");
        double amount = scanner.nextDouble();
        currentAccount.withdraw(amount);
    }
    
    private void deposit() {
        System.out.print("请输入存款金额：");
        double amount = scanner.nextDouble();
        currentAccount.deposit(amount);
    }
    
    private void transfer() {
        System.out.print("请输入目标卡号：");
        int targetNum = scanner.nextInt();
        System.out.print("请输入转账金额：");
        double amount = scanner.nextDouble();
        
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == targetNum) {
                currentAccount.transfer(acc, amount);
                return;
            }
        }
        System.out.println("目标账户不存在");
    }
    
    private void printTransactions() {
        currentAccount.printRecentTransactions();
    }
}

// ==================== 唯一public类：入口 ====================
public class BankSystemV4 {
    public static void main(String[] args) {
        Customer c1 = new Customer("张三", "C001", "13800138000");
        Customer c2 = new Customer("李四", "C002", "13900139000");
        
        BankAccount a1 = new BankAccount(1001, "123456", c1);
        BankAccount a2 = new BankAccount(1002, "654321", c2);
        
        a1.deposit(10000);
        a2.deposit(5000);
        
        ATM atm = new ATM(new BankAccount[]{a1, a2});
        atm.start();
    }
}