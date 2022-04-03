package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unused")
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {


    List<Lecturer> findByEmail(String email);

}