package client.info;

import DB.Connect;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Patient;
import sample.Controller;
import sample.Main;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Info implements Initializable {
    private Connect connect;
    private int patientId = 0;

    @FXML
    public Button save;

    @FXML
    public Label nameLabel;

    @FXML
    public Label surnameLabel;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public Label patronymicLabel;

    @FXML
    public TextField patronymic;

    @FXML
    public Label dateOfBirthdayLabel;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label allergiesLabel;

    @FXML
    public DatePicker dateOfBirthday;

    @FXML
    public TextField phone;

    @FXML
    public TextField email;

    @FXML
    public TextField address;

    @FXML
    public TextArea allergies;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Patient patient = Client.getPatient();

        patientId = patient.getId();
        name.setText(patient.getName());
        surname.setText(patient.getSurname());
        patronymic.setText(patient.getPatronymic());
        dateOfBirthday.setValue(patient.getDateOfBirth());
        phone.setText(String.valueOf(patient.getPhone()));
        email.setText(patient.getEmail());
        allergies.setText(patient.getAllergies());
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String err = validate();
        if (err == null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                java.util.Date date =
                        java.util.Date.from(dateOfBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                if (patientId != 0) {
                    statement.execute("update public.patient set surname ='" + surname.getText() +"'," +
                            " name ='" + name.getText() + "', patronymic = '" + patronymic.getText() + "', " +
                            "\"dateOfBirth\" = '" + sqlDate + "', phone = " + phone.getText() +
                            ", email = '"+ email.getText() + "', address = '" + address.getText() +
                            "', allergies = '" + allergies.getText() + "' where patient.id =" + patientId);
                } else {
                    statement.execute("insert into  public.patient (surname, name, patronymic, \"dateOfBirth\", phone, email, address, allergies, user_id) " +
                            "values ('" + surname.getText() + "', '" + name.getText() + "', '" + patronymic.getText() + "', '" +
                            sqlDate + "', " + phone.getText() + ", '" + email.getText() + "', '" + address.getText() + "', '" +
                            allergies.getText() + "', " + Controller.getUserFromCache().getId() + ")");
                }

                Client.updatePatientToCache();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    private String validate() {
        if (name.getText().isEmpty()) {
            return "Обязательное поле 'Имя' пусто";
        }
        if (surname.getText().isEmpty()) {
            return "Обязательное поле 'Фамилия' пусто";
        }
        if (phone.getText().isEmpty()) {
            return "Обязательное поле 'Телефон' пусто";
        }
        if (dateOfBirthday.getValue() == null) {
            return "Обязательное поле 'Дата рождения' пусто";
        }
        return null;
    }
}
