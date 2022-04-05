package owner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Owner {
    @FXML
    public Button doctor;

    @FXML
    public Button admin;

    @FXML
    public Button schedule;

    @FXML
    public Button payment;

    @FXML
    public Button complaints;

    @FXML
    public Button services;

    @FXML
    public Button changePassword;

    @FXML
    public void doctor(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("doctor/doctor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Врачи");
        Stage.setScene(new Scene(root, 810, 338));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void admin(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("admin/admin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Администраторы");
        Stage.setScene(new Scene(root, 261, 303));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void schedule(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/administrator/schedule/schedule.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("График");
        Stage.setScene(new Scene(root, 600, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void payment(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("payment/payment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Оплаты");
        Stage.setScene(new Scene(root, 671, 301));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void complaints(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/administrator/complaints/complaints.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Жалобы");
        Stage.setScene(new Scene(root, 600, 513));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void services(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("services/services.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Услуги");
        Stage.setScene(new Scene(root, 630, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void changePassword(ActionEvent actionEvent) {
        Stage stage = (Stage) changePassword.getScene().getWindow();
        stage.close();

        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/password/password.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Сменить пароль");
        Stage.setScene(new Scene(root, 348, 165));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }
}
