// Application State
let appState = {
    currentTab: 'dashboard',
    patients: [],
    appointments: [],
    doctors: [],
    medicalRecords: [],
    editingPatientId: null,
    editingAppointmentId: null,
    editingDoctorId: null,
    editingMedicalId: null
};

// Initialize application
document.addEventListener('DOMContentLoaded', () => {
    initializeApp();
    setupEventListeners();
    loadDashboardData();
});

async function initializeApp() {
    // Check API health
    try {
        const isHealthy = await apiClient.checkHealth();
        updateAPIStatus(isHealthy);
    } catch (error) {
        console.error('Failed to connect to API:', error);
        updateAPIStatus(false);
    }
}

function updateAPIStatus(isConnected) {
    const statusDot = document.getElementById('statusIndicator');
    const statusText = document.getElementById('statusText');
    
    if (isConnected) {
        statusDot.classList.add('connected');
        statusDot.classList.remove('error');
        statusText.textContent = 'Connected';
    } else {
        statusDot.classList.remove('connected');
        statusDot.classList.add('error');
        statusText.textContent = 'Disconnected';
    }
}

// Setup Event Listeners
function setupEventListeners() {
    // Search inputs
    document.getElementById('patientSearchInput')?.addEventListener('input', (e) => {
        handlePatientSearch(e.target.value);
    });

    document.getElementById('appointmentSearchInput')?.addEventListener('input', (e) => {
        handleAppointmentSearch(e.target.value);
    });

    document.getElementById('doctorSearchInput')?.addEventListener('input', (e) => {
        handleDoctorSearch(e.target.value);
    });

    // Modal close on background click
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.classList.remove('show');
            }
        });
    });
}

// Tab Switching
function switchTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    // Remove active state from all nav buttons
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // Show selected tab
    const tabElement = document.getElementById(tabName);
    if (tabElement) {
        tabElement.classList.add('active');
    }

    // Add active state to clicked button
    event.target.classList.add('active');
    appState.currentTab = tabName;

    // Load data for the tab
    switch (tabName) {
        case 'patients':
            loadPatients();
            break;
        case 'appointments':
            loadAppointments();
            break;
        case 'doctors':
            loadDoctors();
            break;
        case 'medical':
            loadMedicalRecords();
            break;
    }
}

// ==================== Dashboard ====================

async function loadDashboardData() {
    try {
        // Load all data in parallel
        const [patients, appointments, doctors, medicalRecords] = await Promise.all([
            apiClient.getAllPatients(),
            apiClient.getAllAppointments(),
            apiClient.getAllDoctors(),
            apiClient.getAllMedicalRecords()
        ]);

        appState.patients = patients;
        appState.appointments = appointments;
        appState.doctors = doctors;
        appState.medicalRecords = medicalRecords;

        // Update dashboard stats
        updateDashboardStats();
        displayRecentAppointments();
        displayRecentPatients();
    } catch (error) {
        console.error('Failed to load dashboard data:', error);
        showToast('Failed to load dashboard data', 'error');
    }
}

function updateDashboardStats() {
    document.getElementById('totalPatients').textContent = appState.patients.length;
    document.getElementById('totalAppointments').textContent = appState.appointments.length;
    document.getElementById('totalDoctors').textContent = appState.doctors.length;
    
    // Count active appointments (SCHEDULED status)
    const activeAppointments = appState.appointments.filter(apt => apt.status === 'SCHEDULED').length;
    document.getElementById('activeAppointments').textContent = activeAppointments;
}

function displayRecentAppointments() {
    const container = document.getElementById('recentAppointmentsList');
    const recent = appState.appointments.slice(0, 5);

    if (recent.length === 0) {
        container.innerHTML = '<p class="text-muted">No appointments yet</p>';
        return;
    }

    container.innerHTML = recent.map(apt => `
        <div class="list-item">
            <strong>${apt.patientName || 'Unknown Patient'}</strong>
            <div class="text-muted" style="font-size: 0.85rem;">
                Dr. ${apt.doctorName || 'Unknown'} - ${formatDate(apt.appointmentDateTime)}
            </div>
        </div>
    `).join('');
}

function displayRecentPatients() {
    const container = document.getElementById('recentPatientsList');
    const recent = appState.patients.slice(0, 5);

    if (recent.length === 0) {
        container.innerHTML = '<p class="text-muted">No patients yet</p>';
        return;
    }

    container.innerHTML = recent.map(patient => `
        <div class="list-item">
            <strong>${patient.firstName} ${patient.lastName}</strong>
            <div class="text-muted" style="font-size: 0.85rem;">
                ${patient.email} • ${patient.phoneNumber}
            </div>
        </div>
    `).join('');
}

// ==================== Patients ====================

async function loadPatients() {
    try {
        appState.patients = await apiClient.getAllPatients();
        displayPatients(appState.patients);
    } catch (error) {
        console.error('Failed to load patients:', error);
        showToast('Failed to load patients', 'error');
    }
}

function displayPatients(patients) {
    const tbody = document.getElementById('patientsList');
    
    if (patients.length === 0) {
        tbody.innerHTML = '<tr class="loading"><td colspan="6">No patients found</td></tr>';
        return;
    }

    tbody.innerHTML = patients.map(patient => `
        <tr>
            <td>${patient.patientId || '-'}</td>
            <td>${patient.firstName} ${patient.lastName}</td>
            <td>${patient.email || '-'}</td>
            <td>${patient.phoneNumber || '-'}</td>
            <td>${formatDate(patient.dateOfBirth)}</td>
            <td>
                <button class="btn btn-edit" onclick="editPatient(${patient.patientId})">Edit</button>
                <button class="btn btn-danger" onclick="deletePatientConfirm(${patient.patientId})">Delete</button>
            </td>
        </tr>
    `).join('');
}

async function handlePatientSearch(searchTerm) {
    if (!searchTerm.trim()) {
        displayPatients(appState.patients);
        return;
    }

    try {
        const results = await apiClient.searchPatients(searchTerm);
        displayPatients(results);
    } catch (error) {
        console.error('Search failed:', error);
    }
}

function openPatientModal() {
    appState.editingPatientId = null;
    document.getElementById('patientId').value = '';
    document.getElementById('patientForm').reset();
    openModal('patientModal');
}

async function editPatient(patientId) {
    try {
        const patient = await apiClient.getPatientById(patientId);
        appState.editingPatientId = patientId;
        document.getElementById('patientId').value = patient.patientId;
        document.getElementById('patientFirstName').value = patient.firstName;
        document.getElementById('patientLastName').value = patient.lastName;
        document.getElementById('patientAge').value = patient.age;
        document.getElementById('patientEmail').value = patient.email;
        document.getElementById('patientPhone').value = patient.phoneNumber;
        document.getElementById('patientDOB').value = patient.dateOfBirth || '';
        document.getElementById('patientGender').value = patient.gender || '';
        document.getElementById('patientAddress').value = patient.address || '';
        document.getElementById('patientMedicalHistory').value = patient.medicalHistory || '';
        openModal('patientModal');
    } catch (error) {
        showToast('Failed to load patient details', 'error');
    }
}

async function submitPatientForm(e) {
    e.preventDefault();
    
    const patientData = {
        firstName: document.getElementById('patientFirstName').value,
        lastName: document.getElementById('patientLastName').value,
        age: parseInt(document.getElementById('patientAge').value),
        email: document.getElementById('patientEmail').value,
        phoneNumber: document.getElementById('patientPhone').value,
        dateOfBirth: document.getElementById('patientDOB').value,
        gender: document.getElementById('patientGender').value,
        address: document.getElementById('patientAddress').value,
        medicalHistory: document.getElementById('patientMedicalHistory').value
    };

    try {
        if (appState.editingPatientId) {
            await apiClient.updatePatient(appState.editingPatientId, patientData);
            showToast('Patient updated successfully', 'success');
        } else {
            await apiClient.createPatient(patientData);
            showToast('Patient created successfully', 'success');
        }
        closeModal('patientModal');
        loadPatients();
    } catch (error) {
        showToast('Failed to save patient', 'error');
        console.error(error);
    }
}

async function deletePatientConfirm(patientId) {
    if (confirm('Are you sure you want to delete this patient?')) {
        try {
            await apiClient.deletePatient(patientId);
            showToast('Patient deleted successfully', 'success');
            loadPatients();
        } catch (error) {
            showToast('Failed to delete patient', 'error');
        }
    }
}

// ==================== Appointments ====================

async function loadAppointments() {
    try {
        appState.appointments = await apiClient.getAllAppointments();
        displayAppointments(appState.appointments);
        populateAppointmentSelects();
    } catch (error) {
        console.error('Failed to load appointments:', error);
        showToast('Failed to load appointments', 'error');
    }
}

function displayAppointments(appointments) {
    const tbody = document.getElementById('appointmentsList');
    
    if (appointments.length === 0) {
        tbody.innerHTML = '<tr class="loading"><td colspan="6">No appointments found</td></tr>';
        return;
    }

    tbody.innerHTML = appointments.map(apt => `
        <tr>
            <td>${apt.appointmentId || '-'}</td>
            <td>${apt.patientName || 'Unknown'}</td>
            <td>${apt.doctorName || 'Unknown'}</td>
            <td>${formatDateTime(apt.appointmentDateTime)}</td>
            <td><span class="badge ${getStatusBadgeClass(apt.status)}">${apt.status || 'SCHEDULED'}</span></td>
            <td>
                <button class="btn btn-edit" onclick="editAppointment(${apt.appointmentId})">Edit</button>
                <button class="btn btn-danger" onclick="deleteAppointmentConfirm(${apt.appointmentId})">Delete</button>
            </td>
        </tr>
    `).join('');
}

async function handleAppointmentSearch(searchTerm) {
    if (!searchTerm.trim()) {
        displayAppointments(appState.appointments);
        return;
    }

    const filtered = appState.appointments.filter(apt =>
        (apt.patientName && apt.patientName.toLowerCase().includes(searchTerm.toLowerCase())) ||
        (apt.doctorName && apt.doctorName.toLowerCase().includes(searchTerm.toLowerCase()))
    );
    displayAppointments(filtered);
}

async function populateAppointmentSelects() {
    try {
        // Populate patient select
        const patientSelect = document.getElementById('appointmentPatient');
        if (patientSelect && appState.patients.length > 0) {
            patientSelect.innerHTML = '<option value="">Select a patient...</option>' +
                appState.patients.map(p => 
                    `<option value="${p.patientId}">${p.firstName} ${p.lastName}</option>`
                ).join('');
        }

        // Populate doctor select
        const doctorSelect = document.getElementById('appointmentDoctor');
        if (doctorSelect && appState.doctors.length > 0) {
            doctorSelect.innerHTML = '<option value="">Select a doctor...</option>' +
                appState.doctors.map(d => 
                    `<option value="${d.doctorId}">Dr. ${d.firstName} ${d.lastName} (${d.specialization})</option>`
                ).join('');
        }
    } catch (error) {
        console.error('Failed to populate appointment selects:', error);
    }
}

function openAppointmentModal() {
    appState.editingAppointmentId = null;
    document.getElementById('appointmentId').value = '';
    document.getElementById('appointmentForm').reset();
    populateAppointmentSelects();
    openModal('appointmentModal');
}

async function editAppointment(appointmentId) {
    try {
        const apt = await apiClient.getAppointmentById(appointmentId);
        appState.editingAppointmentId = appointmentId;
        document.getElementById('appointmentId').value = apt.appointmentId;
        document.getElementById('appointmentPatient').value = apt.patientId;
        document.getElementById('appointmentDoctor').value = apt.doctorId;
        
        const dateTime = new Date(apt.appointmentDateTime);
        document.getElementById('appointmentDate').value = dateTime.toISOString().split('T')[0];
        document.getElementById('appointmentTime').value = 
            `${String(dateTime.getHours()).padStart(2, '0')}:${String(dateTime.getMinutes()).padStart(2, '0')}`;
        
        document.getElementById('appointmentReason').value = apt.reason || '';
        document.getElementById('appointmentStatus').value = apt.status || 'SCHEDULED';
        
        populateAppointmentSelects();
        openModal('appointmentModal');
    } catch (error) {
        showToast('Failed to load appointment details', 'error');
    }
}

async function submitAppointmentForm(e) {
    e.preventDefault();
    
    const date = document.getElementById('appointmentDate').value;
    const time = document.getElementById('appointmentTime').value;
    const dateTime = new Date(`${date}T${time}`);

    const appointmentData = {
        patientId: parseInt(document.getElementById('appointmentPatient').value),
        doctorId: parseInt(document.getElementById('appointmentDoctor').value),
        appointmentDateTime: dateTime.toISOString(),
        reason: document.getElementById('appointmentReason').value,
        status: document.getElementById('appointmentStatus').value
    };

    try {
        if (appState.editingAppointmentId) {
            await apiClient.updateAppointment(appState.editingAppointmentId, appointmentData);
            showToast('Appointment updated successfully', 'success');
        } else {
            await apiClient.createAppointment(appointmentData);
            showToast('Appointment scheduled successfully', 'success');
        }
        closeModal('appointmentModal');
        loadAppointments();
    } catch (error) {
        showToast('Failed to save appointment', 'error');
        console.error(error);
    }
}

async function deleteAppointmentConfirm(appointmentId) {
    if (confirm('Are you sure you want to delete this appointment?')) {
        try {
            await apiClient.deleteAppointment(appointmentId);
            showToast('Appointment deleted successfully', 'success');
            loadAppointments();
        } catch (error) {
            showToast('Failed to delete appointment', 'error');
        }
    }
}

// ==================== Doctors ====================

async function loadDoctors() {
    try {
        appState.doctors = await apiClient.getAllDoctors();
        displayDoctors(appState.doctors);
    } catch (error) {
        console.error('Failed to load doctors:', error);
        showToast('Failed to load doctors', 'error');
    }
}

function displayDoctors(doctors) {
    const tbody = document.getElementById('doctorsList');
    
    if (doctors.length === 0) {
        tbody.innerHTML = '<tr class="loading"><td colspan="6">No doctors found</td></tr>';
        return;
    }

    tbody.innerHTML = doctors.map(doctor => `
        <tr>
            <td>${doctor.doctorId || '-'}</td>
            <td>${doctor.firstName} ${doctor.lastName}</td>
            <td>${doctor.specialization || '-'}</td>
            <td>${doctor.email || '-'}</td>
            <td>${doctor.phoneNumber || '-'}</td>
            <td>
                <button class="btn btn-edit" onclick="editDoctor(${doctor.doctorId})">Edit</button>
                <button class="btn btn-danger" onclick="deleteDoctorConfirm(${doctor.doctorId})">Delete</button>
            </td>
        </tr>
    `).join('');
}

async function handleDoctorSearch(searchTerm) {
    if (!searchTerm.trim()) {
        displayDoctors(appState.doctors);
        return;
    }

    const filtered = appState.doctors.filter(doctor =>
        `${doctor.firstName} ${doctor.lastName}`.toLowerCase().includes(searchTerm.toLowerCase()) ||
        (doctor.specialization && doctor.specialization.toLowerCase().includes(searchTerm.toLowerCase()))
    );
    displayDoctors(filtered);
}

function openDoctorModal() {
    appState.editingDoctorId = null;
    document.getElementById('doctorId').value = '';
    document.getElementById('doctorForm').reset();
    openModal('doctorModal');
}

async function editDoctor(doctorId) {
    try {
        const doctor = await apiClient.getDoctorById(doctorId);
        appState.editingDoctorId = doctorId;
        document.getElementById('doctorId').value = doctor.doctorId;
        document.getElementById('doctorName').value = `${doctor.firstName} ${doctor.lastName}`;
        document.getElementById('doctorSpecialization').value = doctor.specialization;
        document.getElementById('doctorEmail').value = doctor.email;
        document.getElementById('doctorPhone').value = doctor.phoneNumber;
        openModal('doctorModal');
    } catch (error) {
        showToast('Failed to load doctor details', 'error');
    }
}

async function submitDoctorForm(e) {
    e.preventDefault();
    
    const doctorData = {
        firstName: document.getElementById('doctorName').value.split(' ')[0],
        lastName: document.getElementById('doctorName').value.split(' ').slice(1).join(' '),
        specialization: document.getElementById('doctorSpecialization').value,
        email: document.getElementById('doctorEmail').value,
        phoneNumber: document.getElementById('doctorPhone').value
    };

    try {
        if (appState.editingDoctorId) {
            await apiClient.updateDoctor(appState.editingDoctorId, doctorData);
            showToast('Doctor updated successfully', 'success');
        } else {
            await apiClient.createDoctor(doctorData);
            showToast('Doctor added successfully', 'success');
        }
        closeModal('doctorModal');
        loadDoctors();
    } catch (error) {
        showToast('Failed to save doctor', 'error');
        console.error(error);
    }
}

async function deleteDoctorConfirm(doctorId) {
    if (confirm('Are you sure you want to delete this doctor?')) {
        try {
            await apiClient.deleteDoctor(doctorId);
            showToast('Doctor deleted successfully', 'success');
            loadDoctors();
        } catch (error) {
            showToast('Failed to delete doctor', 'error');
        }
    }
}

// ==================== Medical Records ====================

async function loadMedicalRecords() {
    try {
        appState.medicalRecords = await apiClient.getAllMedicalRecords();
        displayMedicalRecords(appState.medicalRecords);
    } catch (error) {
        console.error('Failed to load medical records:', error);
        showToast('Failed to load medical records', 'error');
    }
}

function displayMedicalRecords(records) {
    const tbody = document.getElementById('medicalList');
    
    if (records.length === 0) {
        tbody.innerHTML = '<tr class="loading"><td colspan="6">No medical records found</td></tr>';
        return;
    }

    tbody.innerHTML = records.map(record => `
        <tr>
            <td>${record.medicalRecordId || '-'}</td>
            <td>${record.patientName || 'Unknown'}</td>
            <td>${formatDate(record.recordDate)}</td>
            <td>${record.diagnosis || '-'}</td>
            <td>${(record.notes || '').substring(0, 50)}...</td>
            <td>
                <button class="btn btn-edit" onclick="editMedicalRecord(${record.medicalRecordId})">Edit</button>
                <button class="btn btn-danger" onclick="deleteMedicalRecordConfirm(${record.medicalRecordId})">Delete</button>
            </td>
        </tr>
    `).join('');
}

function openMedicalRecordModal() {
    appState.editingMedicalId = null;
    document.getElementById('medicalId').value = '';
    document.getElementById('medicalForm').reset();
    populateMedicalPatientSelect();
    openModal('medicalModal');
}

async function populateMedicalPatientSelect() {
    try {
        const patientSelect = document.getElementById('medicalPatient');
        if (patientSelect && appState.patients.length > 0) {
            patientSelect.innerHTML = '<option value="">Select a patient...</option>' +
                appState.patients.map(p => 
                    `<option value="${p.patientId}">${p.firstName} ${p.lastName}</option>`
                ).join('');
        }
    } catch (error) {
        console.error('Failed to populate medical patient select:', error);
    }
}

async function editMedicalRecord(recordId) {
    try {
        const record = await apiClient.getMedicalRecordById(recordId);
        appState.editingMedicalId = recordId;
        document.getElementById('medicalId').value = record.medicalRecordId;
        document.getElementById('medicalPatient').value = record.patientId;
        document.getElementById('medicalRecordDate').value = record.recordDate;
        document.getElementById('medicalDiagnosis').value = record.diagnosis || '';
        document.getElementById('medicalNotes').value = record.notes || '';
        openModal('medicalModal');
    } catch (error) {
        showToast('Failed to load medical record details', 'error');
    }
}

async function submitMedicalRecordForm(e) {
    e.preventDefault();
    
    const recordData = {
        patientId: parseInt(document.getElementById('medicalPatient').value),
        recordDate: document.getElementById('medicalRecordDate').value,
        diagnosis: document.getElementById('medicalDiagnosis').value,
        notes: document.getElementById('medicalNotes').value
    };

    try {
        if (appState.editingMedicalId) {
            await apiClient.updateMedicalRecord(appState.editingMedicalId, recordData);
            showToast('Medical record updated successfully', 'success');
        } else {
            await apiClient.createMedicalRecord(recordData);
            showToast('Medical record created successfully', 'success');
        }
        closeModal('medicalModal');
        loadMedicalRecords();
    } catch (error) {
        showToast('Failed to save medical record', 'error');
        console.error(error);
    }
}

async function deleteMedicalRecordConfirm(recordId) {
    if (confirm('Are you sure you want to delete this medical record?')) {
        try {
            await apiClient.deleteMedicalRecord(recordId);
            showToast('Medical record deleted successfully', 'success');
            loadMedicalRecords();
        } catch (error) {
            showToast('Failed to delete medical record', 'error');
        }
    }
}

// ==================== Utility Functions ====================

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('show');
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('show');
    }
}

function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast show ${type}`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

function formatDate(dateString) {
    if (!dateString) return '-';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
}

function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '-';
    const date = new Date(dateTimeString);
    return date.toLocaleString('en-US', { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function getStatusBadgeClass(status) {
    switch (status) {
        case 'COMPLETED': return 'badge-success';
        case 'CANCELLED': return 'badge-danger';
        case 'SCHEDULED': return 'badge-warning';
        default: return 'badge-warning';
    }
}
