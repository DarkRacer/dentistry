package owner.doctor.services;

import DB.Connect;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Services;
import owner.doctor.Doctor;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Service implements Initializable {
    private Connect connect;
    private ObservableList<Services> services = FXCollections.observableArrayList();
    private ObservableList<Services> servicesRendered = FXCollections.observableArrayList();

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

    @FXML
    public Label doctor;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        services.clear();
        servicesRendered.clear();
        doctor.setText(Doctor.getSelectedDoctor().getFio());

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            List<Services> servicesBuf = new ArrayList<>();
            List<Services> servicesRenderedBuf = new ArrayList<>();

            final ResultSet resultSet = statement.executeQuery("select s.id," +
                    " s.name," +
                    " s.description," +
                    " s.price" +
                    " from public.services s" +
                    " left join public.services_rendered sr on s.id = sr.\"idServices\"" +
                    " left join public.doctor doc on doc.id = sr.\"idDoc\"" +
                    " where sr.\"idDoc\" = "+ Doctor.getSelectedDoctor().getId());

            while (resultSet.next()) {
                servicesRenderedBuf.add(new Services(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4)));
            }

            Statement statement1 = connect.getConnection().createStatement();

            final ResultSet resultSet1 = statement1.executeQuery("select s.id," +
                    " s.name," +
                    " s.description," +
                    " s.price" +
                    " from public.services s");

            while (resultSet1.next()) {
                servicesBuf.add(new Services(resultSet1.getInt(1), resultSet1.getString(2),
                        resultSet1.getString(3), resultSet1.getInt(4)));
            }

            List<Services> servicesForRemove = new ArrayList<>();
            for (Services serviceRendered : servicesRenderedBuf) {
                for (Services service : servicesBuf) {
                    if (service.getId() == serviceRendered.getId()) {
                        servicesForRemove.add(service);
                        break;
                    }
                }
            }
            servicesBuf.removeAll(servicesForRemove);

            for (Services service : servicesBuf) {
                setTable(service.getId(), service.getName(), service.getDescription(), service.getPrice());
            }

            for (Services serviceRendered : servicesRenderedBuf) {
                setTableRendered(serviceRendered.getId(), serviceRendered.getName(), serviceRendered.getDescription(), serviceRendered.getPrice());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (servicesRendered.size() != 0) {
            try {
                connect = new Connect();

                Statement statement = connect.getConnection().createStatement();
                statement.execute("delete from public.services_rendered where \"idDoc\" = " + Doctor.getSelectedDoctor().getId());

                for (Services services : servicesRendered) {
                    Statement statement2 = connect.getConnection().createStatement();
                    statement2.execute("insert into  public.services_rendered (\"idDoc\", \"idServices\") values (" +
                            Doctor.getSelectedDoctor().getId() + ", " + services.getId() + ")");
                }

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Услуги врача изменены");
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Добавьте хотя бы одну услугу");
        }
    }

    @FXML
    public void remove(ActionEvent actionEvent) {
        Services service = tableRendered.getSelectionModel().getSelectedItem();
        if (service != null) {
            servicesRendered.remove(service);

            setTable(service.getId(), service.getName(), service.getDescription(), service.getPrice());
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
}
