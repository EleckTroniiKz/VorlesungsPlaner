package de.digitra.uniplaner.acceptancetests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.digitra.uniplaner.domain.*;
import de.digitra.uniplaner.repository.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@Transactional
public class UniplanerStepDefsTest extends CucumberSpringContextConfiguration {

    private static final ZonedDateTime DATE_BEFORE_NOW = ZonedDateTime.now().minus(1, DAYS);
    private static final ZonedDateTime DATE_NOW = ZonedDateTime.now();
    private static final ZonedDateTime DATE_AFTER_NOW = ZonedDateTime.now().plus(1, DAYS);
    private final long DEFAULT_SEMESTER_NUMBER = 2;
    private final String DEFAULT_MODULE_NAME ="DEFAULT MODULE NAME" ;
    private final String DEFAULT_STUDY_PROGRAM_NAME ="DEFAULT STUDY PROGRAM NAME" ;


    @Autowired
    private TestRestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(UniplanerStepDefsTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private LectureRepository lectureRepo;
    Lecture lecture = null;
    int oldCount =0;

    @Autowired
    private StudyProgramRepository studyProgramRepo;;
    StudyProgram studyProgram;
    RestClient restClient = null;
    public void before() {
        lectureDateRepo.deleteAll();
        restClient = new RestClient(restTemplate,port);

    }


    @Autowired
    private LectureDateRepository lectureDateRepo;
    LocalDateTime startTimeLectureDate = null;
    LectureDate lectureDate = null;

    List<LectureDate> lectureDates = null;
    @Given("the user saves the lecture date instance in the system")
    public void the_user_saves_the_lecture_date_instance_in_the_system() {
        lectureDate = lectureDateRepo.save(lectureDate);
    }
    @Then("the result list contains one lecture date")
    public void theResultListWillContainOneLectureDate() {
        assertThat(lectureDates.size()).isGreaterThan(0);
    }

    @When("the user requests the list of lecture dates")
    public void theUserCallsTheLectureDates() throws JsonProcessingException {
        lectureDates = restClient.getLectureDates();
        printJsonFromObject(lectureDates);
    }


    @Given("a lecture date in the system")
    public void a_lecture_date_in_the_system() {
        lectureDateRepo.deleteAll();
        newLectureDate();
        lectureDate = lectureDateRepo.save(lectureDate);

    }

    private void newLectureDate() {
        lectureDate = new LectureDate();
        startTimeLectureDate = LocalDateTime.now();
        lectureDate.setStartDate(startTimeLectureDate);
    }


    public static void printJsonFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        mapper.setDateFormat(df);
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        System.out.println(jsonInString);
    }


}
