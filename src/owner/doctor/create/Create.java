package owner.doctor.create;

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

import static sample.signup.Signup.isUniqueLogin;

public class Create {
    private Connect connect;

    @FXML
    public Label nameLabel;

    @FXML
    public Label surnameLabel;

    @FXML
    public Label patronymicLabel;

    @FXML
    public Label specializationLabel;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Button save;

    @FXML
    public Label loginLabel;

    @FXML
    public TextField login;

    @FXML
    public Label passwordLabel;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public TextField patronymic;

    @FXML
    public TextField specialization;

    @FXML
    public TextField phone;

    @FXML
    public TextField email;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField password1;

    @FXML
    public Label passwordLabel1;

    @FXML
    public void save(ActionEvent actionEvent) {
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
                        "values ('" + login.getText() + "', '" + password.getText() + "', 2)");

                Statement statement1 = connect.getConnection().createStatement();
                final ResultSet resultSet = statement1.executeQuery("select u.id from public.\"user\" u where login = '" + login.getText() + "'");

                int userId = 0;
                while (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }

                Statement statement2 = connect.getConnection().createStatement();
                statement2.execute("insert into  public.doctor (surname, name, patronymic, phone, email, specialization, user_id) values (" +
                        "' " + surname.getText() + "', '" + name.getText() + "', '" + patronymic.getText() + "', " +
                        Integer.parseInt(phone.getText())+ ", '" + email.getText() + "', '" + specialization.getText() + "', " +userId +")");

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Врач добавлен");
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    private String validate() {
        if (name.getText().isEmpty()) {
            return "Обязательное поле 'Имя' пусто";
        } if (surname.getText().isEmpty()) {
            return "Обязательное поле 'Фамилия' пусто";
        } if (phone.getText().isEmpty()) {
            return "Обязательное поле 'Телефон' пусто";
        } else if (specialization.getText().isEmpty()) {
            return "Обязательное поле 'Специализация' пусто";
        } else if (login.getText().isEmpty()) {
            return "Обязательное поле 'Логин' пусто";
        } else if (password.getText().isEmpty()) {
            return "Укажите пароль";
        } else if (password1.getText().isEmpty()) {
            return "Повторите пароль";
        } else if (!password.getText().equals(password1.getText())) {
            return "Пароли не совпадают";
        }
        return null;
    }
}
