package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ILectureController {
    /**
     * {@code POST  /lectures} : Methode erstellt eine Ressource vom Typ Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server erstellt werden soll.
     * Parameter lecture ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls lecture nicht zulässig ist.
     *
     */
    @PostMapping
    ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) throws BadRequestException;

    /**
     * {@code PUT  /lectures} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server aktualisiert werden soll.
     *                Diese Instanz enthält die aktuellen Werte.
     * Der Parameter lecture ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls lecture nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws BadRequestException;

    /**
     * {@code PUT  /lectures/:id} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param id             Id der Ressource vom Typ Lecture, die am Server aktualisiert werden soll.
     * @param lectureDetails Instanz von Lecture, die am Server aktualisiert werden soll.
     *                       Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /lectures} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Lecture.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Lecture.
     */
    @GetMapping
    ResponseEntity<List<Lecture>> getAlllectures();

    /**
     * {@code GET  /lectures/:id} : Liefert die Ressource vom Typ Lecture mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Lecture, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ Lecture.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /lectures/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ Lecture, die gelöscht werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLecture(@PathVariable Long id);
}
