package com.docregistrationhub.Service;

import com.docregistrationhub.Model.Doctor;
import com.docregistrationhub.Model.Patient;
import com.docregistrationhub.Repository.PatientRepository;
import com.docregistrationhub.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PatientService {


    @Autowired
    PatientRepository patientRepository;

    //ADD PATIENT
    public String addPatient(Patient patient) {

        Optional<Patient> patientOptional= patientRepository.findByEmail(patient.getEmail());
        if(patientOptional.isEmpty()) {
            validateNameLength(patient.getPatient_name());
            validateCityLength(patient.getCityName());
            validateEmail(patient.getEmail());
            validateMobNo(patient.getMobNo());
            patientRepository.save(patient);
            return "Patient Added Successfully!!";
        }
        else{
            return "Patient Already Exists!!";
        }
    }

    //VALIDATIONS AT BACKEND:-

    //NAME SHOULD BE AT LEAST 3 CHARACTER
    public void validateNameLength(String patientName){
        if(patientName.length()<3){
            throw new InvalidNameException("Name should have be at least 3 characters!!");
        }
    }

    //CITY SHOULD BE AT MAX 20 CHARACTER
    public void validateCityLength(String cityName){
        if(cityName.length()>20){
           throw new InvalidCityNameException("City name should have at most 20 characters!!");
        }
    }

    //EMAIL SHOULD BE A VALID EMAIL ADDRESS
    public void validateEmail(String email) {

        // You can also use EmailValidator from Apache Commons Validator

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(email).matches()) {
        } else {
            throw new InvalidEmailException("Invalid Email!!");
        }
    }

    //PHONE NUMBER SHOULD BE 10 DIGITS
    public void validateMobNo(String mobNo){
        if(mobNo.length()<10 || mobNo.length()>10){
            throw new InvalidMobileNumberException("Mobile Number should have 10 digits!!");
        }
    }


    //REMOVE PATIENT FROM THE PLATFORM
    public String removePatient(int patientId) {

        Optional<Patient> patient = patientRepository.findById(patientId);
        if(patient.isPresent()){
            patientRepository.delete(patient.get());
            return "Deleted Successfully!!";
        }
        throw new PatientNotFoundException("Patient Doesn't Exists!!");
    }
}
