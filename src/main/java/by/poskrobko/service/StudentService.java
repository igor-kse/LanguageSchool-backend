package by.poskrobko.service;

import by.poskrobko.dto.StudentDTO;
import by.poskrobko.mapper.StudentMapper;
import by.poskrobko.model.Group;
import by.poskrobko.model.Role;
import by.poskrobko.model.Student;
import by.poskrobko.model.User;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.repository.StudentGroupDAO;
import by.poskrobko.repository.StudentRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.repository.impl.GroupRepositoryImpl;
import by.poskrobko.repository.impl.StudentGroupDAOImpl;
import by.poskrobko.repository.impl.StudentGroupDAOImpl.StudentGroup;
import by.poskrobko.repository.impl.StudentRepositoryImpl;
import by.poskrobko.repository.impl.UserRepositoryImpl;
import exception.NotExistingEntityException;

import java.util.List;
import java.util.Set;

public class StudentService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final GroupRepository groupRepository = new GroupRepositoryImpl();
    private final StudentRepository studentRepository = new StudentRepositoryImpl();
    private final StudentGroupDAO studentGroupDAO = new StudentGroupDAOImpl();
    private final StudentMapper studentMapper = new StudentMapper();

    public void enrollStudentToGroup(String userId, String groupId) {
        User user = userRepository.findById(userId);
        Group group = groupRepository.findById(groupId);
        if (user == null || group == null) {
            throw new NotExistingEntityException("User or group not found \n  User: " + user + "\n  Group" + group);
        }

        Set<Role> roles = user.getRoles();
        if (!roles.contains(Role.STUDENT)) {
            roles.add(Role.STUDENT);
            userRepository.update(user);
        }
        studentGroupDAO.save(new StudentGroup(userId, groupId));
    }

    public void removeStudentFromGroup(String userId, String groupId) {
        studentGroupDAO.deleteByStudentAndGroupId(userId, groupId);
    }

    public List<StudentDTO> getAllStudents() {
        return studentMapper.toStudents(studentRepository.getAllStudents());
    }

    public List<StudentDTO> getAllStudents(String groupId) {
        var students = studentRepository.getAllStudents(groupId);
        return studentMapper.toStudents(students);
    }

    public StudentDTO save(StudentDTO dto) {
        User user;
        if (dto.id() == null) {
            user = new User(dto.firstName(), dto.lastName(), dto.email(), dto.firstName() + dto.lastName(),
                    Set.of(Role.USER, Role.STUDENT), null);
            userRepository.save(user);
        } else {
            user = userRepository.findById(dto.id());
            if (user == null) {
                throw new NotExistingEntityException("User not found \n  User: " + user + "\n  Id: " + dto.id());
            }
            user.getRoles().add(Role.STUDENT);
            userRepository.update(user);
        }
        studentRepository.save(studentMapper.toStudent(dto, user));
        return dto;
    }

    public void update(StudentDTO dto) {
        var user = userRepository.findById(dto.id());
        if (user == null) {
            throw new NotExistingEntityException("User not found \n  User: " + user);
        }
        studentRepository.update(new Student(dto.age(), dto.channel(), dto.hobbies(), dto.note(), user));
    }

    public void delete(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotExistingEntityException("User not found \n  User: " + user);
        }
        user.getRoles().remove(Role.STUDENT);
        userRepository.update(user);
        studentRepository.delete(id);
    }
}
