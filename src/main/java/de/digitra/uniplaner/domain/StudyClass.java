package de.digitra.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse stellt einen Studienjahrgang (z.B. WWI2020D, INF2021A) in der Hochschule dar.
 */
@Entity
@Table(name = "study_class")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=StudyClass.class)
public class StudyClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;


    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;


    @OneToMany(mappedBy = "studyClass", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Semester> semesters = new HashSet<>();

    @ManyToOne
    //@JsonIgnoreProperties(value = "studyClasses", allowSetters = true)

    private StudyProgram studyProgram;


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

    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public Set<Semester> getSemesters() {
        return semesters;
    }


    public StudyClass addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.setStudyClass(this);
        return this;
    }

    public StudyClass removeSemester(Semester semester) {
        this.semesters.remove(semester);
        semester.setStudyClass(null);
        return this;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyClass)) {
            return false;
        }
        return id != null && id.equals(((StudyClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }




    public void setStartingYear(long l) {
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }
    @Override
    public String toString() {
        return "StudyClass{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                "}";
    }

}
