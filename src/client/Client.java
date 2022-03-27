package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Client {
    @FXML
    public Button help;

    @FXML
    public Button info;

    @FXML
    public Button pay;

    @FXML
    public Button see;

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
            root = FXMLLoader.load(getClass().getResource("/pay/pay.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Оплата услуг");
        Stage.setScene(new Scene(root, 600, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void see(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/see/see.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Посмотреть услуги и врачей");
        Stage.setScene(new Scene(root, 600, 400));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }
}
