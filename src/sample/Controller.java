package sample;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.enumeration.UserType;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;

import static sample.Main.alert;

public class Controller {
    private static Connect connect = null;
    private static User user;

    @FXML
    public TextField login;

    @FXML
    public Button signup;

    @FXML
    public Label passwordLabel;

    @FXML
    public Label loginLabel;

    @FXML
    public PasswordField password;

    @FXML
    public Button loginSystem;

    @FXML
    public void signup(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("signup/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Регистрация");
        Stage.setScene(new Scene(root, 341, 162));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void loginSystem(ActionEvent actionEvent) {
        if (!login.getText().isEmpty() && !password.getText().isEmpty()) {
            int i = 0,k = 0;

            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                final ResultSet resultSet = statement.executeQuery("select * from public.\"user\" where \"user\".login ='" + login.getText() + "'");

                boolean found = false;
                while (resultSet.next()) {
                    UserType userType;
                    if (Objects.equals(login.getText(), resultSet.getString("login")) &&
                                    Objects.equals(password.getText(), resultSet.getString("password"))) {

                        Statement statementUserType = connect.getConnection().createStatement();
                        final ResultSet resultSetUserType = statementUserType.executeQuery("select * from public.user_type where id ="
                                + resultSet.getInt("type"));
                        while (resultSetUserType.next()) {
                            userType = UserType.valueOf(resultSetUserType.getString("name"));
                            user = new User(resultSet.getInt("id"), resultSet.getString("login"),
                                    resultSet.getString("password"), userType);
                            found = true;
                        }
                    }
                    i++;
                }

                if (found) {
                    if (Objects.equals(user.getType().name(), UserType.CLIENT.name())) {
                        closeAndOpenUserWindow(user.getType().name().toLowerCase(Locale.ROOT), 600, 243);
                    } else if (Objects.equals(user.getType().name(), UserType.DOCTOR.name())) {
                        closeAndOpenUserWindow(user.getType().name().toLowerCase(Locale.ROOT), 600, 425);
                    } else if (Objects.equals(user.getType().name(), UserType.ADMINISTRATOR.name())) {
                        closeAndOpenUserWindow(user.getType().name().toLowerCase(Locale.ROOT), 445, 206);
                    } else if (Objects.equals(user.getType().name(), UserType.OWNER.name())) {
                        closeAndOpenUserWindow(user.getType().name().toLowerCase(Locale.ROOT), 401, 275);
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
            }
        } else {
            alert(Alert.AlertType.ERROR, "Ошибка", "Пустой логин или пароль");
        }
    }

    private void closeAndOpenUserWindow(String type, int width, int height) {
        Stage stage = (Stage) loginSystem.getScene().getWindow();
        stage.close();

        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/" + type + "/" + type + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Добро пожаловать");
        Stage.setScene(new Scene(root, width, height));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    public static User getUserFromCache() {
        return user;
    }

    public static void setUserFromCache(User newUser) {
        user = newUser;
    }

    public static void updateCacheUser() {
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select * from public.\"user\" where \"user\".id ='" + user.getId() + "'");

            boolean found = false;
            while (resultSet.next()) {
                UserType userType;

                    Statement statementUserType = connect.getConnection().createStatement();
                    final ResultSet resultSetUserType = statementUserType.executeQuery("select * from public.user_type where id ="
                            + resultSet.getInt("type"));
                    while (resultSetUserType.next()) {
                        userType = UserType.valueOf(resultSetUserType.getString("name"));
                        user = new User(resultSet.getInt("id"), resultSet.getString("login"),
                                resultSet.getString("password"), userType);
                    }
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert(Alert.AlertType.ERROR, "Ошибка", "Некорректный логин или пароль");
        }
    }
}
