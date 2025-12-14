
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(singleTrainingSession);

        // Понедельник
        Collection<List<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        int count = 0;
        for (List<TrainingSession> list : mondaySessions) {
            count += list.size();
        }

        System.out.println("Понедельник: занятий " + count);

        // Вторник
        Collection<List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        if (tuesdaySessions.isEmpty()) {
            System.out.println("Вторник: Нет занятий");
        }
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        timetable.addNewTrainingSession(new TrainingSession(
                groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)
        ));

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        timetable.addNewTrainingSession(new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        ));
        timetable.addNewTrainingSession(new TrainingSession(
                groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        ));
        timetable.addNewTrainingSession(new TrainingSession(
                groupChild, coach, DayOfWeek.SATURDAY, new TimeOfDay(10, 0)
        ));

        // Понедельник
        Collection<List<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int mondayCount = 0;
        for (List<TrainingSession> list : mondaySessions) {
            mondayCount += list.size();
        }
        System.out.println("Понедельник: занятий " + mondayCount);

        // Четверг
        Collection<List<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        int thursdayCount = 0;
        int firstHour = -1;
        int secondHour = -1;
        int i = 0;

        for (List<TrainingSession> list : thursdaySessions) {
            for (TrainingSession ts : list) {
                thursdayCount++;
                if (i == 0) firstHour = ts.getTimeOfDay().getHours();
                if (i == 1) secondHour = ts.getTimeOfDay().getHours();
            }
            i++;
        }

        System.out.println("Четверг: занятий " + thursdayCount);
        System.out.println("Первое занятие: " + firstHour + ":00");
        System.out.println("Второе занятие: " + secondHour + ":00");

        // Вторник
        Collection<List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        if (tuesdaySessions.isEmpty()) {
            System.out.println("Вторник: Нет занятий");
        }
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        ));

        // Понедельник в 13:00
        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        System.out.println("Понедельник 13:00: " + sessionsAt13.size() + " занятий");

        // Понедельник в 14:00
        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        if (sessionsAt14.isEmpty()) {
            System.out.println("Понедельник 14:00: Нет занятий");
        }
    }
}
