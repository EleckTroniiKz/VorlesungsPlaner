package de.digitra.uniplaner.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lecturelecturer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = LectureLecturer.class)
public class LectureLecturer implements Serializable {
    @Id
    private Long id;

    @Column(name = "lecture_id")
    private Long lectureId;


    public void setId(Long id) {
        this.id = id;
    }

    public void setlectureId(Long id) {
        this.lectureId = id;
    }

    public Long getId(){
        return id;
    }

    public Long getLectureId(){
        return lectureId;
    }



}
