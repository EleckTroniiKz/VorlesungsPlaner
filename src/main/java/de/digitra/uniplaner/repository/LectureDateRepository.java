package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.LectureDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@SuppressWarnings("unused")
public interface LectureDateRepository extends JpaRepository<LectureDate, Long> {
    List<LectureDate> findByLecturerId(long lecturerId);
    List<LectureDate> findByStartDate(LocalDateTime startDate);

}