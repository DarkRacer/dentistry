package administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.Controller.setUserFromCache;

public class Administrator {
    @FXML
    public Button record;

    @FXML
    public Button pay;

    @FXML
    public Button create;

    @FXML
    public Button see;

    @FXML
    public Button schedule;

    @FXML
    public Button changePassword;

    @FXML
    public Button exit;

    @FXML
    public void record(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("complaints/complaints.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Жалобы и назначение записи");
        Stage.setScene(new Scene(root, 600, 513));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void pay(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("payment/payment.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Проверка оплат");
        Stage.setScene(new Scene(root, 676, 327));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("create/create.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Создание пользователя");
        Stage.setScene(new Scene(root, 370, 601));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void see(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/client/see/see.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Посмотреть услуги и врачей");
        Stage.setScene(new Scene(root, 738, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void schedule(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("schedule/schedule.fxml"));
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

    @FXML
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
        setUserFromCache(null);
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Стоматология");
        Stage.setScene(new Scene(root, 338, 174));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }
}
