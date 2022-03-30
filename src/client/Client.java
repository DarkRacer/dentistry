package client;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Patient;
import sample.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Client implements Initializable {
    private static Connect connect;
    private static Patient patient;

    @FXML
    public Button help;

    @FXML
    public Button info;

    @FXML
    public Button pay;

    @FXML
    public Button see;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updatePatientToCache();
    }

    @FXML
    public void help(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("help/help.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Обратиться за помощью");
        Stage.setScene(new Scene(root, 600, 307));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void info(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("info/info.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Заполнить свои данные");
        Stage.setScene(new Scene(root, 325, 504));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void pay(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("pay/pay.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Оплата услуг");
        Stage.setScene(new Scene(root, 600, 292));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void see(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("see/see.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Посмотреть услуги и врачей");
        Stage.setScene(new Scene(root, 738, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    public static void updatePatientToCache() {
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from public.patient where patient.user_id =" + Controller.getUserFromCache().getId());

            while (resultSet.next()) {
                patient = new Patient(resultSet.getInt("id"), resultSet.getString("surname"),
                        resultSet.getString("name"), resultSet.getString("patronymic"),
                        LocalDate.from(resultSet.getDate("dateOfBirth").toLocalDate()),
                        resultSet.getInt("phone"), resultSet.getString("email"),
                        resultSet.getString("address"), resultSet.getString("allergies"),
                        resultSet.getInt("user_id"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Patient getPatient() {
        return patient;
    }
}
