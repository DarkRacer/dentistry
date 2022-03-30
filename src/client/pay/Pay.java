package client.pay;

import DB.Connect;
import client.see.ServicesAndDoctorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Pay implements Initializable {
    private Connect connect = null;
    private ObservableList<PaymentDto> payments = FXCollections.observableArrayList();

    @FXML
    public TableView<PaymentDto> table;

    @FXML
    public TableColumn<PaymentDto, String> doctorColumn;

    @FXML
    public TableColumn<PaymentDto, LocalDate> dateColumn;

    @FXML
    public TableColumn<PaymentDto, String> statusColumn;

    @FXML
    public TableColumn<PaymentDto, Integer> priceColumn;

    @FXML
    public Button send;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updatePayments();
    }

    @FXML
    public void sendRequest(ActionEvent actionEvent) {


        updatePayments();
    }

    private void updatePayments() {
        payments.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            //sql
            final ResultSet resultSet = statement.executeQuery("select s.name from public.services s ");

            while (resultSet.next()) {
                setTable(resultSet.getString(1), LocalDate.from(resultSet.getDate(2).toLocalDate()), resultSet.getString(3), resultSet.getInt(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (String doctor, LocalDate date, String status, int price){
        payments.add(new PaymentDto(doctor, date, status, price));

        doctorColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("doctor"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, LocalDate>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("status"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, Integer>("price"));

        table.setItems(payments);
    }
}
