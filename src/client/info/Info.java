package client.info;

import DB.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Controller;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ResourceBundle;

public class Info implements Initializable {
    private Connect connect;
    private int patientId = 0;

    @FXML
    public Button save;

    @FXML
    public Label nameLabel;

    @FXML
    public Label surnameLabel;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public Label patronymicLabel;

    @FXML
    public TextField patronymic;

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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet = statement.executeQuery("select * from public.patient where patient.user_id =" + Controller.user.getId());

            while (resultSet.next()) {
                patientId = resultSet.getInt("id");
                name.setText(resultSet.getString("name"));
                surname.setText(resultSet.getString("surname"));
                patronymic.setText(resultSet.getString("patronymic"));
                dateOfBirthday.setValue(LocalDate.from(resultSet.getDate("dateOfBirth").toLocalDate()));
                phone.setText(resultSet.getString("phone"));
                email.setText(resultSet.getString("email"));
                allergies.setText(resultSet.getString("allergies"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String err = validate();
        if (err == null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                java.util.Date date =
                        java.util.Date.from(dateOfBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                if (patientId != 0) {
                    statement.execute("update public.patient set surname ='" + surname.getText() +"'," +
                            " name ='" + name.getText() + "', patronymic = '" + patronymic.getText() + "', " +
                            "\"dateOfBirth\" = '" + sqlDate + "', phone = " + phone.getText() +
                            ", email = '"+ email.getText() + "', address = '" + address.getText() +
                            "', allergies = '" + allergies.getText() + "' where patient.id =" + patientId);
                } else {
                    statement.execute("insert into  public.patient (surname, name, patronymic, \"dateOfBirth\", phone, email, address, allergies, user_id) " +
                            "values ('" + surname.getText() + "', '" + name.getText() + "', '" + patronymic.getText() + "', '" +
                            sqlDate + "', " + phone.getText() + ", '" + email.getText() + "', '" + address.getText() + "', '" +
                            allergies.getText() + "', " + Controller.user.getId() + ")");
                }

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
        }
        if (surname.getText().isEmpty()) {
            return "Обязательное поле 'Фамилия' пусто";
        }
        if (phone.getText().isEmpty()) {
            return "Обязательное поле 'Телефон' пусто";
        }
        if (dateOfBirthday.getValue() == null) {
            return "Обязательное поле 'Дата рождения' пусто";
        }
        return null;
    }
}
