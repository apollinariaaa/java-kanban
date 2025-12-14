import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable
            = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
        if (dayMap == null) {
            dayMap = new TreeMap<>();
            timetable.put(day, dayMap);
        }

        List<TrainingSession> sessions = dayMap.get(time);
        if (sessions == null) {
            sessions = new ArrayList<>();
            dayMap.put(time, sessions);
        }

        sessions.add(trainingSession);
    }

    public Collection<List<TrainingSession>> getTrainingSessionsForDay(
            DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        return dayMap.values();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        return dayMap.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public Map<Coach, Integer> getCountByCoaches() {

        // 1. Считаем количество тренировок у каждого тренера
        Map<Coach, Integer> counters = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            for (List<TrainingSession> sessionsAtTime : dayMap.values()) {
                for (TrainingSession session : sessionsAtTime) {
                    Coach coach = session.getCoach();
                    counters.put(coach, counters.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<Map.Entry<Coach, Integer>> entries =
                new ArrayList<>(counters.entrySet());

        entries.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Map<Coach, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
