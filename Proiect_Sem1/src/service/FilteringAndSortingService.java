package service;

import model.Stock;
import java.util.List;
import java.util.stream.Collectors;

public class FilteringAndSortingService {

    public List<Stock> filterStocksByGainPercentage(List<Stock> stocks, double minGainPercentage) {
        return stocks.stream()
                .filter(stock -> stock.getAutomaticSalePercentage() >= minGainPercentage)
                .collect(Collectors.toList());
    }

    public List<Stock> sortStocksByPurchasePrice(List<Stock> stocks) {
        return stocks.stream()
                .sorted((stock1, stock2) -> stock1.getPurchasePrice().compareTo(stock2.getPurchasePrice()))
                .collect(Collectors.toList());
    }
}
