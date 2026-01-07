package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.WorkoutService;
import model.Workout;
import java.io.IOException;
import java.util.List;

public class LogSelectionController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private Button strengthButton; // Matches fx:id="strengthButton"
    @FXML private Button cardioButton;   // Matches fx:id="cardioButton"
    @FXML private VBox historyList;      // Matches fx:id="historyList"
    
    // Bottom Navigation Buttons
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        // 1. SETUP MAIN BUTTONS (This makes them click!)
        strengthButton.setOnAction(e -> navigate("LogStrength"));
        cardioButton.setOnAction(e -> navigate("LogCardio"));
        
        // 2. SETUP TOP NAVIGATION
        backButton.setOnAction(e -> navigate("MainDashboard"));
        profileButton.setOnAction(e -> navigate("EditProfile"));

        // 3. SETUP BOTTOM NAVIGATION
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));

        // 4. LOAD DATA
        loadWorkoutHistory();
    }

    private void loadWorkoutHistory() {
        historyList.getChildren().clear();
        List<Workout> workouts = workoutService.getWorkoutHistory();

        if (workouts.isEmpty()) {
            Label placeholder = new Label("No recent workouts.");
            placeholder.setStyle("-fx-text-fill: gray; -fx-padding: 10;");
            historyList.getChildren().add(placeholder);
        } else {
            for (Workout w : workouts) {
                // Simple Label for each history item
                Label label = new Label(w.getName() + " (" + w.getDate() + ")");
                label.getStyleClass().add("history-item"); 
                // Add some basic styling or use a custom HBox like before if preferred
                label.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #eee; -fx-border-width: 0 0 1 0;");
                historyList.getChildren().add(label);
            }
        }
    }

    // Uses the Main.java logic to switch screens safely
    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading: " + fxml + ". Make sure the file exists in /resources/fxml/");
        }
    }
}