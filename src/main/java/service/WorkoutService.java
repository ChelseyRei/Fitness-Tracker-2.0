package service;

import dao.WorkoutDAO;
import model.Workout;
import model.PersonalRecord;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkoutService {
    private WorkoutDAO workoutDAO;

    public WorkoutService() {
        this.workoutDAO = new WorkoutDAO();
    }

    public boolean logWorkout(Workout workout) {
        try {
            workoutDAO.saveWorkout(workout);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Workout> getWorkoutHistory() {
        try {
            return workoutDAO.loadWorkouts();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    // Filter history by date range
    public List<Workout> getWorkoutsByDate(LocalDate start, LocalDate end) {
        return getWorkoutHistory().stream()
                .filter(w -> !w.getDate().isBefore(start) && !w.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public void checkPersonalRecord(String exerciseName, double weight, int reps, int duration) {
        try {
            Map<String, PersonalRecord> prs = workoutDAO.loadPersonalRecords();
            PersonalRecord currentPR = prs.get(exerciseName);

            boolean isNewRecord = false;
            
            if (currentPR == null) {
                isNewRecord = true;
            } else if (weight > currentPR.getWeightKg()) {
                isNewRecord = true;
            }

            if (isNewRecord) {
                workoutDAO.updatePersonalRecord(exerciseName, weight, reps, duration, LocalDate.now());
                System.out.println("NEW PERSONAL RECORD SET!"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}