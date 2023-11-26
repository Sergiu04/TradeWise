package service;

import model.Stock;
import java.util.Random;

public class PredictionService {
    private static final double MAX_PERCENT_CHANGE = 0.1; // 10%
    private Random random;

    public PredictionService() {
        this.random = new Random();
    }

    public double predictStockPrice(Stock stock) {
        double percentChange = (random.nextDouble() * 2 - 1) * MAX_PERCENT_CHANGE;
        return stock.getSellingPrice() * (1 + percentChange);
    }
}
