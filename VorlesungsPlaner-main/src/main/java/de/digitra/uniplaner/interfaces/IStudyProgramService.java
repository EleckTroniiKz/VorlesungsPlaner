package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyProgram;

import java.util.List;
import java.util.Optional;

public interface IStudyProgramService {

    public StudyProgram save(StudyProgram studyprogram);

    public void delete(Long id);

    public List<StudyProgram> findAll();

    public Optional<StudyProgram> findOne(Long id);
}
