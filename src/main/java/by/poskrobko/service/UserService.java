package by.poskrobko.service;

import by.poskrobko.dto.UserDTO;
import by.poskrobko.dto.UserToRegisterDTO;
import by.poskrobko.dto.UserToUpdateDTO;
import by.poskrobko.mapper.UserMapper;
import by.poskrobko.model.Role;
import by.poskrobko.model.Teacher;
import by.poskrobko.model.User;
import by.poskrobko.repository.StudentRepository;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.repository.UserRoleDao;
import by.poskrobko.repository.impl.StudentRepositoryImpl;
import by.poskrobko.repository.impl.TeacherRepositoryImpl;
import by.poskrobko.repository.impl.UserRepositoryImpl;
import by.poskrobko.repository.impl.UserRoleDaoImpl;
import exception.ExistingEntityException;
import exception.NotExistingEntityException;

import java.util.Collections;
import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    private final StudentRepository studentRepository = new StudentRepositoryImpl();
    private final UserRoleDao userRoleDAO = new UserRoleDaoImpl();
    private final UserMapper userMapper = new UserMapper();

    protected static final String USER_EXISTS = "User %s already exists";
    protected static final String USER_NOT_EXISTS = "User %s doesn't exist";

    public UserDTO registerUser(UserToRegisterDTO userDto) {
        if (userRepository.findByEmail(userDto.email()) != null) {
            throw new ExistingEntityException(String.format(USER_EXISTS, userDto.email()));
        }
        User user = userMapper.toUser(userDto);
        userRepository.save(user);

        if (user.getRoles().contains(Role.TEACHER) && teacherRepository.findById(user.getUserId()) == null) {
            teacherRepository.save(new Teacher(user, "", Collections.emptySet()));
        }
        return userMapper.toUserDTO(user);
    }

    public void addAdminRights(String userId) {
        userRoleDAO.save(userId, Role.ADMIN);
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NotExistingEntityException(String.format(USER_NOT_EXISTS, id));
        }
        return userMapper.toUserDTO(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotExistingEntityException(String.format(USER_NOT_EXISTS, email));
        }
        return userMapper.toUserDTO(user);
    }

    public boolean checkByCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return userMapper.toUserDTO(users);
    }

    public void update(UserToUpdateDTO userToUpdate) {
        var user = userMapper.toUser(userToUpdate);
        userRepository.update(user);

        if (!user.getRoles().contains(Role.TEACHER) && teacherRepository.findById(user.getUserId()) != null) {
            teacherRepository.delete(user.getUserId());
        }
        else if (user.getRoles().contains(Role.TEACHER) && teacherRepository.findById(user.getUserId()) == null) {
            teacherRepository.save(new Teacher(user, "", Collections.emptySet()));
        }
    }

    public void delete(String id) {
        userRepository.delete(id);
        studentRepository.delete(id);
        teacherRepository.delete(id);
    }
}
