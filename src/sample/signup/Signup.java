package sample.signup;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;

public class Signup {
    private static Connect connect;

    @FXML
    public Label loginLabel;

    @FXML
    public TextField login;

    @FXML
    public Label passwordLabel;

    @FXML
    public Label password2Label;

    @FXML
    public Button create;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField password2;

    @FXML
    public void create(ActionEvent actionEvent) {
        String err;
        if (!isUniqueLogin(login.getText())) {
            err = "Такой логин уже существует";
        } else {
            err = validate();
        }
        if (err == null) {
            try {
                connect = new Connect();

                Statement statement = connect.getConnection().createStatement();
                statement.execute("insert into  public.\"user\" (login, password, type) " +
                        "values ('" + login.getText() + "', '" + password.getText() + "', 1)");


                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Пользователь создан");
                Stage stage = (Stage) create.getScene().getWindow();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }

    }

    private String validate() {
        if (password.getText().isEmpty()) {
            return "Укажите пароль";
        } else if (password2.getText().isEmpty()) {
            return "Повторите пароль";
        } else if (!password.getText().equals(password2.getText())) {
            return "Пароли не совпадают";
        }
        return null;
    }

    public static boolean isUniqueLogin(String login) {
        try {
            connect = new Connect();

            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select u.id from public.\"user\" u where login = '" + login + "'");

            while (resultSet.next()) {
                if (resultSet.getInt(1) != 0) {
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
