package com.esolution.apollo.fhir.service;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Service
public class PatientService {
	@Autowired
    private IGenericClient fhirClient;

    public List<Patient> findAll() {
    	Bundle bundle = fhirClient.search().forResource(Patient.class).returnBundle(Bundle.class).execute();
        List<Patient> patients = new ArrayList<>();
        if (bundle.hasEntry()) {
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                patients.add((Patient) entry.getResource());
            }
        }
        return patients;
    }

    public Patient findById(String id) {
        return fhirClient.read().resource(Patient.class).withId(id).execute();
    }

    public MethodOutcome save(Patient patient) {
        return fhirClient.create().resource(patient).execute();
    }

    public MethodOutcome update(String id, Patient patient) {
        patient.setId(new IdType("Patient", id));
        return fhirClient.update().resource(patient).execute();
    }

    public void deleteById(String id) {
        fhirClient.delete().resourceById(new IdType("Patient", id)).execute();
    }
}
