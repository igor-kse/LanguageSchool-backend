package by.poskrobko.service;

import by.poskrobko.dto.TeacherDTO;
import by.poskrobko.mapper.TeacherMapper;
import by.poskrobko.model.Group;
import by.poskrobko.model.Teacher;
import by.poskrobko.model.User;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.repository.StudentGroupDAO;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.impl.GroupRepositoryImpl;
import by.poskrobko.repository.impl.StudentGroupDAOImpl;
import by.poskrobko.repository.impl.TeacherRepositoryImpl;

import java.util.List;
import java.util.Set;

public class TeacherService {
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    private final StudentGroupDAO studentGroupDAO = new StudentGroupDAOImpl();
    private final GroupRepository groupRepository = new GroupRepositoryImpl();

    private final TeacherMapper teacherMapper = new TeacherMapper();

    public TeacherDTO addTeacher(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Teacher teacher = new Teacher(user, "", Set.of());
        teacherRepository.save(teacher);
        return teacherMapper.toTeacherDTO(teacher);
    }

    public void deleteTeacher(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher id cannot be null");
        }
        teacherRepository.delete(id);
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherMapper.toTeachersDTO(teacherRepository.findAll());
    }

    public void update(TeacherDTO teacherDTO) {
        teacherRepository.update(teacherMapper.toTeacher(teacherDTO));
    }

    public List<Group> getTeacherGroups(String teacherId) {
        return groupRepository.findAllByTeacher(teacherId);
    }
}
