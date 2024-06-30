import Entity.*;
import Global.Date;
import Repository.AccountRepo;
import Repository.CustomerRepo;
import Repository.TransactionBanking;
import Repository.TransactionRepo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<Customer>();
        List<Account> accounts = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        boolean flag = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int choice;

        String rootPath = System.getProperty("user.dir");

        CustomerRepo customerRepo = new CustomerRepo();
        customerRepo.customers = customers;
        customerRepo.rootPath = rootPath;

        AccountRepo accountRepo = new AccountRepo();
        accountRepo.accounts = accounts;
        accountRepo.rootPath = rootPath;

        TransactionRepo transactionRepo = new TransactionRepo();
        transactionRepo.transactions = transactions;
        transactionRepo.rootPath = rootPath;

        customerRepo.fetchData();
        accountRepo.fetchData();
        transactionRepo.fetchData();

        TransactionBanking transactionBanking = new TransactionBanking();
        transactionBanking.transactions = transactions;
        transactionBanking.customers = customers;
        transactionBanking.accounts = accounts;
        transactionBanking.rootPath = rootPath;

        try{
            do{
                System.out.println("1 - Withdraw");
                System.out.println("2 - Deposit");
                System.out.println("3 - Get Account");
                System.out.println("4 - Get Transaction");
                System.out.println("5 - Exit");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(br.readLine());
                switch (choice) {
                    case 1:
                        transactionBanking.withdraw();
                        transactions.forEach(System.out::println);
                        break;
                    case 2:
                        accounts.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter Your Name: ");
                        String name = br.readLine();
                        Iterator<Map.Entry<Account, Double>> iterator = transactionBanking.getAccounts(name)
                                .entrySet().iterator();
                        while(iterator.hasNext()){
                            Map.Entry<Account, Double> entry = iterator.next();
                            System.out.println("Account " + entry.getKey().getId() + ": " + entry.getValue());
                        }
                        break;
                    case 4:
                        System.out.print("Enter Start Date: ");
                        String startdate = br.readLine();
                        LocalDateTime startDate = Date.parseDate(startdate);
                        System.out.print("Enter End Date: ");
                        String enddate = br.readLine();
                        LocalDateTime endDate = Date.parseDate(enddate);
                        if(!transactionBanking.findTransactions(startDate, endDate).isEmpty()){
                            transactionBanking.findTransactions(startDate, endDate).forEach(System.out::println);
                            System.out.println("Do you want to save this tran");
                            String save = br.readLine();
                            switch (save) {
                                case "yes":
                                    transactionBanking.saveTransactions(transactionBanking.findTransactions(startDate, endDate));
                                    System.out.println("Successfully saved transactions");
                                    break;
                                    case "no": break;
                            }
                        } else {
                            System.out.println("No transactions found !");
                        }
                        break;
                }
            }while (!flag);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}