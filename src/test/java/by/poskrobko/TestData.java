package by.poskrobko;

import by.poskrobko.model.*;
import by.poskrobko.repository.impl.StudentGradeDAOImpl.StudentGrade;
import by.poskrobko.repository.impl.StudentGroupDAOImpl.StudentGroup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class TestData {

    public static final Language ENGLISH = new Language("English", "", "");
    public static final Language PORTUGUESE = new Language("Portuguese", "", "");
    public static final Language ITALIAN = new Language("Italian", "", "");
    public static final Language FRENCH = new Language("French", "", "");

    public static final String GRADE_UUID_ENGLISH_A1 = "grade_uuid1";
    public static final String GRADE_UUID_ENGLISH_A2 = "grade_uuid2";
    public static final String GRADE_UUID_ENGLISH_B1 = "grade_uuid3";
    public static final String GRADE_UUID_ENGLISH_B2 = "grade_uuid4";

    public static final Grade GRADE_ENGLISH_A1 = new Grade(GRADE_UUID_ENGLISH_A1, ENGLISH, CEFRLevel.A1);
    public static final Grade GRADE_ENGLISH_A2 = new Grade(GRADE_UUID_ENGLISH_A2, ENGLISH, CEFRLevel.A2);
    public static final Grade GRADE_ENGLISH_B1 = new Grade(GRADE_UUID_ENGLISH_B1, ENGLISH, CEFRLevel.B1);
    public static final Grade GRADE_ENGLISH_B2 = new Grade(GRADE_UUID_ENGLISH_B2, ENGLISH, CEFRLevel.B2);

    public static final String USER_UUID_1 = "user_uuid_1";
    public static final String USER_UUID_2 = "user_uuid_2";
    public static final String USER_UUID_3 = "user_uuid_3";
    public static final String USER_UUID_4 = "user_uuid_4";
    public static final User USER_1 = new User(USER_UUID_1, "name1", "surname1", "email1@email.com", "password1", Set.of(Role.ADMIN), null);
    public static final User USER_2 = new User(USER_UUID_2, "name2", "surname2", "email2@email.com", "password2", Set.of(Role.TEACHER), null);
    public static final User USER_3 = new User(USER_UUID_3, "name3", "surname3", "email3@email.com", "password3", Set.of(Role.STUDENT), null);
    public static final User USER_4 = new User(USER_UUID_4, "name4", "surname4", "email4@email.com", "password4", Set.of(Role.STUDENT), null);

    public static final Teacher TEACHER = new Teacher(USER_2, "UofT", Set.of("English"));

    public static final StudentGrade USER_1_ENGLISH_A1 = new StudentGrade(USER_UUID_1, GRADE_UUID_ENGLISH_A1);
    public static final StudentGrade USER_1_ENGLISH_B2 = new StudentGrade(USER_UUID_1, GRADE_UUID_ENGLISH_B2);
    public static final StudentGrade USER_2_ENGLISH_A2 = new StudentGrade(USER_UUID_2, GRADE_UUID_ENGLISH_A2);
    public static final StudentGrade USER_3_ENGLISH_B1 = new StudentGrade(USER_UUID_3, GRADE_UUID_ENGLISH_B1);
    public static final StudentGrade USER_3_ENGLISH_B2 = new StudentGrade(USER_UUID_3, GRADE_UUID_ENGLISH_B2);

    public static final String GROUP_UUID_1 = "group_uuid_1";
    public static final String GROUP_UUID_2 = "group_uuid_2";
    public static final String GROUP_NAME_1 = "group_name_1";
    public static final String GROUP_NAME_2 = "group_name_2";
    public static final Group GROUP_1 = new Group(GROUP_UUID_1, GROUP_NAME_1, ENGLISH, new Scale("", "", Set.of()), new Scale.Level("", ""), TEACHER);
    public static final Group GROUP_2 = new Group(GROUP_UUID_2, GROUP_NAME_2, ENGLISH, new Scale("", "", Set.of()), new Scale.Level("", ""), TEACHER);

    public static final StudentGroup USER_3_GROUP_1 = new StudentGroup(USER_UUID_3, GROUP_UUID_1);
    public static final StudentGroup USER_4_GROUP_1 = new StudentGroup(USER_UUID_4, GROUP_UUID_1);

    public static final String PAYMENT_UUID_1 = "payment_uuid_1";
    public static final String PAYMENT_UUID_2 = "payment_uuid_2";
    public static final LocalDate PAYMENT_1_USER_3_DATE_1 = LocalDate.of(2024, 10, 11);
    public static final LocalDate PAYMENT_1_USER_3_DATE_2 = LocalDate.of(2024, 10, 20);
    public static final long AMOUNT_1 = 10000;
    public static final long AMOUNT_2 = 20000;
    public static final String DESCRIPTION_1 = "For some course 1";
    public static final String DESCRIPTION_2 = "For some course 2";
    public static final Payment PAYMENT_1_USER_3 =
            new Payment(PAYMENT_UUID_1, PAYMENT_1_USER_3_DATE_1, USER_3, AMOUNT_1, DESCRIPTION_1);
    public static final Payment PAYMENT_2_USER_3 =
            new Payment(PAYMENT_UUID_2, PAYMENT_1_USER_3_DATE_2, USER_3, AMOUNT_2, DESCRIPTION_2);

    public static final String SCHEDULE_UUID_1 = "schedule_uuid_1";
    public static final String SCHEDULE_UUID_2 = "schedule_uuid_2";
    public static final LocalTime SCHEDULE_1_START_TIME = LocalTime.of(12, 0, 0);
    public static final LocalTime SCHEDULE_1_END_TIME = LocalTime.of(15, 0, 0);
    public static final LocalTime SCHEDULE_2_START_TIME = LocalTime.of(18, 0, 0);
    public static final LocalTime SCHEDULE_2_END_TIME = LocalTime.of(21, 0, 0);
    public static final Schedule SCHEDULE_1 = new Schedule(SCHEDULE_UUID_1, GROUP_1, DayOfWeek.MONDAY, SCHEDULE_1_START_TIME, SCHEDULE_1_END_TIME);
    public static final Schedule SCHEDULE_2 = new Schedule(SCHEDULE_UUID_2, GROUP_1, DayOfWeek.WEDNESDAY, SCHEDULE_2_START_TIME, SCHEDULE_2_END_TIME);
}
