package by.poskrobko.service;

import by.poskrobko.dto.GroupDTO;
import by.poskrobko.mapper.GroupMapper;
import by.poskrobko.model.Group;
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
import java.util.UUID;

public class GroupService {

    private final GroupRepository groupRepository = new GroupRepositoryImpl();
    private final StudentGroupDAO studentGroupDAO = new StudentGroupDAOImpl();
    private final GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();

    private final GroupMapper groupMapper = new GroupMapper();

    public GroupDTO create(GroupDTO group) {
        String groupId = UUID.randomUUID().toString();
        Objects.requireNonNull(group.name());
        groupRepository.save(groupId, group.name(), group.teacher().id(), group.language().name(), group.levelDTO().id());
        return group;
    }

    public GroupDTO findById(String id) {
        Util.assureStringHasLength(id, "group id");
        return groupMapper.toGroupDTO(groupRepository.findById(id));
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

    public List<GroupDTO> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.toGroupDTOs(groups);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void update(GroupDTO group) {
        Group existingGroup = groupRepository.findById(group.id());
        if (existingGroup == null) {
            throw new NotExistingEntityException("Group " + group.id() + " not found");
        }
        groupRepository.update(group.id(), group.name(), group.teacher().id(), group.language().name(),
                group.levelDTO().id());
    }

    public void updateStudents(String groupId, List<String> studentsId) {
        Objects.requireNonNull(groupId, "Group id must not be null");
        Objects.requireNonNull(studentsId, "Students id must not be null");
        groupRepository.deleteStudents(groupId);
        groupRepository.saveStudents(groupId, studentsId);
    }

    public void delete(String id) {
        Group group = groupRepository.findById(id);
        if (group == null) {
            throw new NotExistingEntityException("Group " + id + " not found");
        }
        groupRepository.delete(id);
    }

//    public void assignTeacher(String teacherId, String groupId) {
//        Group group = groupRepository.findById(groupId);
//        Objects.requireNonNull(group);
//
//        Teacher teacher = this.teacherRepository.findById(teacherId);
//        Objects.requireNonNull(teacher);
//
//        group.setTeacher(teacher);
//        groupRepository.update(group);
//    }
}
