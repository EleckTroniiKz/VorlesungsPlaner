package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.StudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface StudyClassRepository extends JpaRepository<StudyClass, Long> {
    Optional<StudyClass> findByName(String name);
}