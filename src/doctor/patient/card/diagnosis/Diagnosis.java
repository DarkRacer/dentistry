package doctor.patient.card.diagnosis;

import DB.Connect;
import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.PatientDiagnosisDto;
import model.dto.PatientDto;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Diagnosis implements Initializable {
    private Connect connect;
    private PatientDto patientDto;
    private ObservableList<PatientDiagnosisDto> patientDiagnosisDtos = FXCollections.observableArrayList();
    private ObservableList<model.Diagnosis> diagnoses = FXCollections.observableArrayList();

    @FXML
    public Label diagnosisLabel;

    @FXML
    public TableView<PatientDiagnosisDto> patientDiagnos;

    @FXML
    public TableColumn<PatientDiagnosisDto, String> name;

    @FXML
    public TableColumn<PatientDiagnosisDto, String> description;

    @FXML
    public TableColumn<PatientDiagnosisDto, String> actual;

    @FXML
    public Button complete;

    @FXML
    public TableView<model.Diagnosis> listDiagnosis;

    @FXML
    public TableColumn<model.Diagnosis, String> lname;

    @FXML
    public TableColumn<model.Diagnosis, String> ldescription;

    @FXML
    public Button assign;

    @FXML
    public Label listDiagnosisLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        patientDto = Doctor.getPatientForDoctor();
        initializePatientDiagnosis();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select d.id," +
                    " d.name," +
                    " d.description" +
                    " from diagnosis d");

            while (resultSet.next()) {
                setTableDiagnos(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void complete(ActionEvent actionEvent) {
        if(patientDiagnos.getSelectionModel().getSelectedItem() != null) {
            PatientDiagnosisDto patientDiagnosisDto = patientDiagnos.getSelectionModel().getSelectedItem();
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("update patient_diagnosis " +
                        " set \"isActual\" = false " +
                        " where patient_id = " + patientDiagnosisDto.getId() + " and diagnosis_id = "+ patientDiagnosisDto.getDiagnosId());

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Актуальность диагноза успешно изменена");
                initializePatientDiagnosis();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите диагноз пациента");
        }
    }

    @FXML
    public void assign(ActionEvent actionEvent) {
        if(listDiagnosis.getSelectionModel().getSelectedItem() != null) {
            model.Diagnosis diagnosis = listDiagnosis.getSelectionModel().getSelectedItem();
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("insert into patient_diagnosis (patient_id, diagnosis_id, \"isActual\") values (" + patientDto.getId() +", "+ diagnosis.getId() +", true)");

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Диагноз успешно назначен");
                initializePatientDiagnosis();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите диагноз");
        }
    }

    private void setTable (int id, int diagnosId, String diagnosis, String descriptionV, String actualV){
        patientDiagnosisDtos.add(new PatientDiagnosisDto(id, diagnosId, diagnosis, descriptionV, actualV));

        name.setCellValueFactory(new PropertyValueFactory<PatientDiagnosisDto, String>("diagnosis"));
        description.setCellValueFactory(new PropertyValueFactory<PatientDiagnosisDto, String>("description"));
        actual.setCellValueFactory(new PropertyValueFactory<PatientDiagnosisDto, String>("actual"));

        patientDiagnos.setItems(patientDiagnosisDtos);
    }

    private void setTableDiagnos (int id, String diagnosis, String descriptionV){
        diagnoses.add(new model.Diagnosis(id, diagnosis, descriptionV));

        lname.setCellValueFactory(new PropertyValueFactory<model.Diagnosis, String>("name"));
        ldescription.setCellValueFactory(new PropertyValueFactory<model.Diagnosis, String>("description"));

        listDiagnosis.setItems(diagnoses);
    }

    private void initializePatientDiagnosis() {
        patientDiagnosisDtos.clear();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select p.id," +
                    " d.id," +
                    " d.name," +
                    " d.description," +
                    " pd.\"isActual\"" +
                    " from patient p" +
                    " left join patient_diagnosis pd on pd.patient_id = p.id" +
                    " left join diagnosis d on d.id = pd.diagnosis_id" +
                    " where p.id =" + patientDto.getId());

            while (resultSet.next()) {
                String actualString = "Вылечен";
                if(resultSet.getBoolean(5)) {
                    actualString = "Не вылечен";
                }
                setTable(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4), actualString);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
