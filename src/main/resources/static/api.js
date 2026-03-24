// API Configuration
const API_BASE_URL = 'http://localhost:8080';

class APIClient {
    constructor(baseUrl = API_BASE_URL) {
        this.baseUrl = baseUrl;
    }

    async request(endpoint, method = 'GET', data = null) {
        const url = `${this.baseUrl}${endpoint}`;
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        if (data && (method === 'POST' || method === 'PUT')) {
            options.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(url, options);
            
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error(`API error: ${endpoint}`, error);
            throw error;
        }
    }

    // Patient APIs
    async getAllPatients() {
        const response = await this.request('/api/patients');
        return response.data || [];
    }

    async getPatientById(id) {
        const response = await this.request(`/api/patients/${id}`);
        return response.data;
    }

    async createPatient(patientData) {
        const response = await this.request('/api/patients', 'POST', patientData);
        return response.data;
    }

    async updatePatient(id, patientData) {
        const response = await this.request(`/api/patients/${id}`, 'PUT', patientData);
        return response.data;
    }

    async deletePatient(id) {
        return await this.request(`/api/patients/${id}`, 'DELETE');
    }

    async searchPatients(name) {
        const response = await this.request(`/api/patients/search?name=${encodeURIComponent(name)}`);
        return response.data || [];
    }

    // Appointment APIs
    async getAllAppointments() {
        const response = await this.request('/api/appointments');
        return response.data || [];
    }

    async getAppointmentById(id) {
        const response = await this.request(`/api/appointments/${id}`);
        return response.data;
    }

    async createAppointment(appointmentData) {
        const response = await this.request('/api/appointments', 'POST', appointmentData);
        return response.data;
    }

    async updateAppointment(id, appointmentData) {
        const response = await this.request(`/api/appointments/${id}`, 'PUT', appointmentData);
        return response.data;
    }

    async deleteAppointment(id) {
        return await this.request(`/api/appointments/${id}`, 'DELETE');
    }

    async getAppointmentsByPatient(patientId) {
        const response = await this.request(`/api/appointments/patient/${patientId}`);
        return response.data || [];
    }

    async getAppointmentsByDoctor(doctorId) {
        const response = await this.request(`/api/appointments/doctor/${doctorId}`);
        return response.data || [];
    }

    // Doctor APIs
    async getAllDoctors() {
        const response = await this.request('/api/doctors');
        return response.data || [];
    }

    async getDoctorById(id) {
        const response = await this.request(`/api/doctors/${id}`);
        return response.data;
    }

    async createDoctor(doctorData) {
        const response = await this.request('/api/doctors', 'POST', doctorData);
        return response.data;
    }

    async updateDoctor(id, doctorData) {
        const response = await this.request(`/api/doctors/${id}`, 'PUT', doctorData);
        return response.data;
    }

    async deleteDoctor(id) {
        return await this.request(`/api/doctors/${id}`, 'DELETE');
    }

    // Medical Record APIs
    async getAllMedicalRecords() {
        const response = await this.request('/api/medical-records');
        return response.data || [];
    }

    async getMedicalRecordById(id) {
        const response = await this.request(`/api/medical-records/${id}`);
        return response.data;
    }

    async createMedicalRecord(recordData) {
        const response = await this.request('/api/medical-records', 'POST', recordData);
        return response.data;
    }

    async updateMedicalRecord(id, recordData) {
        const response = await this.request(`/api/medical-records/${id}`, 'PUT', recordData);
        return response.data;
    }

    async deleteMedicalRecord(id) {
        return await this.request(`/api/medical-records/${id}`, 'DELETE');
    }

    async getMedicalRecordsByPatient(patientId) {
        const response = await this.request(`/api/medical-records/patient/${patientId}`);
        return response.data || [];
    }

    // Disease APIs (if available)
    async getAllDiseases() {
        try {
            const response = await this.request('/api/diseases');
            return response.data || [];
        } catch {
            return [];
        }
    }

    // Dashboard APIs
    async getDashboardStats() {
        try {
            const response = await this.request('/api/dashboard/stats');
            return response.data || {};
        } catch {
            return {};
        }
    }

    // Health check
    async checkHealth() {
        try {
            const response = await this.request('/api/health');
            return response && response.status === 'UP';
        } catch {
            return false;
        }
    }
}

// Create global API client instance
const apiClient = new APIClient();
