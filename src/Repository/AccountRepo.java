package Repository;
import Entity.Account;
import Entity.Currency;
import Generic.IGeneric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AccountRepo implements IGeneric<Account> {
    public static List<Account> accounts;
    public static String rootPath;
    private BufferedReader br;
    private FileReader fr;

    @Override
    public List<Account> fetchData(){
        String accountFilePath = rootPath.replace("\\","/") + "/data/Account.txt";
        String data[];
        String line;
        Account a;

        try{
            fr = new FileReader(accountFilePath);
            br = new BufferedReader(fr);
            while((line = br.readLine())!=null){
                if(line.length()>0){
                    a = new Account();
                    data = line.split("; ");
                    a.setId(data[0]);
                    a.setCustomerId(Integer.parseInt(data[1]));
                    a.setBalance(Double.parseDouble(data[2]));
                    a.setCurrency(Currency.valueOf(data[3]));
                    accounts.add(a);
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

}
