package administrator.payment;

import DB.Connect;
import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.PaymentDto;
import model.enumeration.PaymentStatus;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class Payment implements Initializable {
    private Connect connect = null;
    private ObservableList<PaymentDto> payments = FXCollections.observableArrayList();

    @FXML
    public TableView<PaymentDto> table;

    @FXML
    public TableColumn<PaymentDto, String> clientColumn;

    @FXML
    public TableColumn<PaymentDto, LocalDate> dateColumn;

    @FXML
    public TableColumn<PaymentDto, String> statusColumn;

    @FXML
    public TableColumn<PaymentDto, Integer> priceColumn;

    @FXML
    public Button success;

    @FXML
    public Button failed;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updatePayments();
    }

    private void updatePayments() {
        payments.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select p.id, CONCAT(pat.surname, ' ', pat.Name, ' ', pat.patronymic) as patient," +
                    "sch.date," +
                    "ps.name," +
                    "p.sum" +
                    " from public.payment p" +
                    " left join public.schedule sch on sch.id = p.\"idRecord\" " +
                    " left join public.patient pat on pat.id = sch.\"idPatient\" " +
                    " left join public.payment_status ps on p.status = ps.id" +
                    " where ps.id != 2");

            while (resultSet.next()) {
                String status = "";
                if (Objects.equals(resultSet.getString(4), PaymentStatus.NOT_PAID.name())) {
                    status = "Не оплачено";
                } else if (Objects.equals(resultSet.getString(4), PaymentStatus.CHECK.name())) {
                    status = "Проверяется";
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

        clientColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("fio"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, LocalDate>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("status"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, Integer>("price"));

        table.setItems(payments);
    }

    @FXML
    public void failed(ActionEvent actionEvent) {
        PaymentDto paymentDto = table.getSelectionModel().getSelectedItem();

        if (paymentDto != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("update payment set status = 1 where id = " + paymentDto.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            updatePayments();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите запись");
        }
    }

    @FXML
    public void success(ActionEvent actionEvent) {
        PaymentDto paymentDto = table.getSelectionModel().getSelectedItem();

        if (paymentDto != null) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("update payment set status = 2 where id = " + paymentDto.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            updatePayments();
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите запись");
        }
    }
}
