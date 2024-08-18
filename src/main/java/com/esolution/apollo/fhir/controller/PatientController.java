package com.esolution.apollo.fhir.controller;

import java.util.List;

import org.hl7.fhir.r4.model.Patient;
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

import com.esolution.apollo.fhir.service.PatientService;

import ca.uhn.fhir.rest.api.MethodOutcome;

@RestController
@RequestMapping("/fhir/patients")
public class PatientController {
	@Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll();
    }

    // $ curl -X GET "http://localhost:8080/fhir/patients/595250" -H "Accept: application/fhir+json"
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        Patient patient = patientService.findById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MethodOutcome createPatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient patientDetails) {
        Patient patient = patientService.findById(id);
        if (patient != null) {
            patient.setName(patientDetails.getName());
//            patient.setDescription(patientDetails.getDescription());
//            patient.setStatus(patientDetails.getStatus());
            patient.setAddress(patientDetails.getAddress());
//            patient.setPosition(patientDetails.getPosition());
            MethodOutcome outcome = patientService.update(id, patient);
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        Patient patient = patientService.findById(id);
        if (patient != null) {
            patientService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
