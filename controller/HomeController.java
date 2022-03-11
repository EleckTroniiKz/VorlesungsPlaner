package de.digitra.uniplaner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "home";
    }
    @GetMapping("/create-lecture")
    public String createLecture() {return "create-lecture";}
    @GetMapping("/lecture-list")
    public String lecturett(){
        return "lecture-list";
    }
    @GetMapping("/lecturedate-list")
    public String lecturedatett(){
        return "lecturedate-list";
    }
    @GetMapping("/lecturer-list")
    public String lecturertt(){
        return "lecturer-list";
    }
    @GetMapping("/role-list")
    public String rolett(){
        return "role-list";
    }
    @GetMapping("/semester-list")
    public String semestertt(){
        return "semester-list";
    }
    @GetMapping("/studyclass-list")
    public String studyclasstt(){
        return "studyclass-list";
    }
    @GetMapping("/studyprogram-list")
    public String studyprogramtt(){
        return "studyprogram-list";
    }
    @GetMapping("/uniuser-list")
    public String uniuser(){
        return "uniuser-list";
    }
    @GetMapping("/user-list")
    public String user() {return "user-list";}
    @GetMapping("/webapplication-list")
    public String webapplication() { return "webapplication-list";}
    @GetMapping("/about-us")
    public String aboutUs() { return "about-us";}
    @GetMapping("/impressum")
    public String impressum() { return "impressum";}
    @GetMapping("/datenschutz")
    public String privacy() { return "datenschutz";}
    @GetMapping("/contact")
    public String contact() { return "contact";}

}
