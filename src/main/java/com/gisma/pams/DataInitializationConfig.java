package com.gisma.pams;

import com.gisma.pams.model.Disease;
import com.gisma.pams.model.Doctor;
import com.gisma.pams.model.Patient;
import com.gisma.pams.repository.DiseaseRepository;
import com.gisma.pams.repository.DoctorRepository;
import com.gisma.pams.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializationConfig {

    @Bean
    CommandLineRunner initializeData(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            DiseaseRepository diseaseRepository) {
        return args -> {
            if (patientRepository.count() == 0) {
                initializePatients(patientRepository);
            }
            if (doctorRepository.count() == 0) {
                initializeDoctors(doctorRepository);
            }
            if (diseaseRepository.count() == 0) {
                initializeDiseases(diseaseRepository);
            }
        };
    }

    private void initializePatients(PatientRepository patientRepository) {
        Patient patient1 = new Patient();
        patient1.setFirstName("Ahmed");
        patient1.setLastName("Hassan");
        patient1.setAge(35);
        patient1.setPhoneNumber("9876543210");
        patient1.setEmail("ahmed.hassan@email.com");
        patient1.setMedicalHistory("Hypertension, Diabetes Type 2");

        Patient patient2 = new Patient();
        patient2.setFirstName("Fatima");
        patient2.setLastName("Khan");
        patient2.setAge(42);
        patient2.setPhoneNumber("8765432109");
        patient2.setEmail("fatima.khan@email.com");
        patient2.setMedicalHistory("Asthma, Allergies");

        Patient patient3 = new Patient();
        patient3.setFirstName("Mohammed");
        patient3.setLastName("Ali");
        patient3.setAge(28);
        patient3.setPhoneNumber("7654321098");
        patient3.setEmail("mohammed.ali@email.com");
        patient3.setMedicalHistory("No known allergies");

        Patient patient4 = new Patient();
        patient4.setFirstName("Zahra");
        patient4.setLastName("Ibrahim");
        patient4.setAge(55);
        patient4.setPhoneNumber("6543210987");
        patient4.setEmail("zahra.ibrahim@email.com");
        patient4.setMedicalHistory("Arthritis, High Cholesterol");

        Patient patient5 = new Patient();
        patient5.setFirstName("Omar");
        patient5.setLastName("Malik");
        patient5.setAge(31);
        patient5.setPhoneNumber("5432109876");
        patient5.setEmail("omar.malik@email.com");
        patient5.setMedicalHistory("Migraine, Sleep Apnea");

        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);
        patientRepository.save(patient4);
        patientRepository.save(patient5);
    }

    private void initializeDoctors(DoctorRepository doctorRepository) {
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Dr. Khalid");
        doctor1.setLastName("Ahmed");
        doctor1.setSpecialty("Cardiology");
        doctor1.setDepartment("Heart & Vascular");
        doctor1.setPhoneNumber("1111111111");
        doctor1.setEmail("dr.khalid@hospital.com");
        doctor1.setQualifications("MD, Board Certified in Cardiology, 15 years experience");

        Doctor doctor2 = new Doctor();
        doctor2.setFirstName("Dr. Leila");
        doctor2.setLastName("Salam");
        doctor2.setSpecialty("Pediatrics");
        doctor2.setDepartment("Children & Adolescents");
        doctor2.setPhoneNumber("2222222222");
        doctor2.setEmail("dr.leila@hospital.com");
        doctor2.setQualifications("MD, Specialist in Pediatrics, 12 years experience");

        Doctor doctor3 = new Doctor();
        doctor3.setFirstName("Dr. Samir");
        doctor3.setLastName("Rashid");
        doctor3.setSpecialty("Orthopedics");
        doctor3.setDepartment("Bone & Joint Surgery");
        doctor3.setPhoneNumber("3333333333");
        doctor3.setEmail("dr.samir@hospital.com");
        doctor3.setQualifications("MD, Orthopedic Surgeon, 18 years experience");

        Doctor doctor4 = new Doctor();
        doctor4.setFirstName("Dr. Amina");
        doctor4.setLastName("Noor");
        doctor4.setSpecialty("Dermatology");
        doctor4.setDepartment("Skin & Allergy");
        doctor4.setPhoneNumber("4444444444");
        doctor4.setEmail("dr.amina@hospital.com");
        doctor4.setQualifications("MD, Dermatologist, 10 years experience");

        Doctor doctor5 = new Doctor();
        doctor5.setFirstName("Dr. Hassan");
        doctor5.setLastName("Farah");
        doctor5.setSpecialty("Neurology");
        doctor5.setDepartment("Brain & Nervous System");
        doctor5.setPhoneNumber("5555555555");
        doctor5.setEmail("dr.hassan@hospital.com");
        doctor5.setQualifications("MD, Neurologist, 14 years experience");

        Doctor doctor6 = new Doctor();
        doctor6.setFirstName("Dr. Samira");
        doctor6.setLastName("Mahmoud");
        doctor6.setSpecialty("Oncology");
        doctor6.setDepartment("Cancer Care");
        doctor6.setPhoneNumber("6666666666");
        doctor6.setEmail("dr.samira@hospital.com");
        doctor6.setQualifications("MD, Medical Oncologist, 11 years experience");

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);
        doctorRepository.save(doctor4);
        doctorRepository.save(doctor5);
        doctorRepository.save(doctor6);
    }

    private void initializeDiseases(DiseaseRepository diseaseRepository) {
        Disease disease1 = new Disease();
        disease1.setDiseaseName("Hypertension");
        disease1.setDescription("High blood pressure condition");
        disease1.setSymptoms("Headaches, chest pain, shortness of breath, nosebleed");
        disease1.setTreatment("Medication, diet changes, exercise, stress management");

        Disease disease2 = new Disease();
        disease2.setDiseaseName("Diabetes Type 2");
        disease2.setDescription("Insulin resistance disorder");
        disease2.setSymptoms("Increased thirst, frequent urination, fatigue, blurred vision");
        disease2.setTreatment("Medication, insulin therapy, diet control, exercise");

        Disease disease3 = new Disease();
        disease3.setDiseaseName("Asthma");
        disease3.setDescription("Chronic airway inflammation disease");
        disease3.setSymptoms("Wheezing, breathing difficulty, chest tightness, coughing");
        disease3.setTreatment("Inhalers, bronchodilators, corticosteroids, avoid triggers");

        Disease disease4 = new Disease();
        disease4.setDiseaseName("Arthritis");
        disease4.setDescription("Joint inflammation disease");
        disease4.setSymptoms("Joint pain, stiffness, swelling, reduced movement");
        disease4.setTreatment("Anti-inflammatory drugs, physical therapy, joint protection");

        Disease disease5 = new Disease();
        disease5.setDiseaseName("Migraine");
        disease5.setDescription("Severe headache disorder");
        disease5.setSymptoms("Intense headache, nausea, light sensitivity, vision changes");
        disease5.setTreatment("Pain relief medication, rest, avoiding triggers, preventive drugs");

        Disease disease6 = new Disease();
        disease6.setDiseaseName("Sleep Apnea");
        disease6.setDescription("Sleep disorder with breathing interruption");
        disease6.setSymptoms("Loud snoring, daytime sleepiness, breathing pauses during sleep, insomnia");
        disease6.setTreatment("CPAP therapy, positional changes, weight management, surgery if needed");

        Disease disease7 = new Disease();
        disease7.setDiseaseName("High Cholesterol");
        disease7.setDescription("Elevated cholesterol levels in blood");
        disease7.setSymptoms("Usually asymptomatic, detected through blood tests");
        disease7.setTreatment("Statins, dietary changes, exercise, weight loss");

        Disease disease8 = new Disease();
        disease8.setDiseaseName("Heart Disease");
        disease8.setDescription("Cardiovascular system disorders");
        disease8.setSymptoms("Chest pain, shortness of breath, fatigue, irregular heartbeat");
        disease8.setTreatment("Medication, lifestyle changes, surgery, cardiac rehabilitation");

        diseaseRepository.save(disease1);
        diseaseRepository.save(disease2);
        diseaseRepository.save(disease3);
        diseaseRepository.save(disease4);
        diseaseRepository.save(disease5);
        diseaseRepository.save(disease6);
        diseaseRepository.save(disease7);
        diseaseRepository.save(disease8);
    }
}
