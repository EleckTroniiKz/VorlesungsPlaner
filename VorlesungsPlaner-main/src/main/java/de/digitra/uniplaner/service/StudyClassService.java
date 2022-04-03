package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.interfaces.IStudyClassService;
import de.digitra.uniplaner.repository.StudyClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudyClassService implements IStudyClassService {

    Logger logger = LoggerFactory.getLogger(StudyClassService.class);

    private final StudyClassRepository studyclassRepository;

    public StudyClassService(StudyClassRepository studyclassRepository) {
        this.studyclassRepository = studyclassRepository;
    }

    @Override
    public StudyClass save(StudyClass studyclass) {
        logger.debug("Request to save StudyClass {}", studyclass);
        return studyclassRepository.save(studyclass);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete StudyClass {}", id);
        studyclassRepository.deleteById(id);
    }

    @Override
    public List<StudyClass> findAll() {
        logger.debug("Request to get all StudyClass");
        return studyclassRepository.findAll();
    }

    @Override
    public Optional<StudyClass> findOne(Long id) {
        logger.debug("Request to find StudyClass {}", id);
        return studyclassRepository.findById(id);
    }
}
