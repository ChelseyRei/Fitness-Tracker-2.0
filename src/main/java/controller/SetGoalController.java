package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.GoalService;
import model.Goal;
import model.GoalStatus;
import java.io.IOException;
import java.time.LocalDate;

public class SetGoalController {

    // FXML IDs must match exactly!
    @FXML private ComboBox<String> goalTypeComboBox;
    @FXML private TextArea goalDescriptionTextArea;
    @FXML private TextField targetTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button saveGoalButton;

    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final GoalService goalService = new GoalService();

    @FXML
    public void initialize() {
        // 1. Setup Dropdown
        goalTypeComboBox.getItems().addAll("Weight Loss", "Muscle Gain", "Consistency", "Endurance");
        startDatePicker.setValue(LocalDate.now());

        // 2. Setup Save Button
        saveGoalButton.setOnAction(e -> handleSave());

        // 3. Setup Navigation
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        // We are already on "SetGoal", so goalsNavButton can just refresh or do nothing
        goalsNavButton.setOnAction(e -> navigate("SetGoal")); 
    }

    private void handleSave() {
        try {
            String type = goalTypeComboBox.getValue();
            String description = goalDescriptionTextArea.getText();
            String targetStr = targetTextField.getText();
            LocalDate end = endDatePicker.getValue();

            if (type != null && !description.isEmpty() && !targetStr.isEmpty() && end != null) {
                double target = Double.parseDouble(targetStr);
                Goal goal = new Goal(0, description, "General", LocalDate.now(), end, type, 0.0, target, GoalStatus.IN_PROGRESS);
                
                goalService.addGoal(goal);
                System.out.println("Goal Saved!");
                navigate("MainDashboard");
            } else {
                System.out.println("Please fill all fields.");
            }
        } catch (Exception e) {
            System.out.println("Error saving goal: " + e.getMessage());
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