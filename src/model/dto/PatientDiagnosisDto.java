package model.dto;

public class PatientDiagnosisDto {
    private int id;
    private int diagnosId;
    private String diagnosis;
    private String description;
    private String actual;

    public PatientDiagnosisDto() {
    }

    public PatientDiagnosisDto(int id, int diagnosId, String diagnosis, String description, String actual) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.diagnosId = diagnosId;
        this.description = description;
        this.actual = actual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public int getDiagnosId() {
        return diagnosId;
    }

    public void setDiagnosId(int diagnosId) {
        this.diagnosId = diagnosId;
    }
}
