package doctor;

import DB.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.PatientDto;
import model.dto.RecordDto;
import sample.Controller;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Doctor implements Initializable {
    private static PatientDto patientDto;
    private static model.Doctor doctor;
    private Connect connect = null;
    private ObservableList<RecordDto> records = FXCollections.observableArrayList();
    private static RecordDto recordDto;

    @FXML
    public TableView<RecordDto> table;

    @FXML
    public TableColumn<RecordDto, String> patientColumn;

    @FXML
    public TableColumn<RecordDto, LocalDate> dateColumn;

    @FXML
    public TableColumn<RecordDto, LocalTime> timeColumn;

    @FXML
    public Button info;

    @FXML
    public Button payment;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updateDoctorToCache();
        records.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select CONCAT(p.patronymic, ' ', p.Name, ' ', p.Surname) as patient," +
                    " sch.date," +
                    " sch.time," +
                    " sch.id" +
                    " from public.schedule sch" +
                    " left join public.patient p on p.id = sch.\"idPatient\"" +
                    " left join public.payment pay on pay.\"idRecord\" = sch.id" +
                    " where sch.\"idDoc\" = " + doctor.getId());

            Statement statement1 = connect.getConnection().createStatement();

            final ResultSet resultSet1 = statement1.executeQuery("select pay.\"idRecord\" from public.payment pay");

            List<RecordDto> recordDtos = new ArrayList<>();
            List<RecordDto> dublicateRecordDtos = new ArrayList<>();
            while (resultSet.next()) {
                recordDtos.add(new RecordDto(resultSet.getInt(4), resultSet.getString(1), LocalDate.from(resultSet.getDate(2).toLocalDate()),
                        LocalTime.from(resultSet.getTimestamp(3).toLocalDateTime().toLocalTime())));
            }

            while (resultSet1.next()) {
                for (RecordDto recordDto : recordDtos) {
                    if (recordDto.getId() == resultSet1.getInt(1)) {
                        dublicateRecordDtos.add(recordDto);
                    }
                }
            }
            recordDtos.removeAll(dublicateRecordDtos);

            for (RecordDto recordDto : recordDtos) {
                setTable(recordDto.getFio(), recordDto.getDate(), recordDto.getTime(), recordDto.getId());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void see(ActionEvent actionEvent) {
        RecordDto recordDto = table.getSelectionModel().getSelectedItem();

        if (recordDto != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                final ResultSet resultSet = statement.executeQuery("select CONCAT(p.patronymic, ' ', p.Name, ' ', p.Surname) as patient," +
                        " p.id," +
                        " p.\"dateOfBirth\"," +
                        " p.phone," +
                        " p.email," +
                        " p.allergies," +
                        " p.address" +
                        " from public.schedule sch" +
                        " left join public.patient p on p.id = sch.\"idPatient\"" +
                        " where sch.id = " + recordDto.getId());

                while (resultSet.next()) {
                    patientDto = new PatientDto(resultSet.getInt(2), resultSet.getString(1),
                            LocalDate.from(resultSet.getDate(3).toLocalDate()), resultSet.getInt(4),
                            resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                }

                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("patient/patient.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Информация о пользователе");
                Stage.setScene(new Scene(root, 417, 413));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите пациента");
        }
    }

    @FXML
    public void payment(ActionEvent actionEvent) {
        recordDto = table.getSelectionModel().getSelectedItem();

        if (recordDto != null) {
                Stage stage = (Stage) payment.getScene().getWindow();
                stage.close();

                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("payment/payment.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Выставить счёт");
                Stage.setScene(new Scene(root, 600, 559));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите пациента");
        }
    }

    public void updateDoctorToCache() {
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from public.doctor where doctor.user_id =" + Controller.getUserFromCache().getId());

            while (resultSet.next()) {
                doctor = new model.Doctor(resultSet.getInt("id"), resultSet.getString("surname"),
                        resultSet.getString("name"), resultSet.getString("patronymic"),
                        resultSet.getInt("phone"), resultSet.getString("email"),
                        resultSet.getString("specialization"),
                        resultSet.getInt("user_id"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (String patient, LocalDate date, LocalTime time, int id){
        records.add(new RecordDto(id, patient, date, time));

        patientColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, String>("fio"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalTime>("time"));

        table.setItems(records);
    }

    public static PatientDto getPatientForDoctor() {
        return patientDto;
    }

    public static RecordDto getRecord() {
        return recordDto;
    }

    public static model.Doctor getDoctor() {
        return doctor;
    }
}
