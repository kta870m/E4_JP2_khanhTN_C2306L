package Repository;

import Entity.Customer;
import Generic.IGeneric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CustomerRepo implements IGeneric<Customer>{
    public static List<Customer> customers;
    public static String rootPath;
    private BufferedReader br;
    private FileReader fr;

    @Override
    public List<Customer> fetchData() {
        String customerFilePath = rootPath.replace("\\", "/") + "/data/Customer.txt";
        Customer c = new Customer();
        String data[];
        String line;
        try{
            fr = new FileReader(customerFilePath);
            br = new BufferedReader(fr);
             while((line = br.readLine())!=null){
                if(line.length()>0){
                    c = new Customer();
                    data = line.split("; ");
                    c.setId(Integer.parseInt(data[0]));
                    c.setName(data[1]);
                    c.setPhone(data[2]);
                    customers.add(c);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return customers;
    }
}
