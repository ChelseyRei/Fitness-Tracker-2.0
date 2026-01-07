package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import service.GoalService;
import model.Goal;
import model.GoalStatus;
import java.io.IOException;
import java.time.LocalDate;

public class SetGoalController {

    @FXML private TextField titleField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField targetValueField;
    @FXML private DatePicker endDatePicker;

    private final GoalService goalService = new GoalService();

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll("Weight Loss", "Muscle Gain", "Consistency");
    }

    @FXML
    private void handleSave() throws IOException {
        try {
            String title = titleField.getText();
            String type = typeComboBox.getValue();
            double target = Double.parseDouble(targetValueField.getText());
            LocalDate end = endDatePicker.getValue();

            Goal goal = new Goal(0, title, "General", LocalDate.now(), end, type, 0.0, target, GoalStatus.IN_PROGRESS);
            goalService.addGoal(goal);

            Main.setRoot("MainDashboard");
        } catch (Exception e) {
            System.out.println("Error saving goal.");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        Main.setRoot("MainDashboard");
    }
}