package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}