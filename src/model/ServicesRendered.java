package model;

public class ServicesRendered {
    private int     idDoc;
    private int     idServices;

    public ServicesRendered() {
    }

    public ServicesRendered(int idDoc, int idServices) {
        this.idDoc = idDoc;
        this.idServices = idServices;
    }

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }

    public int getIdServices() {
        return idServices;
    }

    public void setIdServices(int idServices) {
        this.idServices = idServices;
    }
}
