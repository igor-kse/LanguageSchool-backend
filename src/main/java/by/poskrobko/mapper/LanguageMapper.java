package by.poskrobko.mapper;

import by.poskrobko.dto.LanguageEntryDTO;
import by.poskrobko.model.Language;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LanguageMapper extends BaseMapper<Language> {

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

    public List<Language> toLanguages(ResultSet resultSet) {
        return mapMultiple(resultSet, this::toLanguage);
    }

    public Language toLanguage(LanguageEntryDTO languageEntryDTO) {
        return new Language(languageEntryDTO.name(), languageEntryDTO.note(), languageEntryDTO.scale());
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
