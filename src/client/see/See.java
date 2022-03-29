package client.see;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.User;
import model.enumeration.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;

import static sample.Main.alert;

public class See {
    private Connect connect = null;
    @FXML
    public TableView<ServicesAndDoctorModel> table;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> doctorColumn;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> serviceColumn;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> description;

    @FXML
    public TableColumn<ServicesAndDoctorModel, Integer> price;

    @FXML
    public Label serviceLabel;

    @FXML
    public ComboBox<String> service;

    @FXML
    public Label doctorLabel;

    @FXML
    public ComboBox<String> doctor;

    @FXML
    public Button find;

    @FXML
    public void find(ActionEvent actionEvent) {
        if (service.getValue() != null && !service.getValue().isEmpty()) {
            if (doctor.getValue() != null && !doctor.getValue().isEmpty()) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    final ResultSet resultSet = statement.executeQuery("select * from public.\"user\" where \"user\".login ='" + login.getText() + "'");

                    boolean found = false;
                    while (resultSet.next()) {

                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
                }
            } else {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    final ResultSet resultSet = statement.executeQuery("select * from public.\"user\" where \"user\".login ='" + login.getText() + "'");

                    boolean found = false;
                    while (resultSet.next()) {

                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
                }
            }
        } else if (doctor.getValue() != null && !doctor.getValue().isEmpty()) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                final ResultSet resultSet = statement.executeQuery("select * from public.\"user\" where \"user\".login ='" + login.getText() + "'");

                boolean found = false;
                while (resultSet.next()) {

                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
            }
        }
    }
}
