package client.pay;

import DB.Connect;
import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.PaymentDto;
import model.enumeration.PaymentStatus;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
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
        PaymentDto paymentDto = table.getSelectionModel().getSelectedItem();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            statement.execute("update payment set status = 3 where id = " + paymentDto.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        updatePayments();
    }

    private void updatePayments() {
        payments.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select p.id, CONCAT(doc.patronymic, ' ', doc.Name, ' ', doc.Surname) as doctor," +
                    "sch.date," +
                    "ps.name," +
                    "p.sum" +
                    " from public.payment p" +
                    " left join public.schedule sch on sch.id = p.\"idRecord\" " +
                    " left join public.doctor doc on doc.id = sch.\"idDoc\" " +
                    " left join public.patient pat on pat.id = sch.\"idPatient\" " +
                    " left join public.payment_status ps on p.status = ps.id" +
                    " where pat.id = " + Client.getPatient().getId());

            while (resultSet.next()) {
                String status = "";
                if (Objects.equals(resultSet.getString(4), PaymentStatus.NOT_PAID.name())) {
                    status = "Не оплачено";
                } else if (Objects.equals(resultSet.getString(4), PaymentStatus.CHECK.name())) {
                    status = "Проверяется";
                } else if (Objects.equals(resultSet.getString(4), PaymentStatus.PAID.name())){
                    status = "Оплачено";
                }
                setTable(resultSet.getInt(1), resultSet.getString(2),
                        LocalDate.from(resultSet.getDate(3).toLocalDate()), status, resultSet.getInt(5));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (int id, String doctor, LocalDate date, String status, int price){
        payments.add(new PaymentDto(id, doctor, date, status, price));

        doctorColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("doctor"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, LocalDate>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("status"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, Integer>("price"));

        table.setItems(payments);
    }
}
