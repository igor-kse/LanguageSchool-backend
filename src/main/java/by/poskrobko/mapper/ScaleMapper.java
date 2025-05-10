package by.poskrobko.mapper;

import by.poskrobko.dto.ScaleDTO;
import by.poskrobko.dto.ScaleLevelDTO;
import by.poskrobko.model.Scale;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ScaleMapper extends BaseMapper<Scale> {

    private Map<String, Scale> mapMultiple(ResultSet resultSet) {
        Map<String, Scale> scales = new HashMap<>();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("language_scale_name");
                if (name == null) {
                    return null;
                }
                String description = resultSet.getString("language_scale_description");
                String scaleLevelId = resultSet.getString("scale_level_id");
                String scaleLevelName = resultSet.getString("scale_level_name");

                Scale scale = scales.get(name);
                Scale.Level level = new Scale.Level(scaleLevelId, scaleLevelName);
                if (scale == null) {
                    Set<Scale.Level> levels = new HashSet<>();
                    levels.add(level);
                    scales.put(name, new Scale(name, description, levels));
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

    public Scale toScale(ResultSet resultSet) {
        Map<String, Scale> scales = mapMultiple(resultSet);
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

    public List<Scale> toScales(ResultSet resultSet) {
        var scales = mapMultiple(resultSet);
        if (scales == null) {
            return null;
        }
        return new ArrayList<>(scales.values());
    }

    public Scale toScale(ScaleDTO dto) {
        var levelsDTO = dto.levels();
        var levels = new HashSet<Scale.Level>();
        if (levelsDTO != null) {
            levelsDTO.forEach(levelDTO -> {
                levels.add(new Scale.Level(levelDTO.id(), levelDTO.name()));
            });
        }
        return new Scale(dto.name(), dto.description(), levels);
    }

    public ScaleDTO toScaleDTO(Scale scale) {
        var levels = scale.getLevels();
        var levelsDTO = new HashSet<ScaleLevelDTO>();
        if (levels != null) {
            for (Scale.Level level : levels) {
                levelsDTO.add(new ScaleLevelDTO(level.getId(), level.getName()));
            }
        }
        return new ScaleDTO(scale.getName(), scale.getDescription(), levelsDTO);
    }

    public List<ScaleDTO> toScaleDTO(List<Scale> scales) {
        var dtoList = new ArrayList<ScaleDTO>();
        for (Scale scale : scales) {
            dtoList.add(toScaleDTO(scale));
        }
        return dtoList;
    }
}
