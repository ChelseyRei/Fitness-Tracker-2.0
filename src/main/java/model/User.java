package model;

import java.time.LocalDate;

public class User {
    private String name;
    private int age;
    private double heightCm;
    private double weightKg;
    private String sex;
    private double bmi;
    private double bmr;
    private int currentStreak;
    private LocalDate lastWorkoutDate;

    // Constructor for loading from DB
    public User(String name, int age, double heightCm, double weightKg, String sex, double bmi, double bmr, int currentStreak, LocalDate lastWorkoutDate) {
        this.name = name;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.sex = sex;
        this.bmi = bmi;
        this.bmr = bmr;
        this.currentStreak = currentStreak;
        this.lastWorkoutDate = lastWorkoutDate;
    }

    // Constructor for new registration
    public User(String name, int age, double heightCm, double weightKg, String sex, double bmi, double bmr) {
        this(name, age, heightCm, weightKg, sex, bmi, bmr, 0, null);
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getHeightCm() { return heightCm; }
    public double getWeightKg() { return weightKg; }
    public String getSex() { return sex; }
    public double getBMI() { return bmi; }
    public double getBMR() { return bmr; }
    public int getCurrentStreak() { return currentStreak; }
    public LocalDate getLastWorkoutDate() { return lastWorkoutDate; }
}