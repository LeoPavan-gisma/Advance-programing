-- Sample Data for Patient Appointment Management System

-- Insert Sample Patients
INSERT INTO patients (first_name, last_name, age, phone_number, email, medical_history) VALUES
('John', 'Smith', 45, '555-0101', 'john.smith@email.com', 'Hypertension, Type 2 Diabetes'),
('Sarah', 'Johnson', 38, '555-0102', 'sarah.johnson@email.com', 'Asthma, Seasonal Allergies'),
('Michael', 'Brown', 52, '555-0103', 'michael.brown@email.com', 'Arthritis, Osteoporosis'),
('Emily', 'Davis', 29, '555-0104', 'emily.davis@email.com', 'None reported'),
('Robert', 'Wilson', 65, '555-0105', 'robert.wilson@email.com', 'Heart disease, High cholesterol'),
('Lisa', 'Anderson', 42, '555-0106', 'lisa.anderson@email.com', 'Migraine, Anxiety'),
('David', 'Garcia', 55, '555-0107', 'david.garcia@email.com', 'Kidney disease stage 2'),
('Jennifer', 'Martinez', 33, '555-0108', 'jennifer.martinez@email.com', 'Thyroid disorder');

-- Insert Sample Doctors
INSERT INTO doctors (first_name, last_name, specialty, department, phone_number, email, qualifications) VALUES
('Dr. James', 'Patterson', 'Cardiology', 'Heart & Vascular', '555-1001', 'dr.patterson@hospital.com', 'MD, Board Certified in Cardiology, 15+ years experience'),
('Dr. Angela', 'Rodriguez', 'General Medicine', 'Internal Medicine', '555-1002', 'dr.rodriguez@hospital.com', 'MD, Board Certified in Internal Medicine, 10+ years'),
('Dr. Michael', 'Thompson', 'Orthopedics', 'Orthopedic Surgery', '555-1003', 'dr.thompson@hospital.com', 'MD, Fellow of American College of Surgeons, Orthopedic Specialist'),
('Dr. Susan', 'Lee', 'Neurology', 'Neurology Department', '555-1004', 'dr.lee@hospital.com', 'MD, PhD, Neurologist, 12+ years experience'),
('Dr. Robert', 'Jackson', 'Pulmonology', 'Respiratory Disease', '555-1005', 'dr.jackson@hospital.com', 'MD, Pulmonary Specialist, 18+ years'),
('Dr. Linda', 'Harris', 'Endocrinology', 'Diabetes & Metabolism', '555-1006', 'dr.harris@hospital.com', 'MD, Board Certified in Endocrinology, Diabetes Specialist');

-- Insert Sample Diseases
INSERT INTO diseases (disease_name, description, symptoms, treatment) VALUES
('Hypertension', 'High blood pressure condition', 'Headaches, chest pain, shortness of breath', 'Medications, lifestyle changes, reduced salt intake'),
('Type 2 Diabetes', 'Metabolic disorder affecting blood sugar', 'Increased thirst, frequent urination, fatigue', 'Insulin, oral medications, diet control, exercise'),
('Asthma', 'Chronic respiratory disorder', 'Shortness of breath, wheezing, chest tightness', 'Inhalers, bronchodilators, corticosteroids'),
('Arthritis', 'Inflammation of joints', 'Joint pain, stiffness, reduced mobility', 'NSAIDs, physical therapy, exercise'),
('Heart Disease', 'Cardiovascular disease affecting heart function', 'Chest pain, shortness of breath, fatigue', 'Medications, lifestyle changes, surgery if needed'),
('Anxiety Disorder', 'Mental health condition causing excessive worry', 'Nervousness, restlessness, sleep disturbance', 'Psychotherapy, medications, lifestyle management'),
('Migraine', 'Severe headache disorder', 'Intense head pain, nausea, light sensitivity', 'Medications, rest, trigger avoidance'),
('Osteoporosis', 'Reduced bone density condition', 'Bone pain, fractures, height loss', 'Calcium supplements, vitamin D, medications');

-- Insert Sample Appointments (future dates for testing)
INSERT INTO appointments (patient_id, doctor_id, disease_id, appointment_date_time, reason, status, notes) VALUES
(1, 1, 5, DATE_ADD(NOW(), INTERVAL 7 DAY), 'Regular heart check-up', 'SCHEDULED', 'Annual cardiac evaluation'),
(2, 2, 3, DATE_ADD(NOW(), INTERVAL 5 DAY), 'Asthma management review', 'SCHEDULED', 'Check inhaler technique and effectiveness'),
(3, 3, 4, DATE_ADD(NOW(), INTERVAL 10 DAY), 'Joint pain assessment', 'SCHEDULED', 'Evaluate arthritis progression'),
(4, 2, 1, DATE_ADD(NOW(), INTERVAL 3 DAY), 'Routine check-up', 'SCHEDULED', 'General health evaluation'),
(5, 1, 5, DATE_ADD(NOW(), INTERVAL 14 DAY), 'Post treatment follow-up', 'SCHEDULED', 'Heart condition monitoring'),
(6, 4, 7, DATE_ADD(NOW(), INTERVAL 8 DAY), 'Migraine management', 'SCHEDULED', 'Discuss new migraine treatment options'),
(7, 2, 2, DATE_ADD(NOW(), INTERVAL 6 DAY), 'Diabetes follow-up', 'SCHEDULED', 'Blood sugar monitoring and adjustment'),
(8, 6, 2, DATE_ADD(NOW(), INTERVAL 12 DAY), 'Endocrinology consultation', 'SCHEDULED', 'Thyroid and metabolism assessment');

-- Insert Sample Medical Records
INSERT INTO medical_records (patient_id, doctor_id, record_type, diagnosis, treatment_plan, clinical_notes, status) VALUES
(1, 1, 'Consultation', 'Hypertension - Controlled', 'Continue current medications. Increase salt-restricted diet', 'Patient showing good compliance with treatment', 'ACTIVE'),
(2, 2, 'Consultation', 'Asthma - Moderate Persistent', 'Daily inhaler use, rescue inhaler as needed', 'Lung function improved since last visit', 'ACTIVE'),
(3, 3, 'Physical Examination', 'Osteoarthritis - Bilateral Knees', 'Physical therapy 2x/week, OTC pain management', 'Range of motion slightly improved with therapy', 'ACTIVE'),
(4, 2, 'Consultation', 'Vital Signs Normal', 'Maintain healthy lifestyle, annual check-up', 'All lab work within normal ranges', 'ACTIVE'),
(5, 1, 'Consultation', 'Coronary Artery Disease - Stable', 'Current medications effective, monitor closely', 'Recent stress test shows improvement', 'ACTIVE'),
(6, 4, 'Neurological Exam', 'Chronic Migraine with Aura', 'Preventive medication prescribed, migraine diary monitoring', 'Frequency of migraines has decreased', 'ACTIVE');

-- Insert Sample Patient Tests
INSERT INTO patient_tests (patient_id, doctor_id, test_name, test_type, result, normal_range, test_status) VALUES
(1, 1, 'Blood Pressure', 'Vital Signs', '135/85 mmHg', 'Less than 120/80 mmHg', 'COMPLETED'),
(2, 2, 'Spirometry', 'Pulmonary Function', '75% FEV1', 'Greater than 80% predicted', 'COMPLETED'),
(1, 1, 'Lipid Panel', 'Laboratory', 'Total Cholesterol: 190 mg/dL', 'Less than 200 mg/dL', 'COMPLETED'),
(3, 3, 'Bone Density Scan', 'Imaging', 'T-Score: -2.5', 'Greater than -1.0', 'COMPLETED'),
(4, 2, 'Complete Blood Count', 'Laboratory', 'All values normal', 'Within normal range', 'COMPLETED'),
(5, 1, 'Electrocardiogram', 'Cardiac', 'Normal sinus rhythm', 'No abnormalities', 'COMPLETED'),
(7, 2, 'Blood Glucose', 'Laboratory', '145 mg/dL (fasting)', 'Less than 100 mg/dL', 'COMPLETED');

-- Insert Sample Notifications
INSERT INTO notifications (patient_id, doctor_id, notification_type, title, message, is_read, status) VALUES
(1, NULL, 'APPOINTMENT_REMINDER', 'Upcoming Appointment', 'Your appointment with Dr. Patterson is scheduled for tomorrow at 2:00 PM', FALSE, 'ACTIVE'),
(2, NULL, 'MEDICATION_REMINDER', 'Take Your Medication', 'Time to take your evening asthma medication', FALSE, 'ACTIVE'),
(3, NULL, 'TEST_RESULT', 'Lab Results Available', 'Your bone density test results are now available. Please contact your doctor.', FALSE, 'ACTIVE'),
(4, 2, 'GENERAL_INFO', 'Health Tip', 'Remember to stay hydrated and maintain regular exercise routine', TRUE, 'ARCHIVED'),
(5, 1, 'APPOINTMENT_REMINDER', 'Schedule Your Follow-up', 'Please schedule your follow-up appointment within 2 weeks', FALSE, 'ACTIVE');

-- Insert Sample Prescriptions
INSERT INTO prescriptions (medicine_name, dosage, unit, frequency, duration_days, notes, appointment_id, doctor_id) VALUES
('Lisinopril', 10, 'mg', 'Once daily', 90, 'Take in the morning', 1, 1),
('Metformin', 500, 'mg', 'Twice daily', 90, 'Take with meals', 1, 2),
('Albuterol Inhaler', 90, 'mcg', 'As needed', 30, 'Use for acute symptoms', 2, 2),
('Ibuprofen', 400, 'mg', 'Three times daily', 14, 'For pain relief', 3, 3),
('Atorvastatin', 20, 'mg', 'Once daily at bedtime', 90, 'For cholesterol management', 5, 1);

-- Insert Sample Payments
INSERT INTO payments (appointment_id, patient_id, amount, payment_method, payment_status, description) VALUES
(1, 1, 150.00, 'CREDIT_CARD', 'COMPLETED', 'Cardiology consultation fee'),
(2, 2, 100.00, 'INSURANCE', 'COMPLETED', 'General consultation copay'),
(3, 3, 200.00, 'CREDIT_CARD', 'COMPLETED', 'Orthopedic evaluation'),
(4, 4, 80.00, 'CASH', 'COMPLETED', 'General check-up'),
(5, 5, 250.00, 'INSURANCE', 'PENDING', 'Cardiac follow-up consultation');
