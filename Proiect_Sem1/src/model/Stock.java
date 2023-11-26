package model;

public class Stock{
    private Integer id;
    private String name;
    private Double purchasePrice, sellingPrice, automaticSalePercentage;
    private Double notificationDecreasePercentage;
    private Double predictionGainPercentage;

    /**
     * Constructor implicit, fara parametri
     * Setez toate valorile:
     * -> Integer/Double la 0
     * -> numele la "necunoscut"
     */
    public Stock(){
        id = 0;
        name = "necunoscut";
        purchasePrice = (double) 0;
        sellingPrice = (double) 0;
        automaticSalePercentage = (double) 0;
        notificationDecreasePercentage = (double) 0;
        predictionGainPercentage = (double) 0;
    }
    
    /**
     * Constructorul de copiere:
     * @param id reprezinta id-ul unic al fiecarui stock (cheie primara)
     * @param name reprezinta denumirea companiei asociata stock-ului
     * @param purchasePrice reprezinta pretul de cumparare al stock-ului (relatie cu user)
     * @param sellingPrice reprezinta pretul cu care s-a vandut stock-ul (relatie cu user)
     * @param automaticSalePercentage reprezinta procentul de profit la care sa se vanda automat actiunea (setat de user)
     * @param notificationDecreasePercentage reprezinta procentul de scadere al actiuni, la care se trimite o notificare spre utlizator (setat de user)
     * @param predictionGainPercentage reprezinta un model ML care prezice cu cat va creste/va scadea o actiune (un procent +-)  (setat de user)
     */
    public Stock(int id, String name, Double purchasePrice, Double sellingPrice,
                 Double automaticSalePercentage, Double notificationDecreasePercentage,
                 Double predictionGainPercentage){
                    this.id = id;
                    this.name = name;
                    this.purchasePrice = purchasePrice;
                    this.sellingPrice = sellingPrice;
                    this.automaticSalePercentage = automaticSalePercentage;
                    this.notificationDecreasePercentage = notificationDecreasePercentage;
                    this.predictionGainPercentage = predictionGainPercentage;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPurchasePrice(Double purchase){
        purchasePrice = purchase;
    }

    public void setSellingPrice(Double selling){
        sellingPrice = selling;
    }

    public void setAutomaticSalePercentage(Double percentage){
        automaticSalePercentage = percentage;
    }

    public void setNotificationDecreasePercentage(Double decrease){
        notificationDecreasePercentage = decrease;
    }
    public void setPredictionGainPercentage(Double gain){
        predictionGainPercentage = gain;
    }
    
    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Double getPurchasePrice(){
        return purchasePrice;
    }

    public Double getSellingPrice(){
        return sellingPrice;
    }

    public Double getAutomaticSalePercentage(){
        return automaticSalePercentage;
    }

    public Double getNotificationDecreasePercentage(){
        return notificationDecreasePercentage;
    }
    public Double getPredictionGainPercentage(){
        return predictionGainPercentage;
    }

    @Override
    public String toString(){
        return "Stock-ul: " + name + " are urmatoarele date : \n " + 
            "\t" + "purchase price = " + purchasePrice + "\n" +
            "\t" +"selling price = " + sellingPrice +  "\n" + 
            "\t" + "predictia noastra de evolutie a acestui stock este data de urmatorul procent" + 
            predictionGainPercentage + "\n\n";
    }
}