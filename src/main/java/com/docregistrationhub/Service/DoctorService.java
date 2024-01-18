package com.docregistrationhub.Service;

import com.docregistrationhub.Enum.City;
import com.docregistrationhub.Enum.Speciality;
import com.docregistrationhub.Model.Doctor;
import com.docregistrationhub.Model.Patient;
import com.docregistrationhub.Repository.DoctorRepository;
import com.docregistrationhub.Repository.PatientRepository;
import com.docregistrationhub.exception.DoctorAvailabilityException;
import com.docregistrationhub.exception.DoctorLocationNotFound;
import com.docregistrationhub.exception.DoctorNotFoundException;
import com.docregistrationhub.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;


    //ADD DOCTORS TO THE PLATFORM
    public String addDoctor(Doctor doctor) {
        Optional <Doctor> savedDoc= doctorRepository.findByEmail(doctor.getEmail());
        if(savedDoc.isEmpty()){
            doctorRepository.save(doctor);
            return "Successfully Added!!";
        }
        return "Doctor Already Exists!!";
    }


    //REMOVE DOCTOR FROM THE PLATFORM
    public String removeDoctor(int doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isPresent()){
            doctorRepository.delete(doctor.get());
            return "Deleted Successfully!!";
        }
        throw new DoctorNotFoundException("Doctor Doesn't Exists!!");
    }


    //SUGGESTING DOCTORS
    public List<Doctor> suggestDoctor(int patientId) {

        //GETTING PATIENT DETAILS FROM PATIENT_ID
        Patient patient;
        String patientSymptom=null;
        String patientLocation=null;
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if(patientOptional.isPresent()){
             patient= patientOptional.get();
             patientSymptom= String.valueOf(patient.getSymptom());
             patientLocation = patient.getCityName();
        }
        else{
            throw new PatientNotFoundException("Patient Doesn't Exists!!");
        }


        //IF THERE IS NOT ANY DOCTOR ON THAT LOCATION(i.e. DELHI, NOIDA, FARIDABAD)
        List<Doctor> suggestedDoctors;
        if(patientLocation.equals("DELHI") || patientLocation.equals("NOIDA")|| patientLocation.equals("FARIDABAD")){
            suggestedDoctors=doctorRepository.findByCity(City.valueOf(patientLocation));
        }
        else{
               throw new DoctorLocationNotFound("We are still waiting to expand to your location!!");
            }


        //RETRIEVE DOCTORS BASED ON SYMPTOMS
        List<Doctor> finalDoctorsList=new ArrayList<>();
        if((patientSymptom.equals("ARTHRITIS"))|| (patientSymptom.equals("BACK_PAIN"))|| (patientSymptom.equals("TISSUE_INJURIES"))){
            for(Doctor doc: suggestedDoctors){
                if(doc.getSpeciality().equals(Speciality.valueOf("ORTHOPEDIC"))){
                    finalDoctorsList.add(doc);
                }
            }
        }

        else if(patientSymptom.equals("DYSMENORRHEA")){
            for(Doctor doc: suggestedDoctors){
                if(doc.getSpeciality().equals(Speciality.valueOf("GYNECOLOGY"))){
                    finalDoctorsList.add(doc);
                }
            }
        }
        else if(patientSymptom.equals("EAR_PAIN")){
            for(Doctor doc: suggestedDoctors){
                if(doc.getSpeciality().equals(Speciality.valueOf("ENT"))){
                    finalDoctorsList.add(doc);
                }
            }
        }
        else if((patientSymptom.equals("SKIN_INFECTION"))|| patientSymptom.equals("SKIN_BURN")){
            for(Doctor doc: suggestedDoctors){
                if(doc.getSpeciality().equals(Speciality.valueOf("DERMATOLOGY"))){
                    finalDoctorsList.add(doc);
                }
            }
        }

        //CHECK IF THE FINAL DOCTOR IS LIST IS EMPTY
        if (finalDoctorsList.isEmpty()) {
            throw new DoctorAvailabilityException("There isn’t any doctor present at your location for your symptom!!");
        }
        return finalDoctorsList;
    }
}

