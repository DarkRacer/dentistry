package owner.doctor;

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
import model.dto.DoctorDto;
import model.enumeration.PaymentStatus;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class Doctor implements Initializable {
    private Connect connect;
    private ObservableList<DoctorDto> doctors = FXCollections.observableArrayList();
    private static DoctorDto selectedDoctor;

    @FXML
    public TableView<DoctorDto> table;

    @FXML
    public TableColumn<DoctorDto, String> nameColumn;

    @FXML
    public TableColumn<DoctorDto, String> specColumn;

    @FXML
    public TableColumn<DoctorDto, String> phoneColumn;

    @FXML
    public TableColumn<DoctorDto, String> emailColumn;

    @FXML
    public Button create;

    @FXML
    public Button change;

    @FXML
    public Button services;

    @FXML
    public Button delete;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updateDoctors();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        Stage stage = (Stage) create.getScene().getWindow();
        stage.close();

        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("create/create.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добавление врача");
        Stage.setScene(new Scene(root, 342, 435));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void change(ActionEvent actionEvent) {
        selectedDoctor = table.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            Stage stage = (Stage) change.getScene().getWindow();
            stage.close();

            Stage Stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("change/change.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage.setTitle("Изменение информации о враче");
            Stage.setScene(new Scene(root, 335, 293));
            Stage.setResizable(false);
            Stage.centerOnScreen();
            Stage.show();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите врача для изменения информации");
        }
    }

    @FXML
    public void services(ActionEvent actionEvent) {
        selectedDoctor = table.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            Stage stage = (Stage) change.getScene().getWindow();
            stage.close();

            Stage Stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("services/service.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage.setTitle("Услуги врача");
            Stage.setScene(new Scene(root, 600, 554));
            Stage.setResizable(false);
            Stage.centerOnScreen();
            Stage.show();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите врача для изменения информации");
        }
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        selectedDoctor = table.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                final ResultSet resultSet;

                resultSet = statement.executeQuery("select doc.user_id" +
                        " from public.doctor doc " +
                        "where doc.id = " + selectedDoctor.getId());

                int userId = 0;
                while (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }

                Statement statement1 = connect.getConnection().createStatement();
                statement1.execute("delete from doctor where id = " + selectedDoctor.getId());

                Statement statement2 = connect.getConnection().createStatement();
                statement2.execute("delete from \"user\" where id = " + userId);

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Врач уволен");
                updateDoctors();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите врача для увольнения");
        }
    }

    private void updateDoctors() {
        doctors.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet;

                resultSet = statement.executeQuery("select doc.id, CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) as doctor, " +
                        "doc.specialization, doc.phone, doc.email " +
                        "from public.doctor doc");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (int id, String fio, String specialization, String phone, String email){
        doctors.add(new DoctorDto(id, fio, specialization, phone, email));

        nameColumn.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("fio"));
        specColumn.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("specialization"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("email"));

        table.setItems(doctors);
    }

    public static DoctorDto getSelectedDoctor() {
        return selectedDoctor;
    }
}
