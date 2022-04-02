package owner.doctor.change;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import owner.doctor.Doctor;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Change implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            connect = new Connect();

            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from public.doctor");

            while (resultSet.next()) {
                name.setText(resultSet.getString("name"));
                surname.setText(resultSet.getString("surname"));
                patronymic.setText(resultSet.getString("patronymic"));
                phone.setText(String.valueOf(resultSet.getInt("phone")));
                email.setText(resultSet.getString("email"));
                specialization.setText(resultSet.getString("specialization"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String err= validate();
        if (err == null) {
            try {
                connect = new Connect();

                Statement statement = connect.getConnection().createStatement();
                statement.execute("update public.doctor set name = '"+ name.getText() + "', surname = '" + surname.getText()
                        + "', patronymic = '" + patronymic.getText() + "', phone = "+ Integer.parseInt(phone.getText())
                        + " , email = '"+ email.getText() + "', specialization = '" + specialization.getText()
                        + "' where id = " + Doctor.getSelectedDoctor().getId());

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Информация о враче изменена");
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
        }
        return null;
    }
}
