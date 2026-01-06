package model;

import java.time.LocalDate;

public abstract class Workout {
    protected int id;
    protected String name;
    protected String type;
    protected LocalDate date;
    protected int durationMinutes;
    protected double caloriesBurned;

    public Workout(int id, String name, String type, LocalDate date, double caloriesBurned, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.caloriesBurned = caloriesBurned;
        this.durationMinutes = durationMinutes;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getCaloriesBurned() { return caloriesBurned; }
}