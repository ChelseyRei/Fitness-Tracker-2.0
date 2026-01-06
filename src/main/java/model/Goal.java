package model;

import java.time.LocalDate;

public class Goal {
    private int id;
    private String goalTitle;
    private String exerciseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goalType;
    private double currentValue;
    private double targetValue;
    private GoalStatus status;

    public Goal(int id, String title, String exerciseName, LocalDate start, LocalDate end, 
                String type, double current, double target, GoalStatus status) {
        this.id = id;
        this.goalTitle = title;
        this.exerciseName = exerciseName;
        this.startDate = start;
        this.endDate = end;
        this.goalType = type;
        this.currentValue = current;
        this.targetValue = target;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; } // Needed for DAO to set ID after insert
    public String getGoalTitle() { return goalTitle; }
    public String getExerciseName() { return exerciseName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getGoalType() { return goalType; }
    public double getCurrentValue() { return currentValue; }
    public double getTargetValue() { return targetValue; }
    public GoalStatus getStatus() { return status; }
}