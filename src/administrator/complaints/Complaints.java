package administrator.complaints;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.ComplaintsDto;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Complaints implements Initializable {
    private Connect connect = null;
    private ObservableList<ComplaintsDto> patientWithComplaints = FXCollections.observableArrayList();
    private ObservableList<ComplaintsDto> docWithServices = FXCollections.observableArrayList();

    @FXML
    public TableView<ComplaintsDto> tablePatient;

    @FXML
    public TableColumn<ComplaintsDto, String> patientColumn;

    @FXML
    public TableColumn<ComplaintsDto, String> complaintsColumn;

    @FXML
    public TableView<ComplaintsDto> tableDoc;

    @FXML
    public TableColumn<ComplaintsDto, String>  docColumn;

    @FXML
    public TableColumn<ComplaintsDto, String>  servicesColumn;

    @FXML
    public Label dateLabel;

    @FXML
    public DatePicker date;

    @FXML
    public Label timeLabel;

    @FXML
    public TextField time;

    @FXML
    public Button save;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        patientWithComplaints.clear();
        docWithServices.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select pat.id, CONCAT(pat.surname, ' ', pat.Name, ' ', pat.patronymic) as patient, " +
                    "  c.complaints," +
                    " c.id" +
                    "  from public.patient pat" +
                    "  left join public.call_log c on c.\"idPatient\" = pat.id" +
                    "  where c.is_new = true");

            while (resultSet.next()) {
                setTablePatientWithComplaints(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
            }

            Statement statement1 = connect.getConnection().createStatement();
            final ResultSet resultSet1 = statement1.executeQuery("select doc.id, CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) as doctor, string_agg(s.name, ',')" +
                    " from public.doctor doc" +
                    " left join public.services_rendered sr on doc.id = sr.\"idDoc\"" +
                    " left join public.services s on sr.\"idServices\" = s.id" +
                    " group by doc.id");

            while (resultSet1.next()) {
                setTableDocWithServices(resultSet1.getInt(1), resultSet1.getString(2), resultSet1.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        ComplaintsDto patient = tablePatient.getSelectionModel().getSelectedItem();
        ComplaintsDto doctor = tableDoc.getSelectionModel().getSelectedItem();
        String err = validate(patient, doctor);
        if (err == null) {
            try {
                connect = new Connect();
                java.util.Date dateConvert =
                        java.util.Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(dateConvert.getTime());

                Statement statement = connect.getConnection().createStatement();
                statement.execute("insert into  public.schedule (date, time, \"idDoc\", \"idPatient\") " +
                        "values ('" + sqlDate + "', '" + time.getText() + "', " + doctor.getId() + ", " + patient.getId() + ")");

                //check from schedule for err
                Statement statement2 = connect.getConnection().createStatement();
                statement2.execute("update public.call_log  set is_new = false where id =" + patient.getComplId());

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Пациент успешно записан");
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("complaints.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Добро пожаловать");
                Stage.setScene(new Scene(root, 600, 513));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    private void setTablePatientWithComplaints (int id, String fio, String info, int complId){
        patientWithComplaints.add(new ComplaintsDto(id, fio, info, complId));

        patientColumn.setCellValueFactory(new PropertyValueFactory<ComplaintsDto, String>("fio"));
        complaintsColumn.setCellValueFactory(new PropertyValueFactory<ComplaintsDto, String>("info"));

        tablePatient.setItems(patientWithComplaints);
    }

    private void setTableDocWithServices (int id, String fio, String info){
        docWithServices.add(new ComplaintsDto(id, fio, info));

        docColumn.setCellValueFactory(new PropertyValueFactory<ComplaintsDto, String>("fio"));
        servicesColumn.setCellValueFactory(new PropertyValueFactory<ComplaintsDto, String>("info"));

        tableDoc.setItems(docWithServices);
    }

    private String validate (ComplaintsDto patient, ComplaintsDto doctor) {
        if (patient == null) {
            return "Выберите пациента";
        } else if (doctor == null) {
            return "Выберите доктора";
        } else if (date.getValue() == null) {
            return "Укажите дату приема";
        } else if (time.getText().isEmpty()) {
            return "Укажите время";
        }
        return null;
    }
}
