package by.poskrobko.repository;

import by.poskrobko.mapper.BaseMapper;
import by.poskrobko.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@SuppressWarnings("SqlSourceToSinkFlow")
public abstract class AbstractBaseDAO<T> {

    protected final SQLExecutor sqlExecutor = new SQLExecutor();

    private final BaseMapper<T> mapper = new BaseMapper<>();

    protected final void doSave(String sql, Settable settable) {
        sqlExecutor.executeVoid(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            settable.set(ps);

            ps.executeUpdate();
        });
    }

    protected void doDeleteByKey(String sql, String key) {
        sqlExecutor.executeVoid(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, key);
            ps.executeUpdate();
        });
    }

    protected void doDeleteBySettable(String sql, Settable settable) {
        sqlExecutor.executeVoid(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            settable.set(ps);
            ps.executeUpdate();
        });
    }

    protected T doFindBy(String sql, Settable settable, SQLMappable<T> extractor) {
        SQLExecutable<T> strategy = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            settable.set(ps);
            ResultSet resultSet = ps.executeQuery();
            return extractor.extract(resultSet);
        };
        return sqlExecutor.execute(strategy);
    }

    protected List<T> doFindAll(String sql, SQLMappable<List<T>> extractor) {
        return sqlExecutor.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            return extractor.extract(resultSet);
        });
    }

    protected List<T> doFindAllBy(String sql, Settable settable, SQLMappable<T> extractor) {
        return sqlExecutor.execute(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            settable.set(ps);
            ResultSet resultSet = ps.executeQuery();
            return mapper.mapMultiple(resultSet, extractor);
        });
    }

    protected void doUpdate(String sql, Settable settable) {
        SQLVoidExecutable executable = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            settable.set(ps);

            ps.executeUpdate();
        };
        sqlExecutor.executeVoid(executable);
    }
}
