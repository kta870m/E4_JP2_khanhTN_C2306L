package Repository;
import Entity.Status;
import Entity.Transaction;
import Entity.Type;
import Generic.IGeneric;
import Global.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TransactionRepo implements IGeneric<Transaction> {
    public static List<Transaction> transactions;
    public static String rootPath;
    private BufferedReader br;
    private FileReader fr;

    @Override
    public List<Transaction> fetchData(){
        String transactionFilePath = rootPath.replace("\\","/") + "/data/Transaction.txt";
        String[] data;
        String line;
        Transaction t;

        try{
            fr = new FileReader(transactionFilePath);
            br = new BufferedReader(fr);
            while((line = br.readLine())!=null){
                if(line.length()>0){
                    t = new Transaction();
                    data = line.split("; ");
                    t.setId(Integer.parseInt(data[0]));
                    t.setAccountId(data[1]);
                    t.setAmount(Double.parseDouble(data[2]));
                    t.setType(Type.valueOf(data[3]));
                    t.setDateTime(Date.parseDate(data[4]));
                    t.setStatus(Status.valueOf(data[5]));
                    transactions.add(t);
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return transactions;
    }


}
