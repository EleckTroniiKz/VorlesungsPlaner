package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ISemesterController;
import de.digitra.uniplaner.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/semesters")
public class SemesterController implements ISemesterController{
    @Autowired
    private SemesterService semesterService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("semester", semesterService.findAll());
        return "semester-list";
    }

    @GetMapping("/create")
    public String createSemester(Model model) {
        model.addAttribute("semester", new Semester());
        return "create-semester";
    }

    @PostMapping
    public String createSemester(@Valid Semester semester, Errors errors) {
        if(errors.hasErrors()){
            return "create-semester";
        }
        else{
            semesterService.save(semester);
            return "redirect:/semester";
        }
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<Semester> semesterToEdit = semesterService.findOne(id);
        model.addAttribute("semester", semesterToEdit);
        return "update-semester";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid Semester semester, Errors errors) {
        if(errors.hasErrors()){
            return "update-semester";
        }
        else{
            semesterService.delete(id);
            semesterService.save(semester);
            return "redirect:/semester";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        semesterService.delete(id);
        return "redirect:/semester";
    }

    @GetMapping("/search")
    public String searchSemester(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<Semester> semester = semesterService.findAll();
        model.addAttribute("semester", semester);
        return "redirect:/semester";
    }
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/post", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Semester> createSemester(Semester semester) throws BadRequestException, URISyntaxException {
        Semester sem = null;
        if(semester.getId() == null){
            try {
                sem = semesterService.save(semester);
            }
            catch (Error e){
                throw new BadRequestException("Couldnt save semester!");
            }
            return ResponseEntity.ok(sem);
        }
        throw new BadRequestException("Couldn't save semester");
    }

    @RequestMapping(value="/put", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) throws BadRequestException {
        Semester savedSemester = null;
        if(semester.getId() != null){
            try {
                semesterService.delete(semester.getId());
                savedSemester = semesterService.save(semester);
            } catch( Error e) {
                throw new BadRequestException("Semester couldn't be saved");
            }
            return ResponseEntity.ok(savedSemester);
        }
        return new ResponseEntity<Semester>(HttpStatus.OK);

    }

    @RequestMapping(value="/update", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Semester> updateSemester(@PathVariable(value = "id") Long id, @Valid @RequestBody Semester semesterDetails) throws ResourceNotFoundException{
        Semester sem = null;
        if(semesterService.findOne(id).isPresent()) {
            sem =  semesterDetails;
            semesterService.delete(id);
            return ResponseEntity.ok(semesterService.save(semesterDetails));
        }
        else{
            throw new ResourceNotFoundException("Semester not found!");
        }
    }

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Semester>> getAllsemesters(){
        List<Semester> sem = semesterService.findAll();
        return ResponseEntity.ok(sem);
    }

    @RequestMapping(value="/getSemester", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Semester> getSemester(@PathVariable Long id) throws ResourceNotFoundException {
        Semester sem = null;
        if(semesterService.findOne(id).isPresent()){
            sem = semesterService.findOne(id).get();
        }
        else {
            throw new ResourceNotFoundException("Semester not found");
        }
        return ResponseEntity.ok(sem);
    }

    @RequestMapping(value="/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        semesterService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
