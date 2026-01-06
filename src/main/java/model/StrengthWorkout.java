package model;

import java.time.LocalDate;

public class StrengthWorkout extends Workout {
    private int setCount;
    private int repCount;
    private double externalWeightKg;
    private double trainingVolumeKg;
    private double bodyWeightFactor;

    public StrengthWorkout(int id, String name, String type, LocalDate date, double calories, int duration,
                           int sets, int reps, double weight, double volume, double bwFactor) {
        super(id, name, type, date, calories, duration);
        this.setCount = sets;
        this.repCount = reps;
        this.externalWeightKg = weight;
        this.trainingVolumeKg = volume;
        this.bodyWeightFactor = bwFactor;
    }

    public int getSetCount() { return setCount; }
    public int getRepCount() { return repCount; }
    public double getExternalWeightKg() { return externalWeightKg; }
    public double getTrainingVolumeKg() { return trainingVolumeKg; }
    public double getBodyWeightFactor() { return bodyWeightFactor; }
}