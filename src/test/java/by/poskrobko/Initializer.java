package by.poskrobko;

import by.poskrobko.repository.impl.*;
import by.poskrobko.util.DBManager;

import static by.poskrobko.TestData.*;

public class Initializer {

    public static void main(String[] args) {

        var gradeRep = new GradeRepositoryImpl();
        var groupRep = new GroupRepositoryImpl();
        var langRep = new LanguageRepositoryImpl();
        var paymentRep = new PaymentRepositoryImpl();
        var scheduleRep = new ScheduleRepositoryImpl();
        var studentGradeDAO = new StudentGradeDAOImpl();
        var studentGroupDAO = new StudentGroupDAOImpl();
        var teacherRep = new TeacherRepositoryImpl();
        var userRep = new UserRepositoryImpl();
        var userRoleDAO = new UserRoleDaoImpl();

        DBManager.dropDatabase();
        DBManager.initDatabase();

        langRep.save(ENGLISH);
        langRep.save(PORTUGUESE);
        langRep.save(ITALIAN);
        langRep.save(FRENCH);

        gradeRep.save(GRADE_ENGLISH_A1);
        gradeRep.save(GRADE_ENGLISH_A2);
        gradeRep.save(GRADE_ENGLISH_B1);
        gradeRep.save(GRADE_ENGLISH_B2);

        userRep.save(USER_1);
        userRep.save(USER_2);
        userRep.save(USER_3);
        userRep.save(USER_4);

        teacherRep.save(TEACHER);

        groupRep.save(GROUP_1);
        groupRep.save(GROUP_2);

        studentGroupDAO.save(USER_3_GROUP_1);
        studentGroupDAO.save(USER_4_GROUP_1);

        paymentRep.save(PAYMENT_1_USER_3);
        paymentRep.save(PAYMENT_2_USER_3);
    }
}
