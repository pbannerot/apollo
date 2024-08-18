package com.esolution.apollo.fhir.service;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Service
public class PractitionerService {
	@Autowired
    private IGenericClient fhirClient;

    public List<Practitioner> findAll() {
    	Bundle bundle = fhirClient.search().forResource(Practitioner.class).returnBundle(Bundle.class).execute();
        List<Practitioner> practitioners = new ArrayList<>();
        if (bundle.hasEntry()) {
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                practitioners.add((Practitioner) entry.getResource());
            }
        }
        return practitioners;
    }

    public Practitioner findById(String id) {
        return fhirClient.read().resource(Practitioner.class).withId(id).execute();
    }

    public MethodOutcome save(Practitioner practitioner) {
        return fhirClient.create().resource(practitioner).execute();
    }

    public MethodOutcome update(String id, Practitioner practitioner) {
        practitioner.setId(new IdType("Practitioner", id));
        return fhirClient.update().resource(practitioner).execute();
    }

    public void deleteById(String id) {
        fhirClient.delete().resourceById(new IdType("Practitioner", id)).execute();
    }
}
