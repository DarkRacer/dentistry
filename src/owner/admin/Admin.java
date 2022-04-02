package owner.admin;

import DB.Connect;
import doctor.Doctor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Services;
import model.dto.PaymentDto;
import model.dto.UserDto;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    private Connect connect;
    private ObservableList<UserDto> admins = FXCollections.observableArrayList();
    private UserDto selectedAdmin;

    @FXML
    public TableView<UserDto> table;

    @FXML
    public TableColumn<UserDto, Integer> idColumn;

    @FXML
    public TableColumn<UserDto, String> loginColumn;

    @FXML
    public Button create;

    @FXML
    public Button delete;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        updateAdmins();
    }

    @FXML
    public void create(ActionEvent actionEvent) {
        Stage stage = (Stage) create.getScene().getWindow();
        stage.close();
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("create/create.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Создание администратора");
        Stage.setScene(new Scene(root, 341, 162));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        selectedAdmin = table.getSelectionModel().getSelectedItem();
        if (selectedAdmin != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from public.\"user\" where id =" + selectedAdmin.getId());
                updateAdmins();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите администратора");
        }
    }

    private void setTable (int id, String login){
        admins.add(new UserDto(id, login));

        idColumn.setCellValueFactory(new PropertyValueFactory<UserDto, Integer>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<UserDto, String>("login"));

        table.setItems(admins);
    }

    public void updateAdmins() {
        admins.clear();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select u.id, u.login\n" +
                    "from public.\"user\" u" +
                    " where u.type = 3");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
