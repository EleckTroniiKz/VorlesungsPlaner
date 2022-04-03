package de.digitra.uniplaner.controller;

import antlr.build.Tool;
import de.digitra.uniplaner.domain.*;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureDateController;
import de.digitra.uniplaner.service.LectureDateService;
import de.digitra.uniplaner.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lecture-calendar")
public class CalendarController {

    LecturerService lecturerService;
    LectureDateService lectureDateService;

    CalendarController(LecturerService _lecturerService, LectureDateService _lectureDateService) {

        lecturerService = _lecturerService;
        lectureDateService = _lectureDateService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("calendar", new Calendar());
        LectureDate temp = new LectureDate();
        Lecture dummyLecture = new Lecture();
        Lecturer dummyLecturer = new Lecturer();
        Semester dummySemester = new Semester();
        temp.setLecture(dummyLecture);
        temp.setLecturer(dummyLecturer);
        temp.setSemester(dummySemester);
        model.addAttribute("lecturedates", temp);
        return "lecture-calendar";
    }

    @GetMapping("/{id}")
    public String findLectureByLecturer(@PathVariable Long id, Model model) {
        model.addAttribute("lecturedates", lectureDateService.findByLecturerId(id));
        return "lecture-calendar";
    }

    @ModelAttribute("lecturers")
    public List<Lecturer> getLecturers() {
        List<Lecturer> list = lecturerService.findAll();
        return list;
    }

    @GetMapping("/create")
    public String createLectureDate(Model model) {
        model.addAttribute("lecturers", lecturerService.findAll());
        return "lecture-calendar";
    }

}
