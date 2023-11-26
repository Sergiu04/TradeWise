import service.*;
import dao.*;
import model.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        // Crearea serviciilor
        AuthService authService = new AuthService();
        NotificationService notificationService = new NotificationService();
        PredictionService predictionService = new PredictionService();
        DashboardService dashboardService = new DashboardService();
        
        

        // Crearea DAOs
        UserDAO userDAO = new UserDAO();
        PortofolioDAO portofolioDAO = new PortofolioDAO();
        // Crearea unui utilizator de test
        User user = new User(1, "test", "test@test.com", "password", 1000.0);

        // Crearea paginilor
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage(user, dashboardService);
        PredictionPage predictionPage = new PredictionPage(user, predictionService, authService);
        SettingsPage settingsPage = new SettingsPage();

      

        // Rularea aplicatiei
        loginPage.setVisible(true);
     //   dashboardPage.setVisible(true);
     //   predictionPage.setVisible(true);
      //  settingsPage.setVisible(true);
    }
}
