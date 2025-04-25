package by.poskrobko.service;

import by.poskrobko.dto.LanguageScaleDTO;
import by.poskrobko.mapper.LanguageScalesMapper;
import by.poskrobko.model.LanguageScale;
import by.poskrobko.repository.LanguageScalesRepository;
import by.poskrobko.repository.impl.LanguageScalesRepositoryImpl;
import exception.ExistingEntityException;
import exception.NotExistingEntityException;

import java.util.List;

public class LanguageScaleService {

    private final LanguageScalesRepository languageScalesRepository = new LanguageScalesRepositoryImpl();
    private final LanguageScalesMapper languageScalesMapper = new LanguageScalesMapper();

    public LanguageScaleDTO save(LanguageScaleDTO dto) {
        if (dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language scale name cannot be empty");
        }

        LanguageScale scale = languageScalesRepository.findByName(dto.name());
        if (scale != null) {
            throw new ExistingEntityException("Language scale" + dto.name() + " already exists");
        }
        scale = languageScalesMapper.toLanguageScale(dto);
        languageScalesRepository.save(scale);
        return languageScalesMapper.toLanguageScaleDTO(scale);
    }

    public LanguageScaleDTO findByName(String name) {
        LanguageScale scale = languageScalesRepository.findByName(name);
        if (scale == null) {
            throw new NotExistingEntityException("Language scale" + name + " does not exist");
        }
        return languageScalesMapper.toLanguageScaleDTO(scale);
    }

    public void update(LanguageScaleDTO scale) {
        if (scale.name() == null || scale.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language scale name cannot be empty");
        }
        if (scale.levels() == null || scale.levels().isEmpty()) {
            throw new IllegalArgumentException("Language scale levels cannot be empty");
        }
        languageScalesRepository.update(languageScalesMapper.toLanguageScale(scale));
    }

    public List<LanguageScaleDTO> findAll() {
        return languageScalesMapper.toLanguageScaleDTO(languageScalesRepository.getAll());
    }

    public void delete(String name) {
        languageScalesRepository.delete(name);
    }
}
