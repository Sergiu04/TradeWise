package model;

public class Transaction {
    private Integer id, stockId, userId;
    private String transactionDate, transactionType;

    public Transaction(){
        id = 0;
        stockId = 0;
        userId = 0;
        transactionDate = "necunoscut";
        transactionType = "necunoscut";
    }

    public Transaction(Integer id, String transactionDate, String transactionType){
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Transaction(Integer id, Integer stockId, Integer userId,
                        String transactionDate, String transactionType){
        this.id = id;
        this.stockId = stockId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Integer getTransactionId(){
        return id;
    }
    
    public Integer getStockId(){
        return stockId;
    }

    public Integer getUserId(){
        return userId;
    }
    @Override
    public String toString(){
        return "Tranzactia cu id: " + id  
            + ", asociata stock-ului de id: " + stockId 
            + "si a user id ului: " + userId 
            + '\n' + "a fost realizata in data de: " + transactionDate 
            + "si este de tipul: " + transactionType + '\n';
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }


}
