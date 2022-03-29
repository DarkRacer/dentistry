package client.see;

public class ServicesAndDoctorModel {
    private String  doctor;
    private String  service;
    private String  description;
    private int     price;

    public ServicesAndDoctorModel() {
    }

    public ServicesAndDoctorModel(String doctor, String service, String description, int price) {
        this.doctor = doctor;
        this.service = service;
        this.description = description;
        this.price = price;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
