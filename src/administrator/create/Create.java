package administrator.create;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.signup.Signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Objects;

public class Create {
    private Connect connect;

    @FXML
    public TextField password1;

    @FXML
    public Label passwordLabel1;

    @FXML
    public Button save;

    @FXML
    public TextField name;

    @FXML
    public Label nameLabel;

    @FXML
    public Label surnameLabel;

    @FXML
    public TextField surname;

    @FXML
    public TextField patronymic;

    @FXML
    public Label patronymicLabel;

    @FXML
    public Label dateOfBirthdayLabel;

    @FXML
    public Label phoneLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label allergiesLabel;

    @FXML
    public DatePicker dateOfBirthday;

    @FXML
    public TextField phone;

    @FXML
    public TextField email;

    @FXML
    public TextField address;

    @FXML
    public TextArea allergies;

    @FXML
    public Label loginLabel;

    @FXML
    public TextField login;

    @FXML
    public Label passwordLabel;

    @FXML
    public TextField password;

    @FXML
    public void save(ActionEvent actionEvent) {
        String err;
        if (!Signup.isUniqueLogin(login.getText())) {
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


                Statement statement1 = connect.getConnection().createStatement();
                final ResultSet resultSet = statement1.executeQuery("select u.id from public.\"user\" u where login = '" + login.getText() + "'");

                int userId = 0;
                while (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }

                Statement statement2 = connect.getConnection().createStatement();
                java.util.Date date =
                        java.util.Date.from(dateOfBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                statement2.execute("insert into  public.patient (surname, name, patronymic, \"dateOfBirth\", phone, email, address, allergies, user_id) " +
                        "values ('" + surname.getText() + "', '" + name.getText() + "', '" + patronymic.getText() + "', '" +
                        sqlDate + "', '" + phone.getText() + "', '" + email.getText() + "', '" + address.getText() + "', '" +
                        allergies.getText() + "', " + userId + ")");


                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Пользователь создан");
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
        } else if (surname.getText().isEmpty()) {
            return "Обязательное поле 'Фамилия' пусто";
        } else if (phone.getText().isEmpty()) {
            return "Обязательное поле 'Телефон' пусто";
        } else if (dateOfBirthday.getValue() == null) {
            return "Обязательное поле 'Дата рождения' пусто";
        } else if (login.getText().isEmpty()) {
            return "Обязательное поле 'Логин' пусто";
        } else if (password.getText().isEmpty()) {
            return "Обязательное поле 'Логин' пусто";
        } else if (password1.getText().isEmpty()) {
            return "Повторите пароль";
        } else if (!Objects.equals(password.getText(), password1.getText())) {
            return "Пароли не совпадают";
        }
        return null;
    }
}
