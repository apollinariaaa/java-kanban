
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        ));

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        System.out.println("Понедельник:");
        for (TrainingSession ts : mondaySessions) {
            System.out.println(ts.getGroup().getTitle() + " в "
                    + ts.getTimeOfDay().getHours() + ":"
                    + ts.getTimeOfDay().getMinutes());
        }

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        System.out.println("Вторник: " +
                (tuesdaySessions.isEmpty() ? "нет занятий" : "есть занятия"));
    }


    @Test
    void testGetTrainingSessionsForDayMultipleSessionsOrdered() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(
                new Group("Акробатика для детей", Age.CHILD, 60),
                coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        ));

        timetable.addNewTrainingSession(new TrainingSession(
                new Group("Йога для взрослых", Age.ADULT, 90),
                coach, DayOfWeek.THURSDAY, new TimeOfDay(11, 30)
        ));

        timetable.addNewTrainingSession(new TrainingSession(
                new Group("Гимнастика", Age.CHILD, 60),
                coach, DayOfWeek.THURSDAY, new TimeOfDay(16, 0)
        ));

        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        System.out.println("Четверг (по порядку):");
        for (TrainingSession ts : thursdaySessions) {
            System.out.println(ts.getGroup().getTitle() + " в "
                    + ts.getTimeOfDay().getHours() + ":"
                    + ts.getTimeOfDay().getMinutes());
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
