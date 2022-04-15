package model;

public class Card {
    private int id;
    private String teeth;
    private String allergies;
    private int patientId;

    public Card() {
    }

    public Card(int id, String teeth, String allergies, int patientId) {
        this.id = id;
        this.teeth = teeth;
        this.allergies = allergies;
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   public String getTeeth() {
        return teeth;
   }

   public void setTeeth(String teeth) {
        this.teeth = teeth;
   }

   public int getPatientId() {
        return patientId;
   }

   public void setPatientId(int patientId) {
        this.patientId = patientId;
   }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
