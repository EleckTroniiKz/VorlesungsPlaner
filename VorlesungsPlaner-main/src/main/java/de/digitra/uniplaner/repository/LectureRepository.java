package de.digitra.uniplaner.repository;

import de.digitra.uniplaner.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    //List<Lecturer> findByLectureId(long lectureId);
    //@Query("SELECT c FROM lecture c JOIN FETCH c.lecturer WHERE c.id = :lectureId")
    //List<Lecture> fetchByLectureId(@Param("lectureId") long lectureId);



}