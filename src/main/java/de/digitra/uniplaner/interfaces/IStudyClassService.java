package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyClass;

import java.util.List;
import java.util.Optional;

public interface IStudyClassService {

    public StudyClass save(StudyClass studyclass);

    public void delete(Long id);

    public List<StudyClass> findAll();

    public Optional<StudyClass> findOne(Long id);
}
