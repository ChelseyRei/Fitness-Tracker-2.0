package model;

import java.time.LocalDate;

public class CardioWorkout extends Workout {
    private double distanceKm;

    public CardioWorkout(int id, String name, String type, LocalDate date, double calories, int duration, double distanceKm) {
        super(id, name, type, date, calories, duration);
        this.distanceKm = distanceKm;
    }

    public double getDistanceKm() { return distanceKm; }
}