package client.record;

import DB.Connect;
import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.RecordDto;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Record implements Initializable {
    private Connect connect = null;
    private ObservableList<RecordDto> records = FXCollections.observableArrayList();


    @FXML
    public TableView<RecordDto> table;

    @FXML
    public TableColumn<RecordDto, String>  doctor;

    @FXML
    public TableColumn<RecordDto, LocalDate>  date;

    @FXML
    public TableColumn<RecordDto, LocalTime>  time;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        records.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            final ResultSet resultSet = statement.executeQuery("select CONCAT(doc.patronymic, ' ', doc.Name, ' ', doc.Surname) as doctor," +
                    " sch.date," +
                    " sch.time" +
                    " from public.schedule sch" +
                    " left join public.doctor doc on doc.id = sch.\"idDoc\"" +
                    " where sch.\"idPatient\" = " + Client.getPatient().getId());

            while (resultSet.next()) {
                setTable(resultSet.getString(1), LocalDate.from(resultSet.getDate(2).toLocalDate()),
                        LocalTime.from(resultSet.getTimestamp(3).toLocalDateTime().toLocalTime()));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (String doctorV, LocalDate dateV, LocalTime timeV){
        records.add(new RecordDto(doctorV, dateV, timeV));

        doctor.setCellValueFactory(new PropertyValueFactory<RecordDto, String>("fio"));
        date.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalDate>("date"));
        time.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalTime>("time"));

        table.setItems(records);
    }
}
