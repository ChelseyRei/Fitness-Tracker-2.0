package model;

import java.time.LocalDate;

public class BodyMetric {
    private int id;
    private int age;
    private double heightCm;
    private double weightKg;
    private double bmi;
    private LocalDate date;

    public BodyMetric(int id, int age, double heightCm, double weightKg, double bmi, LocalDate date) {
        this.id = id;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bmi = bmi;
        this.date = date;
    }

    public int getAge() { return age; }
    public double getHeightCm() { return heightCm; }
    public double getWeightKg() { return weightKg; }
    public double getBMI() { return bmi; }
    public LocalDate getDate() { return date; }
}