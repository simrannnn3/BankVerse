import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingApplicationFX extends Application {
    private final Bank bank = new Bank();  // Using the bank instance from your backend code
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Banking Application");
        Scene mainMenuScene = new Scene(new MainMenuPane(primaryStage, bank), 600, 400);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
