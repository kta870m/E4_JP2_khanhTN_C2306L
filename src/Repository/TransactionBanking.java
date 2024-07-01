package Repository;

import Entity.*;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionBanking {
    public static List<Transaction> transactions;
    public static List<Account> accounts;
    public static List<Customer> customers;
    public static String rootPath;

    public Account findById(String id){
        return accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Map<Account, Double> getAccounts(String keyword){
        Set<Integer> customerSet = customers.stream()
                .filter(c->c.getName().equals(keyword))
                .map(Customer::getId)
                .collect(Collectors.toSet());

        System.out.println(customerSet);
        Map<Account, Double> accountDoubleMap = new HashMap<>();
                accounts.stream()
                .filter(a->customerSet.contains(a.getCustomerId()))
                .forEach(a->accountDoubleMap.put(a, a.getBalance()));
        return accountDoubleMap;
    }

    public void updateAccount(Account account, double amount){
        accounts.stream()
                .filter(a -> a.getCustomerId() == account.getCustomerId())
                .forEach(a->a.setBalance(a.getBalance() - amount));
    }


    public void withdraw(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double amount;
        Transaction t;
        int tranId = transactions.size() + 1;
        Status status = null;
        try{
            System.out.print("Enter Account ID: ");
            String id = br.readLine();
            Account a = findById(id);
            if(a == null){
                System.out.println("Cannot find account with id: " + id);
            }else{
                System.out.print("Enter Amount: ");
                amount = Integer.parseInt(br.readLine());
                if(amount % 10000 != 0){
                    System.out.println("Amount must be divisible by 10");
                    status = Status.P;
                }else if(amount > a.getBalance()){
                    System.out.println("Amount must be greater than balance");
                    status = Status.R;
                }else{
                    status = Status.C;
                    System.out.println("Successfully withdrawn amount: " + amount);
                    updateAccount(a, amount);
                }
                t = new Transaction(tranId, a.getId(), amount, Type.WITHDRAWAL,LocalDateTime.now(),status);
                transactions.add(t);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<Account, List<Transaction>> findTransactions(String id,LocalDateTime start, LocalDateTime end){
            return transactions.stream()
                    .filter(p->p.getAccountId().equals(id))
                    .filter(t->(t.getDateTime().isAfter(start) || t.getDateTime().equals(start))
                            && (t.getDateTime().isBefore(end) || t.getDateTime().equals(end)))
                    .collect(Collectors.groupingBy(
                            p->findById(p.getAccountId()),
                            Collectors.toList()
                    ));
    }

    public void saveTransactions(Map<Account, List<Transaction>> transactions){
            try{
                File saveFile = new File("Account.id_transaction_history.txt");
                String fileOut = rootPath.replace("\\","/") + "/data/" + saveFile.getName();
                FileWriter fw = new FileWriter(fileOut);
                BufferedWriter bf = new BufferedWriter(fw);
                Iterator<Map.Entry<Account, List<Transaction>>> it = transactions.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry<Account, List<Transaction>> entry = it.next();
                    String objString = entry.getKey().getId() + ": " + entry.getValue() + "\n";
                    try{
                        bf.append(objString);
                        bf.newLine();
                        bf.flush();
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
    }

}
