package com.docregistrationhub.Model;

import com.docregistrationhub.Enum.City;
import com.docregistrationhub.Enum.Speciality;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int doctorId;

    String doctor_name;

    @Enumerated(EnumType.STRING)
    City city;

    @Column(unique = true,nullable = false)
    String email;

    @Column(unique = true)
    String mobNo;

    @Enumerated(EnumType.STRING)
    Speciality speciality;
}
