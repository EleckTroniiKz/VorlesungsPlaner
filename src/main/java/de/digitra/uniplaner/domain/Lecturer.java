package de.digitra.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse dient dazu, Dozenten darzustellen
 */
@Entity
@Table(name = "lecturer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope = Lecturer.class)
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "lecturer")
    private Set<LectureDate> lectureDates = new HashSet<>();


    @ManyToMany(mappedBy = "lecturers")
    //@JsonIgnore
    private Set<Lecture> lectures = new HashSet<>();


    @ManyToOne
    private StudyProgram studyProgram;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LectureDate> getLectureDates() {
        return lectureDates;
    }


    public Lecturer addLectureDate(LectureDate lectureDate) {
        this.lectureDates.add(lectureDate);
        lectureDate.setLecturer(this);
        return this;
    }

    public Lecturer removeLectureDate(LectureDate lectureDate) {
        this.lectureDates.remove(lectureDate);
        lectureDate.setLecturer(null);
        return this;
    }

    public void setLectureDates(Set<LectureDate> lectureDates) {
        this.lectureDates = lectureDates;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }


    public Lecturer addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        lecture.getLecturers().add(this);
        return this;
    }

    public Lecturer removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
        lecture.getLecturers().remove(this);
        return this;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecturer)) {
            return false;
        }
        return id != null && id.equals(((Lecturer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Lecturer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }


}
