package administrator.schedule;

import DB.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.RecordDto;
import sample.Main;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Schedule implements Initializable {
    private Connect connect = null;
    private ObservableList<RecordDto> records = FXCollections.observableArrayList();

    @FXML
    public TableView<RecordDto> table;

    @FXML
    public TableColumn<RecordDto, String> patientColumn;

    @FXML
    public TableColumn<RecordDto, LocalDate> dateColumn;

    @FXML
    public TableColumn<RecordDto, LocalTime> timeColumn;

    @FXML
    public Label dateLabel;

    @FXML
    public DatePicker date;

    @FXML
    public Button see;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        updateTable(LocalDate.now());
        date.setValue(LocalDate.now());
    }

    @FXML
    public void see(ActionEvent actionEvent) {
        if (date.getValue() != null) {
            updateTable(date.getValue());
        } else {
            Main.alert(Alert.AlertType.ERROR, "Ошибка", "Выберите дату");
        }
    }

    private void updateTable(LocalDate localDate) {
        records.clear();

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            java.util.Date dateConvert =
                    java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(dateConvert.getTime());

            final ResultSet resultSet = statement.executeQuery("select CONCAT(p.patronymic, ' ', p.Name, ' ', p.Surname) as patient," +
                    " sch.date," +
                    " sch.time," +
                    " sch.id" +
                    " from public.schedule sch" +
                    " left join public.patient p on p.id = sch.\"idPatient\"" +
                    " left join public.payment pay on pay.\"idRecord\" = sch.id " +
                    " where sch.date = '" + sqlDate +"'");

            Statement statement1 = connect.getConnection().createStatement();

            final ResultSet resultSet1 = statement1.executeQuery("select pay.\"idRecord\" from public.payment pay");

            List<RecordDto> recordDtos = new ArrayList<>();
            List<RecordDto> dublicateRecordDtos = new ArrayList<>();
            while (resultSet.next()) {
                recordDtos.add(new RecordDto(resultSet.getInt(4), resultSet.getString(1), LocalDate.from(resultSet.getDate(2).toLocalDate()),
                        LocalTime.from(resultSet.getTimestamp(3).toLocalDateTime().toLocalTime())));
            }

            while (resultSet1.next()) {
                for (RecordDto recordDto : recordDtos) {
                    if (recordDto.getId() == resultSet1.getInt(1)) {
                        dublicateRecordDtos.add(recordDto);
                    }
                }
            }
            recordDtos.removeAll(dublicateRecordDtos);

            for (RecordDto recordDto : recordDtos) {
                setTable(recordDto.getFio(), recordDto.getDate(), recordDto.getTime(), recordDto.getId());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTable (String patient, LocalDate date, LocalTime time, int id){
        records.add(new RecordDto(id, patient, date, time));

        patientColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, String>("fio"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<RecordDto, LocalTime>("time"));

        table.setItems(records);
    }
}
