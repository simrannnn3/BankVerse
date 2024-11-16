import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class ExitPane extends VBox {
    public ExitPane(Stage stage, Bank bank) { // Add Bank parameter
        // Font settings for consistency
        Font buttonFont = Font.font("Arial", FontWeight.BOLD, 14);

        // Confirm exit button
        Button confirmExitButton = new Button("Confirm Exit");
        confirmExitButton.setFont(buttonFont);
        confirmExitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        confirmExitButton.setOnAction(e -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            exitAlert.setTitle("Exit Confirmation");
            exitAlert.setHeaderText("Are you sure you want to exit?");
            exitAlert.setContentText("Press OK to exit, or Cancel to stay.");

            exitAlert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    stage.close();
                }
            });
        });

        // Back button to return to main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(buttonFont);
        backButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");

        backButton.setOnAction(e -> stage.setScene(new Scene(new MainMenuPane(stage, bank), 600, 400))); // Pass the bank object

        // Add buttons to layout
        this.getChildren().addAll(confirmExitButton, backButton);
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f0f0f0;");
    }
}
