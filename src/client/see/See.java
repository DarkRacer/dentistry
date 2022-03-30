package client.see;

import DB.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static sample.Main.alert;

public class See implements Initializable {
    private Connect connect = null;
    private ObservableList<String> services = FXCollections.observableArrayList();
    private ObservableList<String> doctors = FXCollections.observableArrayList();
    private ObservableList<ServicesAndDoctorModel> servicesAndDoctors = FXCollections.observableArrayList();

    @FXML
    public TableView<ServicesAndDoctorModel> table;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> doctorColumn;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> serviceColumn;

    @FXML
    public TableColumn<ServicesAndDoctorModel, String> description;

    @FXML
    public TableColumn<ServicesAndDoctorModel, Integer> price;

    @FXML
    public Label serviceLabel;

    @FXML
    public ComboBox<String> service;

    @FXML
    public Label doctorLabel;

    @FXML
    public ComboBox<String> doctor;

    @FXML
    public Button find;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        services.clear();
        doctors.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select s.name from public.services s ");
            
            while (resultSet.next()) {
                services.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) as doctor from public.doctor doc ");

            while (resultSet.next()) {
                doctors.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        service.setItems(services);
        doctor.setItems(doctors);
    }

    @FXML
    public void find(ActionEvent actionEvent) {
        if (service.getValue() != null && !service.getValue().isEmpty()) {
            if (doctor.getValue() != null && !doctor.getValue().isEmpty()) {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    final ResultSet resultSet = statement.executeQuery("" +
                            "select CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) as doctor," +
                            "s.name," +
                            "s.description," +
                            "s.price" +
                            " from public.services s " +
                            "left join public.services_rendered sr on sr.\"idServices\" = s.id" +
                            " left join public.doctor doc on doc.id = sr.\"idDoc\" " +
                            " where s.name = '" + service.getValue() + "' " +
                            "and CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) = '" + doctor.getValue() + "' ");

                    while (resultSet.next()) {
                        setTable(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                try {
                    connect = new Connect();
                    Statement statement = connect.getConnection().createStatement();

                    final ResultSet resultSet = statement.executeQuery("" +
                            "select CONCAT(doc.patronymic, ' ', doc.Name, ' ', doc.Surname) as doctor," +
                            "s.name," +
                            "s.description," +
                            "s.price" +
                            " from public.services s " +
                            "left join public.services_rendered sr on sr.\"idServices\" = s.id" +
                            " left join public.doctor doc on doc.id = sr.\"idDoc\" " +
                            " where s.name = '" + service.getValue() + "' ");

                    while (resultSet.next()) {
                        setTable(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (doctor.getValue() != null && !doctor.getValue().isEmpty()) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                final ResultSet resultSet = statement.executeQuery("" +
                        "select CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) as doctor," +
                        "s.name," +
                        "s.description," +
                        "s.price" +
                        " from public.services s " +
                        "left join public.services_rendered sr on sr.\"idServices\" = s.id" +
                        " left join public.doctor doc on doc.id = sr.\"idDoc\" " +
                        " where CONCAT(doc.surname, ' ', doc.Name, ' ', doc.patronymic) = '" + doctor.getValue() + "' ");

                while (resultSet.next()) {
                    setTable(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void setTable (String doctor, String serviceName, String descriptionService, int priceService){
        servicesAndDoctors.add(new ServicesAndDoctorModel(doctor, serviceName, descriptionService, priceService));

        doctorColumn.setCellValueFactory(new PropertyValueFactory<ServicesAndDoctorModel, String>("doctor"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<ServicesAndDoctorModel, String>("service"));
        description.setCellValueFactory(new PropertyValueFactory<ServicesAndDoctorModel, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<ServicesAndDoctorModel, Integer>("price"));

        table.setItems(servicesAndDoctors);
    }
}
