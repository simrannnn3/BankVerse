import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class TransferPane extends VBox {
    public TransferPane(Stage stage, Bank bank) {
        TextField fromAccountField = new TextField();
        fromAccountField.setPromptText("Enter Sender's Account Number");
        fromAccountField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4CAF50; -fx-padding: 5;");

        TextField toAccountField = new TextField();
        toAccountField.setPromptText("Enter Receiver's Account Number");
        toAccountField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4CAF50; -fx-padding: 5;");

        TextField transferAmountField = new TextField();
        transferAmountField.setPromptText("Enter Transfer Amount");
        transferAmountField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #4CAF50; -fx-padding: 5;");

        Button transferButton = new Button("Transfer");
        transferButton.setFont(Font.font("Arial", 14));
        transferButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        transferButton.setOnAction(e -> {
            try {
                String fromAccount = fromAccountField.getText();
                String toAccount = toAccountField.getText();
                double amount = Double.parseDouble(transferAmountField.getText());

                bank.transfer(fromAccount, toAccount, amount);

                showAlert("Success", "Amount transferred successfully!");

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

        this.getChildren().addAll(fromAccountField, toAccountField, transferAmountField, transferButton, backButton);
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
