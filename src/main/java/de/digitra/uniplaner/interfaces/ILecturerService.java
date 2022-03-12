package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecturer;

import java.util.List;
import java.util.Optional;

public interface ILecturerService {

    public Lecturer save(Lecturer lecturer);

    public void delete(Long id);

    public List<Lecturer> findAll();

    public Optional<Lecturer> findOne(Long id);
}
