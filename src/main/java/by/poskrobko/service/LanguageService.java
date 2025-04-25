package by.poskrobko.service;

import by.poskrobko.dto.LanguageEntryDTO;
import by.poskrobko.mapper.LanguageMapper;
import by.poskrobko.model.Language;
import by.poskrobko.model.LanguageScale;
import by.poskrobko.repository.LanguageRepository;
import by.poskrobko.repository.LanguageScalesRepository;
import by.poskrobko.repository.impl.LanguageRepositoryImpl;
import by.poskrobko.repository.impl.LanguageScalesRepositoryImpl;
import exception.ExistingEntityException;
import exception.NotExistingEntityException;

import java.util.List;

public class LanguageService {

    private final LanguageRepository languageRepository = new LanguageRepositoryImpl();
    private final LanguageScalesRepository languageScalesRepository = new LanguageScalesRepositoryImpl();
    private final LanguageMapper languageMapper = new LanguageMapper();

    public LanguageEntryDTO save(LanguageEntryDTO dto) {
        if (dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language name cannot be empty");
        }

        Language language = languageRepository.findByName(dto.name());
        if (language != null) {
            throw new ExistingEntityException("Language " + dto.name() + " already exists");
        }

        LanguageScale scale = languageScalesRepository.findByName(dto.scale());
        if (scale == null) {
            throw new NotExistingEntityException("Scale " + dto.scale() + " does not exist");
        }

        language = languageMapper.toLanguage(dto);
        languageRepository.save(language);
        return languageMapper.toLanguageEntryDTO(language);
    }

    public LanguageEntryDTO findByName(String name) {
        Language language = languageRepository.findByName(name);
        if (language == null) {
            throw new NotExistingEntityException("Language " + name + " does not exist");
        }
        return languageMapper.toLanguageEntryDTO(language);
    }

    public void update(LanguageEntryDTO dto) {
        if (dto.name() == null || dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language name cannot be empty");
        }
        Language language = languageMapper.toLanguage(dto);
        languageRepository.update(language);
    }

    public List<LanguageEntryDTO> findAll() {
        List<Language> languages = languageRepository.findAll();
        return languageMapper.toLanguageEntryDTO(languages);
    }

    public void delete(String name) {
        languageRepository.delete(name);
    }
}
