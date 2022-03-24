package de.digitra.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Die Klasse stellt einen Vorlesungstermin dar.
 */
@Entity
@Table(name = "lecture_date")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",scope = LectureDate.class)
public class LectureDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToOne
    private Lecture lecture;

    @ManyToOne

    private Semester semester;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

        public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Lecture getLecture() {
        return lecture;
    }


    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LectureDate)) {
            return false;
        }
        return id != null && id.equals(((LectureDate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "LectureDate{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
