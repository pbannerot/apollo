package com.esolution.apollo.fhir.service;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    
	@Autowired
    private IGenericClient fhirClient;

    public List<Location> findAll() {
    	Bundle bundle = fhirClient.search().forResource(Location.class).returnBundle(Bundle.class).execute();
        List<Location> locations = new ArrayList<>();
        if (bundle.hasEntry()) {
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                locations.add((Location) entry.getResource());
            }
        }
        return locations;
    }

    public Location findById(String id) {
        return fhirClient.read().resource(Location.class).withId(id).execute();
    }

    public MethodOutcome save(Location location) {
        return fhirClient.create().resource(location).execute();
    }

    public MethodOutcome update(String id, Location location) {
        location.setId(new IdType("Location", id));
        return fhirClient.update().resource(location).execute();
    }

    public void deleteById(String id) {
        fhirClient.delete().resourceById(new IdType("Location", id)).execute();
    }
}
