package model;

public class PatientDiagnosis {
    private int patientId;
    private int diagnosisId;
    private boolean isActual;

    public PatientDiagnosis() {
    }

    public PatientDiagnosis(int patientId, int diagnosisId, boolean isActual) {
        this.patientId = patientId;
        this.diagnosisId = diagnosisId;
        this.isActual = isActual;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }
}
