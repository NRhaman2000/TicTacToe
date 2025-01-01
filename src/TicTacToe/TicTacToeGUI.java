package TicTacToe;

/*
 * Remember to update the Java version:
 * 1. On terminal PATH//jdk-23.0.1.jdk/Contents/Home
 * 2. nano ~/.zshrc
 * 3. Add this "export PATH="/Users/naimrahman/Documents/GitHub/jdk-23.0.1.jdk/Contents/Home/bin:$PATH""
 * 4. save this
 * 5. Java version is updated.
 * After version is upadated, do this:
 * First compile this on terminal (remember the path should be slected all the way to TicTacToeGUI.java)
 * javac --module-path /Users/naimrahman/Documents/GitHub/JavaFX/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml -d . TicTacToeGUI.java Board.java Player.java
 * Then run this: java --module-path /Users/naimrahman/Documents/GitHub/JavaFX/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml TicTacToe.TicTacToeGUI
 * Remember to use latest version of Java and JavaFX
 * 
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToeGUI extends Application {

	private Board board = new Board(); // Reuse your Board class
	private Player playerX = new Player("Player X", 'X'); // Player X
	private Player playerO = new Player("Player O", 'O'); // Player O
	private Player currentPlayer = playerX; // Start with Player X
    private Button[][] buttons = new Button[3][3];

    public static void main(String[] args) {
        launch(args); // Launch JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane(); // Grid layout for the game board

        // Initialize buttons and add to grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(100, 100); // Set button size
                button.setStyle("-fx-font-size: 24;");
                int finalRow = row;
                int finalCol = col;

                // Button click event
                button.setOnAction(event -> {
                    if (!board.isFull() && button.getText().isEmpty()) {
                        button.setText(String.valueOf(currentPlayer.getSymbol()));
                        if (board.makeMove(finalRow, finalCol, currentPlayer.getSymbol())) {
                            if (board.checkWinner(currentPlayer.getSymbol())) {
                                showAlert(currentPlayer.getName() + " wins!");
                                resetGame();
                            } else if (board.isFull()) {
                                showAlert("It's a draw!");
                                resetGame();
                            } else {
                                currentPlayer = (currentPlayer == playerX) ? playerO : playerX; // Switch player
                            }
                        }
                    }
                });

                buttons[row][col] = button; // Add button to array
                grid.add(button, col, row); // Add button to grid
            }
        }

        // Set up scene and stage
        Scene scene = new Scene(grid, 320, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetGame() {
        board = new Board(); // Reset the board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(""); // Clear buttons
            }
        }
        currentPlayer = playerX; // Reset to Player X
    }
}
