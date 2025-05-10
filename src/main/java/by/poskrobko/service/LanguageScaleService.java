package by.poskrobko.service;

import by.poskrobko.dto.ScaleDTO;
import by.poskrobko.mapper.ScaleMapper;
import by.poskrobko.model.Scale;
import by.poskrobko.repository.LanguageScalesRepository;
import by.poskrobko.repository.impl.LanguageScalesRepositoryImpl;
import exception.ExistingEntityException;
import exception.NotExistingEntityException;

import java.util.List;

public class LanguageScaleService {

    private final LanguageScalesRepository languageScalesRepository = new LanguageScalesRepositoryImpl();
    private final ScaleMapper scaleMapper = new ScaleMapper();

    public ScaleDTO save(ScaleDTO dto) {
        if (dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language scale name cannot be empty");
        }

        Scale scale = languageScalesRepository.findByName(dto.name());
        if (scale != null) {
            throw new ExistingEntityException("Language scale" + dto.name() + " already exists");
        }
        scale = scaleMapper.toScale(dto);
        languageScalesRepository.save(scale);
        return scaleMapper.toScaleDTO(scale);
    }

    public ScaleDTO findByName(String name) {
        Scale scale = languageScalesRepository.findByName(name);
        if (scale == null) {
            throw new NotExistingEntityException("Language scale" + name + " does not exist");
        }
        return scaleMapper.toScaleDTO(scale);
    }

    public void update(String oldName, ScaleDTO scaleDTO) {
        if (scaleDTO.name() == null || scaleDTO.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Language scale name cannot be empty");
        }
        if (scaleDTO.levels() == null || scaleDTO.levels().isEmpty()) {
            throw new IllegalArgumentException("Language scale levels cannot be empty");
        }
        Scale scale = scaleMapper.toScale(scaleDTO);
        languageScalesRepository.update(oldName, scale);
    }

    public List<ScaleDTO> findAll() {
        return scaleMapper.toScaleDTO(languageScalesRepository.findAll());
    }

    public void delete(String name) {
        languageScalesRepository.delete(name);
    }
}
