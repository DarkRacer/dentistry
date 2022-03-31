package doctor.payment;

import DB.Connect;
import doctor.Doctor;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Services;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Payment implements Initializable {
    private Connect connect;
    private ObservableList<Services> services = FXCollections.observableArrayList();
    private ObservableList<Services> servicesRendered = FXCollections.observableArrayList();

    @FXML
    public Label sumLabel;

    @FXML
    public Label sum;

    @FXML
    public Button save;

    @FXML
    public TableView<Services> table;

    @FXML
    public TableColumn<Services, String> name;

    @FXML
    public TableColumn<Services, String> description;

    @FXML
    public TableColumn<Services, Integer> price;

    @FXML
    public TableView<Services> tableRendered;

    @FXML
    public TableColumn<Services, String> nameRendered;

    @FXML
    public TableColumn<Services, String> descriptionRendered;

    @FXML
    public TableColumn<Services, Integer> priceRendered;

    @FXML
    public Label yourLabel;

    @FXML
    public Label renderedLabel;

    @FXML
    public Button remove;

    @FXML
    public Button add;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        services.clear();
        servicesRendered.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select s.id," +
                    " s.name," +
                    " s.description," +
                    " s.price" +
                    " from public.services s" +
                    " left join public.services_rendered sr on s.id = sr.\"idServices\"" +
                    " left join public.doctor doc on doc.id = sr.\"idDoc\"" +
                    " left join public.schedule sch on sch.\"idDoc\" = doc.id" +
                    " where sr.\"idDoc\" = "+ Doctor.getDoctor().getId() +" and sch.id = "+ Doctor.getRecord().getId() +"");

            while (resultSet.next()) {
                setTable(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (servicesRendered != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("insert into public.payment (sum, status, \"idRecord\") " +
                        "values (" + Integer.parseInt(sum.getText().replace(" р.", ""))
                        + ", 1, " + Doctor.getRecord().getId() + ")");

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Счёт успешно выставлен");

                Statement statement1 = connect.getConnection().createStatement();
                final ResultSet resultSet = statement1.executeQuery("select sch.\"idPatient\" from public.schedule sch where sch.id =" +Doctor.getRecord().getId());

                int idPatient = 0;
                while(resultSet.next()) {
                    idPatient = resultSet.getInt(1);
                }

                if (idPatient != 0) {
                    Statement statement2 = connect.getConnection().createStatement();
                    statement2.execute("update public.call_log  set is_new = false where \"idPatient\" =" + idPatient);
                }

                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
                Stage Stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/doctor/doctor.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage.setTitle("Добро пожаловать");
                Stage.setScene(new Scene(root, 600, 400));
                Stage.setResizable(false);
                Stage.centerOnScreen();
                Stage.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @FXML
    public void remove(ActionEvent actionEvent) {
        Services service = tableRendered.getSelectionModel().getSelectedItem();
        if (service != null) {
            servicesRendered.remove(service);

            setTable(service.getId(), service.getName(), service.getDescription(), service.getPrice());
            calculateSum();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите услугу");
        }
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        Services service = table.getSelectionModel().getSelectedItem();
        if (service != null) {
            services.remove(service);

            setTableRendered(service.getId(), service.getName(), service.getDescription(), service.getPrice());
            calculateSum();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите услугу");
        }
    }

    private void setTable (int id, String nameV, String descriptionV, int priceV){
        services.add(new Services(id, nameV, descriptionV, priceV));

        name.setCellValueFactory(new PropertyValueFactory<Services, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Services, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Services, Integer>("price"));

        table.setItems(services);
    }

    private void setTableRendered (int id, String nameV, String descriptionV, int priceV){
        servicesRendered.add(new Services(id, nameV, descriptionV, priceV));

        nameRendered.setCellValueFactory(new PropertyValueFactory<Services, String>("name"));
        descriptionRendered.setCellValueFactory(new PropertyValueFactory<Services, String>("description"));
        priceRendered.setCellValueFactory(new PropertyValueFactory<Services, Integer>("price"));

        tableRendered.setItems(servicesRendered);
    }

    private void calculateSum () {
        int summ = 0;
        for (Services service : servicesRendered) {
            summ += service.getPrice();
        }

        sum.setText(String.valueOf(summ) + " р.");
    }
}
