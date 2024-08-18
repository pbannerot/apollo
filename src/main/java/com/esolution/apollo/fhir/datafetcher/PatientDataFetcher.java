package com.esolution.apollo.fhir.datafetcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.esolution.apollo.fhir.model.PatientModel;
import com.esolution.apollo.fhir.service.PatientService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import graphql.execution.DataFetcherResult;

@DgsComponent
public class PatientDataFetcher {
	private final PatientService patientService;

//    @Autowired
    public PatientDataFetcher(PatientService patientService) {
        this.patientService = patientService;
    }
    
    @DgsQuery
    public DataFetcherResult<List<PatientModel>> patients() throws IOException {
        List<PatientModel> patients = patientService.patientsRequest();
        return DataFetcherResult.<List<PatientModel>>newResult()
                .data(patients)
                //.localContext(Map.of("hasAmenityData", false))
                .build();
    }

    @DgsQuery
    public DataFetcherResult<PatientModel> patient(@InputArgument String id) {
        PatientModel patient = patientService.patientRequest(id);
        return DataFetcherResult.<PatientModel>newResult()
                .data(patient)
                //.localContext(Map.of("hasAmenityData", true))
                .build();
    }
}
