package client.help;

import DB.Connect;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Main;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Help {
    private Connect connect;

    @FXML
    public TextArea complaints;

    @FXML
    public Button send;

    @FXML
    public Label error;


    @FXML
    public void send(ActionEvent actionEvent) {
        if (!complaints.getText().isEmpty()) {
            try {
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("insert into public.call_log (complaints, \"dateOfRequest\", \"idPatient\", is_new) " +
                        "values ('" + complaints.getText() + "', '" + Date.valueOf(LocalDate.now()) + "', " + Client.getPatient().getId() + ", true)");

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Ваша заявка успешно подана");
                Stage stage = (Stage) send.getScene().getWindow();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
