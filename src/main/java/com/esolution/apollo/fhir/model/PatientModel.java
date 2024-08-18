package com.esolution.apollo.fhir.model;

import com.esolution.apollo.codegen.types.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientModel extends Patient {

}
