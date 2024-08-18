package com.esolution.apollo.fhir.controller;

import java.util.List;

import org.hl7.fhir.r4.model.Device;
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

import com.esolution.apollo.fhir.service.DeviceService;

import ca.uhn.fhir.rest.api.MethodOutcome;

@RestController
@RequestMapping("/fhir/devices")
public class DeviceController {
	@Autowired
    private DeviceService deviceService;

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable String id) {
        Device device = deviceService.findById(id);
        if (device != null) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MethodOutcome createDevice(@RequestBody Device device) {
        return deviceService.save(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable String id, @RequestBody Device deviceDetails) {
        Device device = deviceService.findById(id);
        if (device != null) {
//            device.setName(deviceDetails.getName());
//            device.setDescription(deviceDetails.getDescription());
            device.setStatus(deviceDetails.getStatus());
//            device.setAddress(deviceDetails.getAddress());
//            device.setPosition(deviceDetails.getPosition());
            MethodOutcome outcome = deviceService.update(id, device);
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        Device device = deviceService.findById(id);
        if (device != null) {
            deviceService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
