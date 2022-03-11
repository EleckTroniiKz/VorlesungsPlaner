package de.digitra.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse stellt eine Vorlesung dar.
 */
@Entity
@Table(name = "lecture")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id", scope = Lecture.class)
public class Lecture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lecture_name")
    private String lectureName;

    @Column(name = "modul_name")
    private String modulName;

    @Column(name = "duration")
    private Long duration;




    @OneToMany(mappedBy = "lecture")

    private Set<LectureDate> lectureDates = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinTable(name = "lecture_lecturer",
               joinColumns = @JoinColumn(name = "lecture_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"))
    private Set<Lecturer> lecturers = new HashSet<>();


    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }



    @ManyToOne
    private StudyProgram studyProgram;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }


    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getModulName() {
        return modulName;
    }


    public void setModulName(String modulName) {
        this.modulName = modulName;
    }

    public Long getDuration() {
        return duration;
    }


    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Set<LectureDate> getLectureDates() {
        return lectureDates;
    }


    public Lecture addLectureDate(LectureDate lectureDate) {
        this.lectureDates.add(lectureDate);
        lectureDate.setLecture(this);
        return this;
    }

    public Lecture removeLectureDate(LectureDate lectureDate) {
        this.lectureDates.remove(lectureDate);
        lectureDate.setLecture(null);
        return this;
    }

    public void setLectureDates(Set<LectureDate> lectureDates) {
        this.lectureDates = lectureDates;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }


    public Lecture addLecturer(Lecturer lecturer) {
        this.lecturers.add(lecturer);
        lecturer.getLectures().add(this);
        return this;
    }

    public Lecture removeLecturer(Lecturer lecturer) {
        this.lecturers.remove(lecturer);
        lecturer.getLectures().remove(this);
        return this;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecture)) {
            return false;
        }
        return id != null && id.equals(((Lecture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Lecture{" +
            "id=" + getId() +
            ", lectureName='" + getLectureName() + "'" +
            ", modulName='" + getModulName() + "'" +
            ", duration=" + getDuration() +
            "}";
    }

    public StudyProgram getStudyProgramm() {
        return studyProgram;
    }

    public void setStudyProgramm(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }


}
