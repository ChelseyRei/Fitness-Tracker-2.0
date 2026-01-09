package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.UserService;
import model.User;
import java.io.IOException;

public class EditProfileController {

    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private TextField ageField;
    
    // You likely have these buttons in your FXML, or need to add them
    @FXML private Button saveButton; 
    @FXML private Button cancelButton;
    @FXML private Button profileButton;

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

        // Setup Buttons
        // Note: If these buttons crash with NullPointerException, ensure fx:id="saveButton" is in your FXML
        saveButton.setOnAction(e -> handleSave());
        cancelButton.setOnAction(e -> navigate("MainDashboard"));
        profileButton.setOnAction(e -> navigate("EditProfile"));

    }

    private void handleSave() {
        try {
            double newWeight = Double.parseDouble(weightField.getText());
            double newHeight = Double.parseDouble(heightField.getText());
            int newAge = Integer.parseInt(ageField.getText());

            userService.registerOrUpdateUser(
                currentUser.getName(), newAge, newHeight, newWeight, 
                currentUser.getSex(), "Moderately Active"
            );

            navigate("MainDashboard");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numbers.");
        }
    }

    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}