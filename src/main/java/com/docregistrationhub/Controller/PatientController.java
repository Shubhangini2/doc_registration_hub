package com.docregistrationhub.Controller;

import com.docregistrationhub.Model.Patient;
import com.docregistrationhub.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    //ADD PATIENTS TO THE PLATFORM WITH BACKGROUND CHECK
    @PostMapping("/add/p")
    public ResponseEntity addPatient(@RequestBody Patient patient){
        try {
            String response = patientService.addPatient(patient);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    //REMOVE PATIENT FROM PLATFORM
    @DeleteMapping("/removePatient/{patientId}")
    public ResponseEntity removeDoctor(@PathVariable int patientId) {
        try {
            String response = patientService.removePatient(patientId);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
