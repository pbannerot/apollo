package com.esolution.apollo.fhir.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.esolution.apollo.fhir.model.PatientModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    // ----------------------------------------------------------------------------
    private static final String PATIENT_API_URL = "http://localhost:8080/fhir";
    private final RestClient client = RestClient.builder().baseUrl(PATIENT_API_URL).build();

    private final ObjectMapper mapper = new ObjectMapper();
    public List<PatientModel> patientsRequest() throws IOException {
        JsonNode response = client
                .get()
                .uri("/patients")
                .retrieve()
                .body(JsonNode.class);

        if (response != null) {
            return mapper.readValue(response.traverse(), new TypeReference<List<PatientModel>>() {});
        }

        return null;
    }
    
    public PatientModel patientRequest(String id) {
        return client
                .get()
                .uri("/listings/{listing_id}", id)
                .retrieve()
                .body(PatientModel.class);
    }
}
