package model;

import java.time.LocalDate;

public class PersonalRecord {
    private String exerciseName;
    private int durationMinutes;
    private int reps;
    private double weightKg;
    private LocalDate date;

    public PersonalRecord(String exerciseName, int durationMinutes, int reps, double weightKg, LocalDate date) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.reps = reps;
        this.weightKg = weightKg;
        this.date = date;
    }

    public String getExerciseName() { return exerciseName; }
    public double getWeightKg() { return weightKg; }
    public int getReps() { return reps; }
    public int getDurationMinutes() { return durationMinutes; }
    public LocalDate getDate() { return date; }
}