package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import service.UserService;
import java.io.IOException;

public class RegistrationFormController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField heightField;
    @FXML private TextField weightField;
    @FXML private ComboBox<String> sexComboBox;
    @FXML private ComboBox<String> activityLevelComboBox;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        sexComboBox.getItems().addAll("Male", "Female");
        activityLevelComboBox.getItems().addAll("Sedentary", "Lightly Active", "Moderately Active", "Very Active");
    }

    @FXML
    private void handleNext() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            String sex = sexComboBox.getValue();
            String activity = activityLevelComboBox.getValue();

            if (name.isEmpty() || sex == null || activity == null) {
                System.out.println("Please fill all fields.");
                return;
            }

            // Register the user
            boolean success = userService.registerOrUpdateUser(name, age, height, weight, sex, activity);

            if (success) {
                Main.setRoot("RegistrationSummary");
            } else {
                System.out.println("Registration failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}