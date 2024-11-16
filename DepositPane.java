import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class DepositPane extends VBox {
    public DepositPane(Stage stage, Bank bank) {
        TextField accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter Account Number");
        accountNumberField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4CAF50; -fx-padding: 5;");

        TextField depositAmountField = new TextField();
        depositAmountField.setPromptText("Enter Deposit Amount");
        depositAmountField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4CAF50; -fx-padding: 5;");

        Button depositButton = new Button("Deposit");
        depositButton.setFont(Font.font("Arial", 14));
        depositButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        depositButton.setOnAction(e -> {
            try {
                String accountNumber = accountNumberField.getText();
                double amount = Double.parseDouble(depositAmountField.getText());

                Account account = bank.getAccount(accountNumber);
                account.deposit(amount);

                showAlert("Success", "Amount deposited successfully!");

                // Return to main menu
                stage.setScene(new Scene(new MainMenuPane(stage, bank), 600, 400));
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(e -> stage.setScene(new Scene(new MainMenuPane(stage, bank), 600, 400)));

        this.getChildren().addAll(accountNumberField, depositAmountField, depositButton, backButton);
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f0f0f0;");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
