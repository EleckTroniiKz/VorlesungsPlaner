package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.interfaces.IStudyProgramService;
import de.digitra.uniplaner.repository.StudyProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudyProgramService implements IStudyProgramService {

    Logger logger = LoggerFactory.getLogger(StudyProgramService.class);

    private final StudyProgramRepository studyprogramRepository;

    public StudyProgramService(StudyProgramRepository studyprogramRepository) {
        this.studyprogramRepository = studyprogramRepository;
    }

    @Override
    public StudyProgram save(StudyProgram studyprogram) {
        logger.debug("Request to save StudyProgram {}", studyprogram);
        return studyprogramRepository.save(studyprogram);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete StudyProgram {}", id);
        studyprogramRepository.deleteById(id);
    }

    @Override
    public List<StudyProgram> findAll() {
        logger.debug("Request to get all StudyProgram");
        return studyprogramRepository.findAll();
    }

    @Override
    public Optional<StudyProgram> findOne(Long id) {
        logger.debug("Request to find StudyProgram {}", id);
        return studyprogramRepository.findById(id);
    }
}
