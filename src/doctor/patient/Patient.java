package doctor.patient;

import DB.Connect;
import doctor.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.dto.PatientDto;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Patient implements Initializable {
    private PatientDto patientDto;
    private Connect connect;

    @FXML
    public Label complaintsLabel;

    @FXML
    public TextArea complaints;

    @FXML
    public Label fioLabel;

    @FXML
    public Label fio;

    @FXML
    public Label dateOfBirthLabel;

    @FXML
    public Label dateOfBirth;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label phone;

    @FXML
    public Label emailLabel;

    @FXML
    public Label email;

    @FXML
    public Label allergiesLabel;

    @FXML
    public TextArea allergies;

    @FXML
    public Button save;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        patientDto = Doctor.getPatientForDoctor();
        fio.setText(patientDto.getFio());
        dateOfBirth.setText(String.valueOf(patientDto.getDateOfBirth()));
        phone.setText(String.valueOf(patientDto.getPhone()));
        email.setText(patientDto.getEmail());
        allergies.setText(patientDto.getAllergies());

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select c.complaints, c.\"dateOfRequest\" from public.call_log c"+
                    " where c.\"idPatient\" = " + patientDto.getId());
            StringBuilder complaintsWithDate = new StringBuilder();

            while (resultSet.next()) {
                complaintsWithDate.append("Дата: ").append(LocalDate.from(resultSet.getDate(2).toLocalDate())).append("\n").append(resultSet.getString(1)).append("\n\n");
            }

            complaints.setText(complaintsWithDate.toString());
            complaints.setWrapText(true);
            complaints.setEditable(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            statement.execute("update patient set allergies ='" + allergies.getText() + "' where id = " + patientDto.getId());
            Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Информация успешно изменена");
            Stage stage = (Stage) save.getScene().getWindow();
            stage.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
