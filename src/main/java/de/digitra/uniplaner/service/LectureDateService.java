package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.interfaces.ILectureDateService;
import de.digitra.uniplaner.repository.LectureDateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LectureDateService implements ILectureDateService {

    Logger logger = LoggerFactory.getLogger(LectureDateService.class);

    private final LectureDateRepository lecturedateRepository;

    public LectureDateService(LectureDateRepository lecturedateRepository) {
        this.lecturedateRepository = lecturedateRepository;
    }

    @Override
    public LectureDate save(LectureDate lecturedate) {
        logger.debug("Request to save LectureDate {}", lecturedate);
        return lecturedateRepository.save(lecturedate);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete LectureDate {}", id);
        lecturedateRepository.deleteById(id);
    }

    @Override
    public List<LectureDate> findAll() {
        logger.debug("Request to get all LectureDate");
        return lecturedateRepository.findAll();
    }

    @Override
    public Optional<LectureDate> findOne(Long id) {
        logger.debug("Request to find LectureDate {}", id);
        return lecturedateRepository.findById(id);
    }
}
