package by.poskrobko.mapper;

import by.poskrobko.dto.LanguageDTO;
import by.poskrobko.dto.LanguageEntryDTO;
import by.poskrobko.dto.ScaleDTO;
import by.poskrobko.model.Language;
import by.poskrobko.model.Scale;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageMapper extends BaseMapper<Language> {

    private final ScaleMapper scaleMapper = new ScaleMapper();

    public Language toLanguage(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String name = resultSet.getString("language_name");
            String note = resultSet.getString("note");
            String scaleName = resultSet.getString("language_scale_name");
            if (name == null) {
                return null;
            }
            return new Language(name, note, scaleName);
        });
    }

    public Language toLanguageWithoutNote(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String name = resultSet.getString("language_name");
            String scaleName = resultSet.getString("language_scale_name");
            if (name == null) {
                return null;
            }
            return new Language(name, "", scaleName);
        });
    }

    public List<Language> toLanguages(ResultSet resultSet) {
        return mapMultiple(resultSet, this::toLanguage);
    }


    public Language toLanguage(LanguageEntryDTO languageDTO) {
        return new Language(languageDTO.name(), languageDTO.note(), languageDTO.scale());
    }


    public LanguageDTO toLanguageDTO(Language language, ScaleDTO scaleDTO) {
        return new LanguageDTO(language.getName(), scaleDTO, language.getNote());
    }

    public List<LanguageDTO> toLanguageDTOs(List<Scale> scales, List<Language> languages) {
        var scaleDTOs = scales.stream()
                .map(scaleMapper::toScaleDTO)
                .collect(Collectors.toMap(ScaleDTO::name, scaleDTO -> scaleDTO));
        var languageDTOs = new ArrayList<LanguageDTO>();
        languages.forEach(language -> {
            var languageDTO = toLanguageDTO(language, scaleDTOs.get(language.getScaleName()));
            languageDTOs.add(languageDTO);
        });
        return languageDTOs;
    }


    public LanguageEntryDTO toLanguageEntryDTO(Language language, String scaleName) {
        return new LanguageEntryDTO(language.getName(), scaleName, language.getNote());
    }

    public LanguageEntryDTO toLanguageEntryDTO(Language language) {
        return new LanguageEntryDTO(language.getName(), language.getScaleName(), language.getNote());
    }

    public List<LanguageEntryDTO> toLanguageEntryDTO(List<Language> languages) {
        var languagesDTO = new ArrayList<LanguageEntryDTO>();
        for (Language language : languages) {
            languagesDTO.add(toLanguageEntryDTO(language));
        }
        return languagesDTO;
    }
}
