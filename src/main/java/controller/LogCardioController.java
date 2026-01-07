package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.WorkoutService;
import model.CardioWorkout;
import java.io.IOException;
import java.time.LocalDate;

public class LogCardioController {

    @FXML private TextField exerciseNameField;
    @FXML private TextField durationField;
    @FXML private TextField distanceField;
    @FXML private DatePicker datePicker;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    private void handleSave() {
        try {
            String name = exerciseNameField.getText();
            int duration = Integer.parseInt(durationField.getText());
            double distance = Double.parseDouble(distanceField.getText());
            LocalDate date = datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now();

            // Simple calorie calc (approximate)
            double calories = duration * 8.0; 

            CardioWorkout workout = new CardioWorkout(0, name, "Cardio", date, calories, duration, distance);

            if (workoutService.logWorkout(workout)) {
                // Check PR for distance or duration
                workoutService.checkPersonalRecord(name, 0, 0, duration);
                goBack();
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        goBack();
    }

    private void goBack() throws IOException {
        Main.setRoot("LogSelection");
    }
}