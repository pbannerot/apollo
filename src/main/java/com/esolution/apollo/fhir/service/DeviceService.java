package com.esolution.apollo.fhir.service;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Service
public class DeviceService {
	@Autowired
    private IGenericClient fhirClient;

    public List<Device> findAll() {
    	Bundle bundle = fhirClient.search().forResource(Device.class).returnBundle(Bundle.class).execute();
        List<Device> devices = new ArrayList<>();
        if (bundle.hasEntry()) {
            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                devices.add((Device) entry.getResource());
            }
        }
        return devices;
    }

    public Device findById(String id) {
        return fhirClient.read().resource(Device.class).withId(id).execute();
    }

    public MethodOutcome save(Device device) {
        return fhirClient.create().resource(device).execute();
    }

    public MethodOutcome update(String id, Device device) {
        device.setId(new IdType("Device", id));
        return fhirClient.update().resource(device).execute();
    }

    public void deleteById(String id) {
        fhirClient.delete().resourceById(new IdType("Device", id)).execute();
    }
}
