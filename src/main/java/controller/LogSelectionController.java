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

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        // Workout Type Selections
        strengthButton.setOnAction(e -> navigate("LogStrength"));
        cardioButton.setOnAction(e -> navigate("LogCardio"));

        // Bottom Navigation Bar
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));

        // Top Bar
        backButton.setOnAction(e -> navigate("MainDashboard"));
        profileButton.setOnAction(e -> navigate("EditProfile"));

        loadWorkoutHistory();
    }

    private void loadWorkoutHistory() {
        historyList.getChildren().clear();
        List<Workout> workouts = workoutService.getWorkoutHistory();

        if (workouts.isEmpty()) {
            historyList.getChildren().add(new Label("No recent workouts."));
        } else {
            for (Workout workout : workouts) {
                String details = (workout instanceof StrengthWorkout sw) 
                    ? sw.getSetCount() + " sets | " + sw.getRepCount() + " reps" 
                    : ((CardioWorkout) workout).getDurationMinutes() + " mins";
                addHistoryItem(workout.getName(), details);
            }
        }
    }

    private void addHistoryItem(String name, String details) {
        VBox item = new VBox(new Label(name), new Label(details));
        item.getStyleClass().add("history-item");
        historyList.getChildren().add(item);
    }

    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}