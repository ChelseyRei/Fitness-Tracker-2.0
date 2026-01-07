package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class RegistrationFormController {

    @FXML private Button backButton;
    @FXML private TextField nameTextField;
    @FXML private DatePicker birthdayDatePicker;
    @FXML private ComboBox<String> sexComboBox;
    @FXML private TextField weightTextField;
    @FXML private TextField heightTextField;
    @FXML private Button nextButton;

    // Connect to Backend Service
    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        setupSexComboBox();
        setupEventHandlers();
    }

    private void setupSexComboBox() {
        sexComboBox.getItems().addAll("Male", "Female");
    }

    private void setupEventHandlers() {
        // Use lambdas for safe navigation
        backButton.setOnAction(e -> navigate("RegistrationStart"));
        nextButton.setOnAction(e -> handleNext());
    }

    private void handleNext() {
        if (validateForm()) {
            boolean success = saveUserData();
            if (success) {
                navigate("RegistrationSummary");
            } else {
                System.err.println("Failed to save user data.");
            }
        }
    }

    private boolean validateForm() {
        String name = nameTextField.getText();
        LocalDate birthday = birthdayDatePicker.getValue();
        String sex = sexComboBox.getValue();
        String weight = weightTextField.getText();
        String height = heightTextField.getText();

        if (name == null || name.trim().isEmpty()) return false;
        if (birthday == null) return false;
        if (sex == null) return false;
        if (weight == null || weight.trim().isEmpty()) return false;
        if (height == null || height.trim().isEmpty()) return false;

        return true;
    }

    private boolean saveUserData() {
        try {
            String name = nameTextField.getText();
            LocalDate birthday = birthdayDatePicker.getValue();
            String sex = sexComboBox.getValue();
            double weight = Double.parseDouble(weightTextField.getText());
            double height = Double.parseDouble(heightTextField.getText());

            // Calculate Age from Birthday
            int age = Period.between(birthday, LocalDate.now()).getYears();

            // Save to Database via Service
            // Note: We pass "Moderately Active" as default since this form doesn't ask for it
            return userService.registerOrUpdateUser(name, age, height, weight, sex, "Moderately Active");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for weight or height.");
            return false;
        }
    }

    // Helper method to use your Main.setRoot navigation
    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxml);
        }
    }
}