package de.digitra.uniplaner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value={"/home", "/", ""})
    public String home(){
        return "home";
    }
    @GetMapping("/lecture-calendar")
    public String lectureCalendar() {
        return "lecture-calendar";
    }

    @GetMapping("/about-us")
    public String aboutUs() { return "about-us";}

    @GetMapping("/impressum")
    public String impressum() { return "impressum";}

    @GetMapping("/datenschutz")
    public String privacy() { return "datenschutz";}

    @GetMapping("/contact")
    public String contact() { return "contact";}


}
