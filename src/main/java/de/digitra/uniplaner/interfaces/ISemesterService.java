package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Semester;

import java.util.List;
import java.util.Optional;

public interface ISemesterService {

    public Semester save(Semester semester);

    public void delete(Long id);

    public List<Semester> findAll();

    public Optional<Semester> findOne(Long id);
}
