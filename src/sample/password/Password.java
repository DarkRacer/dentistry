package sample.password;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sample.Controller;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Password {
    private Connect connect;

    @FXML
    public PasswordField old;

    @FXML
    public Label newLabel;

    @FXML
    public PasswordField newPassword;

    @FXML
    public Label passwordLabel;

    @FXML
    public PasswordField newPassword1;

    @FXML
    public Button save;

    @FXML
    public Label oldLabel;

    @FXML
    public void save(ActionEvent actionEvent) {
        String err =validate();
        if (err == null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("update public.\"user\" set password ='" + newPassword.getText() + "' where id =" + Controller.getUserFromCache().getId());

                Controller.updateCacheUser();
                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Пароль изменен");
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();

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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    private String validate() {
        if (old.getText().isEmpty()) {
            return "Введите старый пароль";
        } else if (newPassword.getText().isEmpty()) {
            return "Введите новый пароль";
        } else if (newPassword1.getText().isEmpty()) {
            return "Повторите новый пароль";
        } else if (!Objects.equals(old.getText(), Controller.getUserFromCache().getPassword())) {
            return "Старый пароль не совпадает";
        } else if (Objects.equals(newPassword.getText(), Controller.getUserFromCache().getPassword())) {
            return "Пароли не могут быть одинаковыми";
        } else if (!Objects.equals(newPassword.getText(), newPassword1.getText())) {
            return "Новые пароли не совпадают";
        }
        return null;
    }
}
