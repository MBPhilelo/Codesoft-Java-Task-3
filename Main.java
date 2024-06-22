/**
 * 
 */

/**
 * @author HP
 *
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private ATM atm;
    private BankAccount currentAccount;
    private Label balanceLabel;
    private TextField amountField;

    @Override
    public void start(Stage primaryStage) {
        atm = new ATM();
        // Adding some predefined accounts for demonstration
        atm.addAccount(new BankAccount("1001", "1111", 500));
        atm.addAccount(new BankAccount("1002", "2222", 600));
        atm.addAccount(new BankAccount("1003", "3333", 700));

        // Login Screen
        Stage loginStage = new Stage();
        loginStage.setTitle("ATM Management System");
        GridPane loginGrid = createLoginScreen(loginStage);
        Scene loginScene = new Scene(loginGrid, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private GridPane createLoginScreen(Stage loginStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);
        loginGrid.setAlignment(Pos.CENTER);

        Label accNumLabel = new Label("ACC NUM:");
        TextField accNumField = new TextField();
        Label pinCodeLabel = new Label("PIN CODE:");
        PasswordField pinCodeField = new PasswordField();
        Button loginButton = new Button("LOGIN");

        loginButton.setOnAction(e -> {
            String accNum = accNumField.getText();
            String pinCode = pinCodeField.getText();
            if (atm.verifyAccount(accNum, pinCode)) {
                currentAccount = atm.getAccount(accNum);
                loginStage.close();
                showATMMenu();
            } else {
                showMessage("Invalid account number or PIN.");
            }
        });

        loginGrid.add(accNumLabel, 0, 0);
        loginGrid.add(accNumField, 1, 0);
        loginGrid.add(pinCodeLabel, 0, 1);
        loginGrid.add(pinCodeField, 1, 1);
        loginGrid.add(loginButton, 1, 2);

        return loginGrid;
    }

    private void showATMMenu() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("MYCODESPACE BANK");

        balanceLabel = new Label("Balance: " + currentAccount.getBalance());
        amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button withdrawButton = new Button("WITHDRAW");
        Button depositButton = new Button("DEPOSIT");
        Button balanceButton = new Button("BALANCE");

        withdrawButton.setOnAction(e -> handleWithdraw());
        depositButton.setOnAction(e -> handleDeposit());
        balanceButton.setOnAction(e -> handleCheckBalance());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(balanceLabel, 0, 0, 2, 1);
        gridPane.add(amountField, 0, 1, 2, 1);
        gridPane.add(withdrawButton, 0, 2);
        gridPane.add(depositButton, 1, 2);
        gridPane.add(balanceButton, 0, 3);

        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleWithdraw() {
        double amount = Double.parseDouble(amountField.getText());
        if (currentAccount.withdraw(amount)) {
            showMessage("Withdrawal successful. Please take your cash.");
        } else {
            showMessage("Insufficient balance or invalid amount.");
        }
        updateBalance();
    }

    private void handleDeposit() {
        double amount = Double.parseDouble(amountField.getText());
        currentAccount.deposit(amount);
        showMessage("Deposit successful. Your new balance is: " + currentAccount.getBalance());
        updateBalance();
    }

    private void handleCheckBalance() {
        updateBalance();
        showMessage("Your current balance is: " + currentAccount.getBalance());
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: " + currentAccount.getBalance());
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
