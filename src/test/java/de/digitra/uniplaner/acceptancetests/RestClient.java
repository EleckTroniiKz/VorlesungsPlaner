package de.digitra.uniplaner.acceptancetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.domain.Lecturer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class RestClient {


    private final String SERVER_URL = "http://localhost";
    private final String LECTURE_DATE_RESOURCE = "lecturedates";
    private static final String LECTURER_RESOURCE = "lecturers";;

    private TestRestTemplate restTemplate;

    private int port;

    public RestClient(TestRestTemplate restTemplate, int port){
        this.restTemplate = restTemplate;
        this.port = port;
    }
    public String lectureDateEndpoint() {
        return SERVER_URL + ":" + port + "/"+LECTURE_DATE_RESOURCE;
    }

    private String getResourceEndpointForId(String resource, Long id) {
        String entityUrl = "";
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            entityUrl = lectureDateEndpoint()+"/"+id;
        }
        return entityUrl;
    }
    private String getResourceEndpoint(String resource) {
        String entityUrl = "";
        if (resource.equals(LECTURE_DATE_RESOURCE)) {
            return SERVER_URL + ":" + port + "/"+LECTURE_DATE_RESOURCE;
        }
        if (resource.equals(LECTURER_RESOURCE)) {
            return SERVER_URL + ":" + port + "/"+LECTURER_RESOURCE;
        }
        return entityUrl;
    }

    public List<LectureDate> getLectureDates(){
        ResponseEntity<LectureDate[]> lectureDates = restTemplate.getForEntity(lectureDateEndpoint() , LectureDate[].class);
        return Arrays.stream(lectureDates.getBody()).collect(Collectors.toList());
    }
    public LectureDate createLectureDate(LectureDate lectureDate) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        ResponseEntity<LectureDate> response = restTemplate.exchange(lectureDateEndpoint(), HttpMethod.POST, requestEntity, LectureDate.class);
        LectureDate result = response.getBody();
        return result;

    }

    public ResponseEntity<LectureDate> getLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.getForEntity(uri, LectureDate.class);
        return response;
    }

    public void deleteLectureDateById(Long id) {
        String uri = getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        restTemplate.delete(uri);
    }

    public ResponseEntity<LectureDate> updateLectureDate(Long id, LectureDate lectureDate) {

        lectureDate.setStartDate(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LectureDate> requestEntity = new HttpEntity<>(lectureDate, headers);
        String uri = this.getResourceEndpointForId(LECTURE_DATE_RESOURCE, id);
        ResponseEntity<LectureDate> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, LectureDate.class);
        return response;

    }

    public ResponseEntity<Lecturer> createLecturer(Lecturer lecturer) {
        String uri = getResourceEndpoint(LECTURER_RESOURCE);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Lecturer> requestEntity = new HttpEntity<>(lecturer, headers);
        ResponseEntity<Lecturer> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Lecturer.class);

        return response;
    }
    /**
     * End HTTP Client Methods
     */

}
