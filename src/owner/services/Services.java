package owner.services;

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
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Services implements Initializable {
    private Connect connect;
    private ObservableList<model.Services> services = FXCollections.observableArrayList();
    private model.Services selectedService;

    @FXML
    public TableView<model.Services> table;

    @FXML
    public TableColumn<model.Services, String> nameColumn;

    @FXML
    public TableColumn<model.Services, String> descriptionColumn;

    @FXML
    public TableColumn<model.Services, Integer> priceColumn;

    @FXML
    public Label nameLabel;

    @FXML
    public TextField nameField;

    @FXML
    public Label priceLabel;

    @FXML
    public TextField priceField;

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
                    "select s.id," +
                    "s.name," +
                    "s.description," +
                    "s.price" +
                    " from public.services s " +
                    "left join public.services_rendered sr on sr.\"idServices\" = s.id");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<model.Services>() {

            @Override
            public void changed(ObservableValue observableValue, model.Services oldValue, model.Services newValue) {
                if(table.getSelectionModel().getSelectedItem() != null)
                {
                    selectedService = table.getSelectionModel().getSelectedItem();
                    nameField.setText(selectedService.getName());
                    descriptionField.setText(selectedService.getDescription());
                    priceField.setText(String.valueOf(selectedService.getPrice()));
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

                statement.execute("insert into public.services (name, description, price) values " +
                        "('" + nameField.getText() + "', '" + descriptionField.getText() + "', " +
                        Integer.parseInt(priceField.getText()) + ")");

                updateServices();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
        }
    }

    @FXML
    public void change(ActionEvent actionEvent) {
        if (selectedService != null) {
            String err = validate();
            if (err == null) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    statement.execute("update public.services set " +
                            "name = '" + nameField.getText() + "', " +
                            "description = '" + descriptionField.getText() + "', " +
                            "price = " + Integer.parseInt(priceField.getText()) + " " +
                            "where id = " + selectedService.getId());

                    updateServices();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                Main.alert(Alert.AlertType.ERROR, "Ошибка", err);
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите услугу");
        }
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        if (selectedService != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from public.services where id =" + selectedService.getId());

                Statement statement1 = connect.getConnection().createStatement();
                statement1.execute("delete from public.services_rendered sr where sr.\"idServices\" =" + selectedService.getId());

                updateServices();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите услугу");
        }
    }

    private void setTable (int id, String serviceName, String descriptionService, int priceService){
        services.add(new model.Services(id, serviceName, descriptionService, priceService));

        nameColumn.setCellValueFactory(new PropertyValueFactory<model.Services, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<model.Services, String>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<model.Services, Integer>("price"));

        table.setItems(services);
    }

    private void updateServices () {
        services.clear();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("" +
                    "select s.id," +
                    "s.name," +
                    "s.description," +
                    "s.price" +
                    " from public.services s " +
                    "left join public.services_rendered sr on sr.\"idServices\" = s.id");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
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
        } else if (priceField.getText().isEmpty()) {
            return "Укажите цену услуги";
        }
        return null;
    }
}
