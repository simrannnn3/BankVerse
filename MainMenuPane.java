import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class MainMenuPane extends VBox {
    public MainMenuPane(Stage stage, Bank bank) {
        // Create buttons with styling
        Button createAccountButton = createStyledButton("Create Account");
        Button depositButton = createStyledButton("Deposit");
        Button withdrawButton = createStyledButton("Withdraw");
        Button balanceInquiryButton = createStyledButton("Balance Inquiry");
        Button transferButton = createStyledButton("Transfer");
        Button deleteAccountButton = createStyledButton("Delete Account");
        Button exitButton = createStyledButton("Exit");

        // Set up event handlers as before
        createAccountButton.setOnAction(e -> stage.setScene(new Scene(new AccountCreationPane(stage, bank), 600, 400)));
        depositButton.setOnAction(e -> stage.setScene(new Scene(new DepositPane(stage, bank), 600, 400)));
        withdrawButton.setOnAction(e -> stage.setScene(new Scene(new WithdrawPane(stage, bank), 600, 400)));
        balanceInquiryButton.setOnAction(e -> stage.setScene(new Scene(new BalanceInquiryPane(stage, bank), 600, 400)));
        transferButton.setOnAction(e -> stage.setScene(new Scene(new TransferPane(stage, bank), 600, 400)));
        deleteAccountButton.setOnAction(e -> stage.setScene(new Scene(new DeleteAccountPane(stage, bank), 600, 400)));
        exitButton.setOnAction(e -> stage.setScene(new Scene(new ExitPane(stage, bank), 600, 400))); // Pass the bank object



        // Set main layout styling
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setStyle("-fx-background-color: #f0f0f0;");

        // Add buttons to layout
        this.getChildren().addAll(createAccountButton, depositButton, withdrawButton, balanceInquiryButton, transferButton, deleteAccountButton, exitButton);
    }

    // Method to create a styled button
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 8;");

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #388E3C; -fx-background-radius: 8;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 8;"));
        return button;
    }
}
