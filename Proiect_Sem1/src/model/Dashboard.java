package model;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private List<Stock> portofolio;
    private List<Transaction> transactionHistory;

    public Dashboard(){
        portofolio = new ArrayList<>();
        transactionHistory = new ArrayList<>();
    }

    public Dashboard(List<Stock> s, List<Transaction> t)
    {
        portofolio = s;
        transactionHistory = t;
    }

    public void viewPortofolio(){
        for(Stock s : portofolio)
            System.out.println(s.toString());
    }

    public void viewTransactionHistory(){
        for(Transaction t : transactionHistory)
            System.out.println(t.toString());
    }
}
