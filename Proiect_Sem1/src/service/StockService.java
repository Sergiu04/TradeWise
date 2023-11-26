package service;

import model.Stock;
import model.User;
import dao.PortofolioDAO;
import dao.UserDAO;

import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private PortofolioDAO portofolioDao;
    private UserDAO userDao;
    private NotificationService notificationService;

    public StockService() {
        this.portofolioDao = new PortofolioDAO();
        this.userDao = new  UserDAO();
        this.notificationService = new NotificationService();
    }
    public void checkStockPrice(User user, Stock stock) {
        if (stock.getSellingPrice() <= stock.getPurchasePrice() * (1 - stock.getNotificationDecreasePercentage() / 100)) {
            notificationService.sendNotification(user, "The stock " + stock.getName() + " has reached the notification decrease percentage.");
        }
    }
    public void setAutomaticSalePercentage(User user, Stock stock, double percentage) {
        if (!portofolioDao.userOwnsStock(user, stock)) {
            throw new IllegalArgumentException("User does not own this stock");
        }

        stock.setAutomaticSalePercentage(percentage);

        portofolioDao.updateStock(stock);
    }

    public void buyStock(User user, Stock stock, int quantity) {
        double cost = stock.getSellingPrice() * quantity;
        if (user.getBalance() < cost) {
            throw new IllegalArgumentException("Insufficient funds to buy the stock");
        }
    
        // Adaugă acțiunea la portofoliul utilizatorului
        for (int i = 0; i < quantity; i++) {
            portofolioDao.addStockToPortofolio(user, stock);
        }
    
        // Scade costul acțiunii din soldul utilizatorului
        user.setBalance(user.getBalance() - cost);
    
        // Actualizează soldul utilizatorului în baza de date
        userDao.updateUser(user);
    }

    public void sellStock(User user, Stock stock, int quantity) {
        // Verifică dacă utilizatorul deține suficiente acțiuni pentru a le vinde
        int ownedQuantity = portofolioDao.getQuantityOfStock(user, stock);
        if (ownedQuantity < quantity) {
            throw new IllegalArgumentException("User does not own enough of this stock");
        }
    
        // Calculează veniturile din vânzare
        double revenue = stock.getSellingPrice() * quantity;
    
        // Elimină acțiunile din portofoliul utilizatorului
        for (int i = 0; i < quantity; i++) {
            portofolioDao.removeStockFromPortofolio(user, stock);
        }
    
        // Adaugă veniturile la soldul utilizatorului
        user.setBalance(user.getBalance() + revenue);
    
        // Actualizează soldul utilizatorului în baza de date
        userDao.updateUser(user);
    }
    
    public void setNotificationDecreasePercentage(User user, Stock stock, double percentage) {
        if (!portofolioDao.userOwnsStock(user, stock)) {
            throw new IllegalArgumentException("User does not own this stock");
        }
    
        stock.setNotificationDecreasePercentage(percentage);
    
        portofolioDao.updateStock(stock);
    }

    public void setPredictionGainPercentage(User user, Stock stock, double percentage) {
        if (!portofolioDao.userOwnsStock(user, stock)) {
            throw new IllegalArgumentException("User does not own this stock");
        }
    
        stock.setPredictionGainPercentage(percentage);
    
        portofolioDao.updateStock(stock);
    }

    public List<Stock> searchStocks(String searchTerm) {
        if (searchTerm == null) {
            throw new IllegalArgumentException("Search term cannot be null");
        }
    
        return portofolioDao.getAllStocks()
                .stream()
                .filter(stock -> stock.getName() != null && stock.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    
    
    
}
