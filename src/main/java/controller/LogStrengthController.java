package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import service.WorkoutService;
import model.StrengthWorkout;
import java.io.IOException;
import java.time.LocalDate;

public class LogStrengthController {

    @FXML private TextField exerciseNameField;
    @FXML private TextField setsField;
    @FXML private TextField repsField;
    @FXML private TextField weightField;
    @FXML private TextField durationField;
    @FXML private DatePicker datePicker;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    private void handleSave() {
        try {
            String name = exerciseNameField.getText();
            int sets = Integer.parseInt(setsField.getText());
            int reps = Integer.parseInt(repsField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int duration = Integer.parseInt(durationField.getText());
            LocalDate date = datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now();

            // Simple calculation for calories (can be improved in Service)
            double calories = duration * 5.0; 
            double volume = sets * reps * weight;

            StrengthWorkout workout = new StrengthWorkout(
                0, name, "Strength", date, calories, duration,
                sets, reps, weight, volume, 0.0
            );

            boolean success = workoutService.logWorkout(workout);
            
            if (success) {
                // Check for Personal Record
                workoutService.checkPersonalRecord(name, weight, reps, duration);
                goBack();
            } else {
                System.out.println("Failed to save workout.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numbers.");
        } catch (IOException e) {
            e.printStackTrace();
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