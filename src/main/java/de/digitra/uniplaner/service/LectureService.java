package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.interfaces.ILectureService;
import de.digitra.uniplaner.repository.LectureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LectureService implements ILectureService {

    Logger logger = LoggerFactory.getLogger(LectureService.class);

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public Lecture create(Lecture newLecture) {
        return null;
    }

    @Override
    public Lecture save(Lecture lecture) {
        logger.debug("Request to save Lecture {}", lecture);
        System.out.println("Request to save Lecture"+ lecture);
        return lectureRepository.save(lecture);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Lecture {}", id);
        lectureRepository.deleteById(id);
    }

    @Override
    public List<Lecture> findAll() {
        logger.debug("Request to get all Lecture");
        return lectureRepository.findAll();
    }

    @Override
    public Optional<Lecture> findOne(Long id) {
        logger.debug("Request to find Lecture {}", id);
        return lectureRepository.findById(id);
    }

    @Override
    public Lecture update(Long id, Lecture lecture) {
        return null;
    }
}
