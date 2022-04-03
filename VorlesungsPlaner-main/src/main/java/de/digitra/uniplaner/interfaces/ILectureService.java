package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface ILectureService {

    public Lecture save(Lecture lecture);

    public void delete(Long id);

    public List<Lecture> findAll();

    public Optional<Lecture> findOne(Long id);
}
