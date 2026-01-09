package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.UserService;
import service.WorkoutService;
import model.User;
import model.Workout;
import model.StrengthWorkout;
import model.CardioWorkout;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainDashboardController {

    @FXML private Button profileButton;
    @FXML private Label streakText;
    @FXML private Label dateLabel;
    @FXML private ScrollPane activityScrollPane;
    @FXML private VBox activityList;
    @FXML private HBox reminderCard;
    @FXML private Button logWorkoutButton;
    @FXML private Button refreshQuoteButton;
    @FXML private Label quoteText;
    @FXML private Label quoteAuthor;
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    // Services to access the database
    private final UserService userService = new UserService();
    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        setupDateLabel();
        setupStreakText();
        loadTodaysActivities();
        loadRandomQuote();
        setupEventHandlers();
        
        // Refresh streak logic on load to ensure it's up to date
        userService.refreshStreak();
    }

    private void setupDateLabel() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        dateLabel.setText(today.format(formatter));
    }

    private void setupStreakText() {
        User user = userService.getUserProfile();
        
        if (user != null) {
            int streakCount = user.getCurrentStreak();
            if (streakCount == 0) {
                streakText.setText("You haven't started a streak yet. Do one activity today to kick things off.");
            } else {
                streakText.setText("You are on a " + streakCount + "-day streak. Keep it up!");
            }
        } else {
            streakText.setText("Welcome! Log your first workout to start.");
        }
    }

    private void loadTodaysActivities() {
        // Clear previous dummy data
        activityList.getChildren().clear();

        // Get workouts for TODAY from the database
        LocalDate today = LocalDate.now();
        List<Workout> workouts = workoutService.getWorkoutsByDate(today, today);

        if (workouts.isEmpty()) {
            Label emptyLabel = new Label("No activities logged today.");
            emptyLabel.setStyle("-fx-text-fill: #7d7d7d; -fx-padding: 10;");
            activityList.getChildren().add(emptyLabel);
        } else {
            for (Workout workout : workouts) {
                String details = "";
                if (workout instanceof StrengthWorkout) {
                    StrengthWorkout sw = (StrengthWorkout) workout;
                    details = sw.getSetCount() + " sets | " + sw.getRepCount() + " reps | " + sw.getExternalWeightKg() + "kg";
                } else if (workout instanceof CardioWorkout) {
                    CardioWorkout cw = (CardioWorkout) workout;
                    details = cw.getDurationMinutes() + " mins | " + cw.getDistanceKm() + " km";
                }
                addActivityItem(workout.getName(), details);
            }
        }
    }

    private void addActivityItem(String activityName, String details) {
        HBox item = new HBox(10);
        item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        item.getStyleClass().add("activity-item");

        Label checkmark = new Label();
        checkmark.getStyleClass().add("svg-icon-checkmark");

        VBox textBox = new VBox();
        Label nameLabel = new Label(activityName);
        nameLabel.getStyleClass().add("activity-name");
        Label detailsLabel = new Label(details);
        detailsLabel.getStyleClass().add("activity-details");
        textBox.getChildren().addAll(nameLabel, detailsLabel);

        item.getChildren().addAll(checkmark, textBox);
        activityList.getChildren().add(item);
    }

    private void loadRandomQuote() {
        // You can connect this to a quote service later. 
        // For now, simple rotation or fixed quote is fine.
        quoteText.setText("You just can't beat the person who never gives up.");
        quoteAuthor.setText("- Babe Ruth");
    }

    private void setupEventHandlers() {
        // We use lambdas to handle the IOExceptions from navigation
        profileButton.setOnAction(e -> navigate("EditProfileController"));
        reminderCard.setOnMouseClicked(e -> navigate("LogSelectionController")); // Direct to logging
        logWorkoutButton.setOnAction(e -> navigate("LogSelectionController"));
        refreshQuoteButton.setOnAction(e -> loadRandomQuote());
        
        // Navigation Bar
        summaryNavButton.setOnAction(e -> navigate("ProgressReportController"));
        // homeNavButton is already here, so no action needed or just refresh
        homeNavButton.setOnAction(e -> initialize()); 
        goalsNavButton.setOnAction(e -> navigate("SetGoalController"));
    }

    // Helper method to handle navigation via Main.setRoot
    private void navigate(String fxmlName) {
        try {
            Main.setRoot(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxmlName);
        }
    }
}