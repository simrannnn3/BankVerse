import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class AccountCreationPane extends VBox {
    private static final double MINIMUM_BALANCE = 10000; // Define minimum balance

    public AccountCreationPane(Stage stage, Bank bank) {
        // Font settings
        Font textFont = Font.font("Arial", FontWeight.NORMAL, 14);
        Font buttonFont = Font.font("Arial", FontWeight.BOLD, 14);

        // Account number field
        TextField accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter Account Number");
        accountNumberField.setFont(textFont);

        // Account holder field
        TextField accountHolderField = new TextField();
        accountHolderField.setPromptText("Enter Account Holder Name");
        accountHolderField.setFont(textFont);

        // Initial balance field
        TextField initialBalanceField = new TextField();
        initialBalanceField.setPromptText("Enter Initial Balance");
        initialBalanceField.setFont(textFont);

        // Create account button
        Button createButton = new Button("Create Account");
        createButton.setFont(buttonFont);
        createButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Back button
        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(buttonFont);
        backButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");

        // Set action for the Create Account button
        createButton.setOnAction(e -> {
            try {
                String accountNumber = accountNumberField.getText();
                String accountHolder = accountHolderField.getText();
                String initialBalanceText = initialBalanceField.getText();

                // Validation checks
                if (accountNumber.trim().isEmpty()) {
                    showAlert("Error", "Account number cannot be empty.");
                    return;
                }

                if (accountHolder.trim().isEmpty()) {
                    showAlert("Error", "Account holder name cannot be empty.");
                    return;
                }

                if (initialBalanceText.trim().isEmpty()) {
                    showAlert("Error", "Initial balance cannot be empty.");
                    return;
                }

                double initialBalance = Double.parseDouble(initialBalanceText);
                
                if (initialBalance < 0) {
                    showAlert("Error", "Initial balance cannot be negative.");
                    return;
                }
                
                if (initialBalance < MINIMUM_BALANCE) {
                    showAlert("Error", "Initial balance must be at least " + MINIMUM_BALANCE + ".");
                    return;
                }

                // Create account
                bank.createAccount(new SavingsAccount(accountNumber, accountHolder, initialBalance));

                // Show success message
                showAlert("Success", "Account created successfully!");

                // Return to main menu after successful creation
                stage.setScene(new Scene(new MainMenuPane(stage, bank), 600, 400));
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid number for the initial balance.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // Set action for the Back button
        backButton.setOnAction(e -> {
            // Navigate back to the main menu without creating an account
            stage.setScene(new Scene(new MainMenuPane(stage, bank), 600, 400));
        });

        // Add components to the layout
        this.getChildren().addAll(accountNumberField, accountHolderField, initialBalanceField, createButton, backButton);
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 8;");
    }

    // Show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.showAndWait();
    }
}
