package de.digitra.uniplaner.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse stellt einen Studiengang (z.B. Informatik, Wirtschaftsinformatik) in der Hochschule dar.
 */
@Entity
@Table(name = "study_program")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",scope=StudyProgram.class)
public class StudyProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;


    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudyClass> studyClasses = new HashSet<>();

    //@JsonIgnoreProperties(value = "studyProgram")

    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY,
     cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lecture> lectures = new HashSet<>();


    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lecturer> lecturers = new HashSet<>();


    public Set<Lecturer> getLecturers() {
        return lecturers;
    }


    public StudyProgram addLecturer(Lecturer lecturer) {
        this.lecturers.add(lecturer);
        lecturer.setStudyProgram(this);
        return this;
    }

    public StudyProgram removeLecturer(Lecturer lecturer) {
        this.lecturers.remove(lecturer);
        lecturer.setStudyProgram(null);
        return this;
    }







    public Set<Lecture> getLectures() {
        return lectures;
    }


    public StudyProgram addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        lecture.setStudyProgramm(this);
        return this;
    }

    public StudyProgram removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
        lecture.setStudyProgramm(null);
        return this;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    //end übernommen


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }


    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<StudyClass> getStudyClasses() {
        return studyClasses;
    }


    public StudyProgram addStudyClass(StudyClass studyClass) {
        this.studyClasses.add(studyClass);
        studyClass.setStudyProgram(this);
        return this;
    }

    public StudyProgram removeStudyClass(StudyClass studyClass) {
        this.studyClasses.remove(studyClass);
        studyClass.setStudyProgram(null);
        return this;
    }

    public void setStudyClasses(Set<StudyClass> studyClass) {
        this.studyClasses = studyClass;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyProgram)) {
            return false;
        }
        return id != null && id.equals(((StudyProgram) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "StudyProgram{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            "}";
    }
}
