package by.poskrobko.service;

import by.poskrobko.model.Grade;
import by.poskrobko.model.Group;
import by.poskrobko.model.Teacher;
import by.poskrobko.repository.GradeRepository;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.repository.StudentGroupDAO;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.impl.GradeRepositoryImpl;
import by.poskrobko.repository.impl.GroupRepositoryImpl;
import by.poskrobko.repository.impl.StudentGroupDAOImpl;
import by.poskrobko.repository.impl.TeacherRepositoryImpl;
import by.poskrobko.util.Util;
import exception.NotExistingEntityException;

import java.util.List;
import java.util.Objects;

public class GroupService {

    private final GroupRepository groupRepository = new GroupRepositoryImpl();
    private final StudentGroupDAO studentGroupDAO = new StudentGroupDAOImpl();
    private final GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();

    public void create(String name, Grade grade, Teacher teacher) {
        Util.assureStringHasLength(name, "group name");
        Objects.requireNonNull(grade, "grade cannot be null");
        Objects.requireNonNull(teacher, "teacher cannot be null");
        Group group = new Group(teacher, name, grade);
        groupRepository.save(group);
    }

    public Group findById(String id) {
        Util.assureStringHasLength(id, "group id");
        return groupRepository.findById(id);
    }

    public List<Group> findAllByTeacher(String id) {
        Util.assureStringHasLength(id, "Teacher id");
        return groupRepository.findAllByTeacher(id);
    }

    public List<Group> findAllByUser(String id) {
        Objects.requireNonNull(id, "id cannot be null");
//        List<StudentGroupDAOImpl.StudentGroup> studentGroups = studentGroupDAO.findAllByUserId(id);
        return null;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void updateGroup(Group group) {
        Group existingGroup = groupRepository.findById(group.getId());
        if (existingGroup == null) {
            throw new NotExistingEntityException("Group " + group.getId() + " not found");
        }
        groupRepository.update(group);
    }

    public void deleteGroup(String id) {
        Group group = groupRepository.findById(id);
        if (group == null) {
            throw new NotExistingEntityException("Group " + id + " not found");
        }
        groupRepository.delete(id);
    }

    public void assignTeacher(String teacherId, String groupId) {
        Group group = groupRepository.findById(groupId);
        Objects.requireNonNull(group);

        Teacher teacher = this.teacherRepository.findById(teacherId);
        Objects.requireNonNull(teacher);

        group.setTeacher(teacher);
        groupRepository.update(group);
    }

}
