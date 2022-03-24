package de.digitra.uniplaner.service;


import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.interfaces.ILecturerService;
import de.digitra.uniplaner.repository.LecturerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LecturerService implements ILecturerService {

    Logger logger = LoggerFactory.getLogger(LecturerService.class);

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        logger.debug("Request to save Lecturer {}", lecturer);
        return lecturerRepository.save(lecturer);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Lecturer {}", id);
        lecturerRepository.deleteById(id);
    }

    @Override
    public List<Lecturer> findAll() {
        logger.debug("Request to get all Lecturer");
        return lecturerRepository.findAll();
    }

    @Override
    public Optional<Lecturer> findOne(Long id) {
        logger.debug("Request to find Lecturer {}", id);
        return lecturerRepository.findById(id);
    }

    public List<Lecturer> findByEmail(String email) {
        return lecturerRepository.findByEmail(email);
    }
}
