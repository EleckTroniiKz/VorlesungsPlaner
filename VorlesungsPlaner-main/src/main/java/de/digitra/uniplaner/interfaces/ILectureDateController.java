package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ILectureDateController {
    /**
     * {@code POST  /lecturedates} : Methode erstellt eine Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server erstellt werden soll.
     * Parameter lecturedate ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls lecturedate nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<LectureDate> createLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException;

    /**
     * {@code PUT  /lecturedates} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server aktualisiert werden soll.
     *                    Diese Instanz enthält die aktuellen Werte.
     * Der Parameter lecturedate ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls lecturedate nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException;

    /**
     * {@code PUT  /lecturedates/:id} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param id                 Id der Ressource vom Typ LectureDate, die am Server aktualisiert werden soll.
     * @param lecturedateDetails Instanz von LectureDate, die am Server aktualisiert werden soll.
     *                           Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */

    @PutMapping("/{id}")
    ResponseEntity<LectureDate> updateLectureDate(@PathVariable(value = "id") Long id, @Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /lecturedates} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ LectureDate.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ LectureDate.
     */
    @GetMapping
    ResponseEntity<List<LectureDate>> getAlllecturedates();

    /**
     * {@code GET  /lecturedates/:id} : Liefert die Ressource vom Typ LectureDate mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ LectureDate, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ LectureDate.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /lecturedates/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ LectureDate, die gelöscht werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLectureDate(@PathVariable Long id);
}
