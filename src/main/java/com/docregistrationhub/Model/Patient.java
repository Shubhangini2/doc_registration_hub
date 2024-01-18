package com.docregistrationhub.Model;


import com.docregistrationhub.Enum.Symptom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int patientId;

    String patient_name;

    String cityName;

    @Column(unique = true,nullable = false)
    String email;

    @Column(unique=true)
    String mobNo;

    @Enumerated(EnumType.STRING)
    Symptom symptom;
}
