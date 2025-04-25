package by.poskrobko.mapper;

import by.poskrobko.dto.LanguageScaleDTO;
import by.poskrobko.dto.LanguageScaleLevelDTO;
import by.poskrobko.model.LanguageScale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LanguageScalesMapper extends BaseMapper<LanguageScale> {

    private Map<String, LanguageScale> mapMultiple(ResultSet resultSet) {
        Map<String, LanguageScale> scales = new HashMap<>();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("language_scale_name");
                if (name == null) {
                    return null;
                }
                String description = resultSet.getString("language_scale_description");
                String scaleLevelId = resultSet.getString("scale_level_id");
                String scaleLevelName = resultSet.getString("scale_level_name");

                LanguageScale scale = scales.get(name);
                LanguageScale.Level level = new LanguageScale.Level(scaleLevelId, scaleLevelName);
                if (scale == null) {
                    Set<LanguageScale.Level> levels = new HashSet<>();
                    levels.add(level);
                    scales.put(name, new LanguageScale(name, description, levels));
                } else {
                    scale.getLevels().add(level);
                }
            }
            return scales;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LanguageScale toLanguageScale(ResultSet resultSet) {
        Map<String, LanguageScale> scales = mapMultiple(resultSet);
        if (scales == null) {
            return null;
        }
        if (scales.size() > 1) {
            throw new RuntimeException("Mapping error. More than one user was found.");
        }
        return !scales.isEmpty()
                ? scales.values().iterator().next()
                : null;
    }

    public List<LanguageScale> toLanguageScales(ResultSet resultSet) {
        var scales = mapMultiple(resultSet);
        if (scales == null) {
            return null;
        }
        return new ArrayList<>(scales.values());
    }

    public LanguageScale toLanguageScale(LanguageScaleDTO dto) {
        var levelsDTO = dto.levels();
        var levels = new HashSet<LanguageScale.Level>();
        if (levelsDTO != null) {
            levelsDTO.forEach(levelDTO -> {
                levels.add(new LanguageScale.Level(levelDTO.id(), levelDTO.name()));
            });
        }
        return new LanguageScale(dto.name(), dto.description(), levels);
    }

    public LanguageScaleDTO toLanguageScaleDTO(LanguageScale scale) {
        var levels = scale.getLevels();
        var levelsDTO = new HashSet<LanguageScaleLevelDTO>();
        if (levels != null) {
            for (LanguageScale.Level level : levels) {
                levelsDTO.add(new LanguageScaleLevelDTO(level.getId(), level.getName()));
            }
        }
        return new LanguageScaleDTO(scale.getName(), scale.getDescription(), levelsDTO);
    }

    public List<LanguageScaleDTO> toLanguageScaleDTO(List<LanguageScale> scales) {
        var dtoList = new ArrayList<LanguageScaleDTO>();
        for (LanguageScale scale : scales) {
            dtoList.add(toLanguageScaleDTO(scale));
        }
        return dtoList;
    }
}
