
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        // Проверяем понедельник
        Collection<List<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        int count = 0;
        for (List<TrainingSession> list : mondaySessions) {
            count += list.size();
        }

        assertEquals(1, count);

        // Проверяем вторник
        Collection<List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdult = new TrainingSession(
                groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)
        );
        timetable.addNewTrainingSession(thursdayAdult);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        );
        TrainingSession thursdayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        );
        TrainingSession saturdayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.SATURDAY, new TimeOfDay(10, 0)
        );

        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(saturdayChild);

        // Понедельник: должно быть 1 занятие
        Collection<List<TrainingSession>> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        int mondayCount = 0;
        for (List<TrainingSession> list : mondaySessions) {
            mondayCount += list.size();
        }
        assertEquals(1, mondayCount);

        // Четверг: должно быть 2 занятия
        Collection<List<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        int thursdayCount = 0;
        for (List<TrainingSession> list : thursdaySessions) {
            thursdayCount += list.size();
        }
        assertEquals(2, thursdayCount);

        // Проверяем порядок по времени: 13:00 -> 20:00
        List<List<TrainingSession>> thursdayLists = (List<List<TrainingSession>>) thursdaySessions;
        // Если cast не проходит, можно просто проверить часы вручную в цикле
        int firstHour = -1;
        int secondHour = -1;
        int i = 0;
        for (List<TrainingSession> list : thursdaySessions) {
            for (TrainingSession ts : list) {
                if (i == 0) firstHour = ts.getTimeOfDay().getHours();
                if (i == 1) secondHour = ts.getTimeOfDay().getHours();
            }
            i++;
        }
        assertEquals(13, firstHour);
        assertEquals(20, secondHour);

        // Вторник: должно быть 0 занятий
        Collection<List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверяем понедельник в 13:00
        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        assertEquals(1, sessionsAt13.size());
        assertEquals(singleTrainingSession, sessionsAt13.get(0));

        // Проверяем понедельник в 14:00
        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(sessionsAt14.isEmpty());
    }
}