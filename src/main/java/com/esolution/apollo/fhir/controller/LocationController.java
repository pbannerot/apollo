package com.esolution.apollo.fhir.controller;

import java.util.List;

import org.hl7.fhir.r4.model.Location;
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

import com.esolution.apollo.fhir.service.LocationService;

import ca.uhn.fhir.rest.api.MethodOutcome;

@RestController
@RequestMapping("/fhir/locations")
public class LocationController {
	@Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    // $ curl -X GET "http://localhost:8080/fhir/locations/68" -H "Accept: application/fhir+json"
    public ResponseEntity<Location> getLocationById(@PathVariable String id) {
        Location location = locationService.findById(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MethodOutcome createLocation(@RequestBody Location location) {
        return locationService.save(location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable String id, @RequestBody Location locationDetails) {
        Location location = locationService.findById(id);
        if (location != null) {
            location.setName(locationDetails.getName());
            location.setDescription(locationDetails.getDescription());
            location.setStatus(locationDetails.getStatus());
            location.setAddress(locationDetails.getAddress());
            location.setPosition(locationDetails.getPosition());
            MethodOutcome outcome = locationService.update(id, location);
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        Location location = locationService.findById(id);
        if (location != null) {
            locationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
