package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.domain.LectureLecturer;
import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.interfaces.ILectureService;
import de.digitra.uniplaner.repository.LectureLecturerRepository;
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

    private final LectureLecturerRepository lectureLecturerRepository;

    public LectureService(LectureRepository lectureRepository, LectureLecturerRepository lectureLecturerRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureLecturerRepository = lectureLecturerRepository;
    }

    @Override
    public Lecture create(Lecture newLecture) {
        return null;
    }

    @Override
    public Lecture save(Lecture lecture) {
        System.out.println(lecture);
        logger.debug("Request to save Lecture {}", lecture);
        return lectureRepository.save(lecture);
    }

    public LectureLecturer createRef(Lecture lecture, Lecturer lecturer){
        LectureLecturer lecturerLecturer = new LectureLecturer();
        lecturerLecturer.setId(lecture.getId());
        lecturerLecturer.setlectureId(lecturer.getId());
        return lectureLecturerRepository.save(lecturerLecturer);
    }

    @Override
    public void delete(Long id) {
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
        return lectureRepository.save(lecture);
    }
}
