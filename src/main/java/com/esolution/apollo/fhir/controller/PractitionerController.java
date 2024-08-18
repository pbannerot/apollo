package com.esolution.apollo.fhir.controller;

import java.util.List;

import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esolution.apollo.fhir.service.PractitionerService;

import ca.uhn.fhir.rest.api.MethodOutcome;

@RestController
@RequestMapping("/fhir/practitioners")
public class PractitionerController {
	@Autowired
    private PractitionerService practitionerService;

    @GetMapping
    public List<Practitioner> getAllPractitioners() {
        return practitionerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Practitioner> getPractitionerById(@PathVariable String id) {
        Practitioner practitioner = practitionerService.findById(id);
        if (practitioner != null) {
            return ResponseEntity.ok(practitioner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MethodOutcome createPractitioner(@RequestBody Practitioner practitioner) {
        return practitionerService.save(practitioner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Practitioner> updatePractitioner(@PathVariable String id, @RequestBody Practitioner practitionerDetails) {
        Practitioner practitioner = practitionerService.findById(id);
        if (practitioner != null) {
            practitioner.setName(practitionerDetails.getName());
//            practitioner.setDescription(practitionerDetails.getDescription());
//            practitioner.setStatus(practitionerDetails.getStatus());
            practitioner.setAddress(practitionerDetails.getAddress());
//            practitioner.setPosition(practitionerDetails.getPosition());
            MethodOutcome outcome = practitionerService.update(id, practitioner);
            return ResponseEntity.ok(practitioner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable String id) {
        Practitioner practitioner = practitionerService.findById(id);
        if (practitioner != null) {
            practitionerService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
