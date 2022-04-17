package owner.diagnosis;

import DB.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Services;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Diagnosis implements Initializable {
    private Connect connect;
    private ObservableList<model.Diagnosis> diagnoses = FXCollections.observableArrayList();
    private model.Diagnosis selectedDiagnosis;

    @FXML
    public TableView<model.Diagnosis> table;

    @FXML
    public TableColumn<model.Diagnosis, String> nameColumn;

    @FXML
    public TableColumn<model.Diagnosis, String> descriptionColumn;

    @FXML
    public Label nameLabel;

    @FXML
    public TextField nameField;

    @FXML
    public Label description;

    @FXML
    public TextArea descriptionField;

    @FXML
    public Button save;

    @FXML
    public Button change;

    @FXML
    public Button delete;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("" +
                    "select d.id," +
                    " d.name," +
                    " d.description" +
                    " from public.diagnosis d");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<model.Diagnosis>() {

            @Override
            public void changed(ObservableValue observableValue, model.Diagnosis oldValue, model.Diagnosis newValue) {
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    selectedDiagnosis = table.getSelectionModel().getSelectedItem();
                    nameField.setText(selectedDiagnosis.getName());
                    descriptionField.setText(selectedDiagnosis.getDescription());
                }
            }
        });
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String err = validate();
        if (err == null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("insert into public.diagnosis (name, description) values " +
                        "('" + nameField.getText() + "', '" + descriptionField.getText() + "')");

                updateDiagnoses();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    @FXML
    public void change(ActionEvent actionEvent) {
        if (selectedDiagnosis != null) {
            String err = validate();
            if (err == null) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    statement.execute("update public.diagnosis set " +
                            "name = '" + nameField.getText() + "', " +
                            "description = '" + descriptionField.getText() + "' " +
                            "where id = " + selectedDiagnosis.getId());

                    updateDiagnoses();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите диагноз");
        }
    }


    @FXML
    public void delete(ActionEvent actionEvent) {
        if (selectedDiagnosis != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from public.diagnosis where id =" + selectedDiagnosis.getId());

                Statement statement1 = connect.getConnection().createStatement();
                statement1.execute("delete from public.patient_diagnosis pd where pd.diagnosis_id =" + selectedDiagnosis.getId());

                updateDiagnoses();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите диагноз");
        }
    }

    private void setTable (int id, String serviceName, String descriptionService){
        diagnoses.add(new model.Diagnosis(id, serviceName, descriptionService));

        nameColumn.setCellValueFactory(new PropertyValueFactory<model.Diagnosis, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<model.Diagnosis, String>("description"));

        table.setItems(diagnoses);
    }

    private void updateDiagnoses () {
        diagnoses.clear();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("" +
                    "select d.id," +
                    " d.name," +
                    " d.description" +
                    " from public.diagnosis d");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String validate() {
        if (nameField.getText().isEmpty()) {
            return "Укажите название услуги";
        } else if (descriptionField.getText().isEmpty()) {
            return "Укажите описание услуги";
        }
        return null;
    }
}
