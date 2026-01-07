package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.UserService;
import model.User;
import java.io.IOException;

public class EditProfileController {

    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private TextField ageField;

    private final UserService userService = new UserService();
    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = userService.getUserProfile();
        if (currentUser != null) {
            weightField.setText(String.valueOf(currentUser.getWeightKg()));
            heightField.setText(String.valueOf(currentUser.getHeightCm()));
            ageField.setText(String.valueOf(currentUser.getAge()));
        }
    }

    @FXML
    private void handleSave() throws IOException {
        try {
            double newWeight = Double.parseDouble(weightField.getText());
            double newHeight = Double.parseDouble(heightField.getText());
            int newAge = Integer.parseInt(ageField.getText());

            // Reuse the register logic to update
            userService.registerOrUpdateUser(
                currentUser.getName(), newAge, newHeight, newWeight, 
                currentUser.getSex(), "Moderately Active"
            );

            Main.setRoot("MainDashboard");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numbers.");
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        Main.setRoot("MainDashboard");
    }
}