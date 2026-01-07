package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.WorkoutService;
import model.Workout;
import model.StrengthWorkout;
import model.CardioWorkout;

import java.io.IOException;
import java.util.List;

public class LogSelectionController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private Button strengthButton;
    @FXML private Button cardioButton;
    @FXML private ScrollPane historyScrollPane;
    @FXML private VBox historyList;
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    // Connect to the backend service
    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        loadWorkoutHistory();
        setupEventHandlers();
    }

    private void loadWorkoutHistory() {
        // Clear dummy data first
        historyList.getChildren().clear();

        // Fetch actual history from the database
        List<Workout> workouts = workoutService.getWorkoutHistory();

        if (workouts.isEmpty()) {
            Label emptyLabel = new Label("No recent workouts.");
            emptyLabel.setStyle("-fx-text-fill: #7d7d7d; -fx-padding: 10;");
            historyList.getChildren().add(emptyLabel);
        } else {
            for (Workout workout : workouts) {
                String iconType = "Dumbbell"; // Default
                String details = "";

                if (workout instanceof StrengthWorkout) {
                    StrengthWorkout sw = (StrengthWorkout) workout;
                    iconType = "Dumbbell";
                    details = sw.getSetCount() + " sets | " + sw.getRepCount() + " reps | " + sw.getExternalWeightKg() + "kg";
                } else if (workout instanceof CardioWorkout) {
                    CardioWorkout cw = (CardioWorkout) workout;
                    iconType = "Cardio";
                    details = cw.getDurationMinutes() + " mins | " + cw.getDistanceKm() + " km";
                }

                addHistoryItem(iconType, workout.getName(), details);
            }
        }
    }

    private void addHistoryItem(String iconType, String workoutName, String details) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("history-item");

        Label icon = new Label();
        // Use the CSS classes defined in your style sheets
        if (iconType.equals("Cardio")) {
            icon.getStyleClass().add("svg-icon-cardio");
        } else {
            icon.getStyleClass().add("svg-icon-dumbbell");
        }

        VBox textBox = new VBox(2);
        Label nameLabel = new Label(workoutName);
        nameLabel.getStyleClass().add("history-workout-name");
        
        Label detailsLabel = new Label(details);
        detailsLabel.getStyleClass().add("history-workout-details");
        
        textBox.getChildren().addAll(nameLabel, detailsLabel);

        item