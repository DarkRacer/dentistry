package owner.payment;

import DB.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class Payment implements Initializable {
    private Connect connect = null;
    private ObservableList<PaymentDto> payments = FXCollections.observableArrayList();
    private ObservableList<String> statusList = FXCollections.observableArrayList();

    @FXML
    public TableView<PaymentDto> table;

    @FXML
    public Label statusLabel;

    @FXML
    public ComboBox<String> status;

    @FXML
    public TableColumn<PaymentDto, String> clientColumn;

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
        statusList.add("Не оплачено");
        statusList.add("Оплачено");
        statusList.add("Проверяется");

        status.setItems(statusList);

        updatePayments("");
    }

    private void updatePayments(String stat) {
        payments.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            final ResultSet resultSet;
            if (Objects.equals(stat, "")) {
                resultSet = statement.executeQuery("select p.id, CONCAT(pat.surname, ' ', pat.Name, ' ', pat.patronymic) as patient," +
                        "sch.date," +
                        "ps.name," +
                        "p.sum" +
                        " from public.payment p" +
                        " left join public.schedule sch on sch.id = p.\"idRecord\" " +
                        " left join public.patient pat on pat.id = sch.\"idPatient\" " +
                        " left join public.payment_status ps on p.status = ps.id");

            } else {
                int idStat = 0;
                for (int i = 0; i < statusList.size(); i++) {
                    if (Objects.equals(statusList.get(i), stat)) {
                        idStat = ++i;
                        break;
                    }
                }
                resultSet = statement.executeQuery("select p.id, CONCAT(pat.surname, ' ', pat.Name, ' ', pat.patronymic) as patient," +
                        "sch.date," +
                        "ps.name," +
                        "p.sum" +
                        " from public.payment p" +
                        " left join public.schedule sch on sch.id = p.\"idRecord\" " +
                        " left join public.patient pat on pat.id = sch.\"idPatient\" " +
                        " left join public.payment_status ps on p.status = ps.id" +
                        " where p.status =" + idStat);
            }
            while (resultSet.next()) {
                String status = "";
                if (Objects.equals(resultSet.getString(4), PaymentStatus.NOT_PAID.name())) {
                    status = "Не оплачено";
                } else if (Objects.equals(resultSet.getString(4), PaymentStatus.CHECK.name())) {
                    status = "Проверяется";
                } else if (Objects.equals(resultSet.getString(4), PaymentStatus.PAID.name())) {
                    status = "Оплачено";
                }

                setTable(resultSet.getInt(1), resultSet.getString(2),
                        LocalDate.from(resultSet.getDate(3).toLocalDate()), status, resultSet.getInt(5));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (int id, String fio, LocalDate date, String status, int price){
        payments.add(new PaymentDto(id, fio, date, status, price));

        clientColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("fio"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, LocalDate>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("status"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentDto, Integer>("price"));

        table.setItems(payments);
    }

    @FXML
    public void sendRequest(ActionEvent actionEvent) {
        String stat = status.getValue();
        if (stat != null) {
            updatePayments(stat);
        }
    }
}
