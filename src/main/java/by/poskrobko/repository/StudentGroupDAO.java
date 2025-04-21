package by.poskrobko.repository;

import by.poskrobko.repository.impl.StudentGroupDAOImpl;

import java.util.List;

public interface StudentGroupDAO {
    void save(StudentGroupDAOImpl.StudentGroup studentGroup);

    List<StudentGroupDAOImpl.StudentGroup> findAllByStudentId(String userId);

    List<StudentGroupDAOImpl.StudentGroup> findAllByGroupId(String groupId);

    void deleteByStudentId(String id);

    void deleteByGroupId(String id);

    void deleteByStudentAndGroupId(String userId, String groupId);
}
