package com.docregistrationhub.Controller;


import com.docregistrationhub.Model.Doctor;
import com.docregistrationhub.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    //ADD DOCTOR TO THE PLATFORM
    @PostMapping("/add/d")
    public ResponseEntity addDoctor(@RequestBody Doctor doctor) {
        String response = doctorService.addDoctor(doctor);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


    //REMOVE DOCTOR FROM PLATFORM
    @DeleteMapping("/removeDoc/{doctorId}")
    public ResponseEntity removeDoctor(@PathVariable int doctorId) {
        try {
            String response = doctorService.removeDoctor(doctorId);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


   //SUGGEST DOCTORS FOR PARTICULAR LOCATION AND SYMPTOM
    @GetMapping("/suggestedDoctors")
    public ResponseEntity  suggestDoctors(@RequestParam int patientId){
        try {
            List<Doctor> doctors = doctorService.suggestDoctor(patientId);
            return new ResponseEntity(doctors, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}