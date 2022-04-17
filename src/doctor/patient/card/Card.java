package doctor.patient.card;

import DB.Connect;
import doctor.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dto.PatientDto;
import sample.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class Card implements Initializable {
    private String[] statuses = {" ", "O", "R", "C", "P", "Pt", "П", "А", "I", "II", "III", "K", "И"};
    private ObservableList<String> status = FXCollections.observableArrayList();
    private Connect connect;
    private PatientDto patientDto;
    private HashMap<String, ComboBox<String>> mapTeeth;

    @FXML
    public Button info;

    @FXML
    public ComboBox<String> r8up;

    @FXML
    public ComboBox<String> r8down;

    @FXML
    public ComboBox<String> r7up;

    @FXML
    public ComboBox<String> r7down;

    @FXML
    public ComboBox<String> r6up;

    @FXML
    public ComboBox<String> r6down;

    @FXML
    public ComboBox<String> r5up;

    @FXML
    public ComboBox<String> r5down;

    @FXML
    public ComboBox<String> r4up;

    @FXML
    public ComboBox<String> r4down;

    @FXML
    public ComboBox<String> r3up;

    @FXML
    public ComboBox<String> r3down;

    @FXML
    public ComboBox<String> r2up;

    @FXML
    public ComboBox<String> r2down;

    @FXML
    public ComboBox<String> r1up;

    @FXML
    public ComboBox<String> r1down;

    @FXML
    public ComboBox<String> l1up;

    @FXML
    public ComboBox<String> l1down;

    @FXML
    public ComboBox<String> l2up;

    @FXML
    public ComboBox<String> l2down;

    @FXML
    public ComboBox<String> l3up;

    @FXML
    public ComboBox<String> l3down;

    @FXML
    public ComboBox<String> l4up;

    @FXML
    public ComboBox<String> l4down;

    @FXML
    public ComboBox<String> l5up;

    @FXML
    public ComboBox<String> l5down;

    @FXML
    public ComboBox<String> l6up;

    @FXML
    public ComboBox<String> l6down;

    @FXML
    public ComboBox<String> l7up;

    @FXML
    public ComboBox<String> l7down;

    @FXML
    public ComboBox<String> l8up;

    @FXML
    public ComboBox<String> l8down;

    @FXML
    public Button add;

    @FXML
    public ImageView pic1;

    @FXML
    public ImageView pic2;

    @FXML
    public ImageView pic3;

    @FXML
    public ImageView pic4;

    @FXML
    public ImageView pic5;

    @FXML
    public ImageView pic6;

    @FXML
    public Button diagnosis;

    @FXML
    public Button save;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        patientDto = Doctor.getPatientForDoctor();
        initComboBox();
        setInfoAboutTeeth();
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select p.path from picture p where patient_id ="+ patientDto.getId());
            List<String> imgs = new ArrayList<>();
            while (rs.next()) {
                imgs.add(rs.getString(1));
            }

            int size = imgs.size();
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic1.setImage(image);
                size--;
            }
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic2.setImage(image);
                size--;
            }
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic3.setImage(image);
                size--;
            }
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic4.setImage(image);
                size--;
            }
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic5.setImage(image);
                size--;
            }
            if (size != 0) {
                File file = new File(imgs.get(size-1));
                Image image = new Image(file.toURI().toString());
                pic6.setImage(image);
                size--;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    public void info(ActionEvent actionEvent) {
        Main.alert(Alert.AlertType.INFORMATION, "Информация о кодах", "О-отсутствует\nR-корень\nС-кариес\nР-пульпит\n" +
                "Рt-периодонтит\nП-пломбированный\nА-Пародонтоз\nI,II,III-степени подвижности\nK-коронка\nИ-искусственный зуб");
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterpng);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            if(pic1.getImage() == null) {
                pic1.setImage(image);
            } else if (pic2.getImage() == null) {
                pic2.setImage(image);
            } else if (pic3.getImage() == null) {
                pic3.setImage(image);
            } else if(pic4.getImage() == null) {
                pic4.setImage(image);
            } else if(pic5.getImage() == null) {
                pic5.setImage(image);
            } else {
                pic6.setImage(image);
            }
            saveImage(image);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void diagnosis(ActionEvent actionEvent) {
        Stage Stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("diagnosis/diagnosis.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage.setTitle("Диагнозы пациента");
        Stage.setScene(new Scene(root, 683, 576));
        Stage.setResizable(false);
        Stage.centerOnScreen();
        Stage.show();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String data = "";
        for (Map.Entry<String, ComboBox<String>> entry : mapTeeth.entrySet()) {
            if (entry.getValue().getValue() != null) {
                data += entry.getKey() + ":" + entry.getValue().getValue() + ",";
            } else {
                data += entry.getKey() + ": ,";
            }
        }

        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();

            statement.execute("update card set teeth ='" + data + "' where patient_id=" + patientDto.getId());

            Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Информация о зубах сохранена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComboBox(){
        status.clear();
        status.addAll(statuses);
        r8up.setItems(status);
        r8down.setItems(status);
        r7up.setItems(status);
        r7down.setItems(status);
        r6up.setItems(status);
        r6down.setItems(status);
        r5up.setItems(status);
        r5down.setItems(status);
        r4up.setItems(status);
        r4down.setItems(status);
        r3up.setItems(status);
        r3down.setItems(status);
        r2up.setItems(status);
        r2down.setItems(status);
        r1up.setItems(status);
        r1down.setItems(status);

        l8up.setItems(status);
        l8down.setItems(status);
        l7up.setItems(status);
        l7down.setItems(status);
        l6up.setItems(status);
        l6down.setItems(status);
        l5up.setItems(status);
        l5down.setItems(status);
        l4up.setItems(status);
        l4down.setItems(status);
        l3up.setItems(status);
        l3down.setItems(status);
        l2up.setItems(status);
        l2down.setItems(status);
        l1up.setItems(status);
        l1down.setItems(status);

        mapTeeth = new HashMap<>();
        mapTeeth.put("r8up", r8up);
        mapTeeth.put("r8down", r8down);
        mapTeeth.put("r7up", r7up);
        mapTeeth.put("r7down", r7down);
        mapTeeth.put("r6up", r6up);
        mapTeeth.put("r6down", r6down);
        mapTeeth.put("r5up", r5up);
        mapTeeth.put("r5down", r5down);
        mapTeeth.put("r4up", r4up);
        mapTeeth.put("r4down", r4down);
        mapTeeth.put("r3up", r3up);
        mapTeeth.put("r3down", r3down);
        mapTeeth.put("r2up", r2up);
        mapTeeth.put("r2down", r2down);
        mapTeeth.put("r1up", r1up);
        mapTeeth.put("r1down", r1down);
        mapTeeth.put("l8up", l8up);
        mapTeeth.put("l8down", l8down);
        mapTeeth.put("l7up", l7up);
        mapTeeth.put("l7down", l7down);
        mapTeeth.put("l6up", l6up);
        mapTeeth.put("l6down", l6down);
        mapTeeth.put("l5up", l5up);
        mapTeeth.put("l5down", l5down);
        mapTeeth.put("l4up", l4up);
        mapTeeth.put("l4down", l4down);
        mapTeeth.put("l3up", l3up);
        mapTeeth.put("l3down", l3down);
        mapTeeth.put("l2up", l2up);
        mapTeeth.put("l2down", l2down);
        mapTeeth.put("l1up", l1up);
        mapTeeth.put("l1down", l1down);
    }

    private void setInfoAboutTeeth() {
        String teeth = "";
        try {
            connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select c.teeth from card c where patient_id ="+ patientDto.getId());

            while (rs.next()) {
                teeth = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> teeths = Arrays.asList(teeth.split(","));
        for (String t : teeths) {
            if (mapTeeth.get(t.split(":")[0]) != null){
                mapTeeth.get(t.split(":")[0]).setValue(t.split(":")[1]);
            }
        }

        r8down.setItems(status);
        r7up.setItems(status);
        r7down.setItems(status);
        r6up.setItems(status);
        r6down.setItems(status);
        r6up.setItems(status);
        r6down.setItems(status);
        r5up.setItems(status);
        r5down.setItems(status);
        r4up.setItems(status);
        r4down.setItems(status);
        r3up.setItems(status);
        r3down.setItems(status);
        r2up.setItems(status);
        r2down.setItems(status);
        r1up.setItems(status);
        r1down.setItems(status);

        l8up.setItems(status);
        l8down.setItems(status);
        l7up.setItems(status);
        l7down.setItems(status);
        l6up.setItems(status);
        l6down.setItems(status);
        l6up.setItems(status);
        l6down.setItems(status);
        l5up.setItems(status);
        l5down.setItems(status);
        l4up.setItems(status);
        l4down.setItems(status);
        l3up.setItems(status);
        l3down.setItems(status);
        l2up.setItems(status);
        l2down.setItems(status);
        l1up.setItems(status);
        l1down.setItems(status);
    }

    public void saveImage(Image image) {
        UUID uuid = UUID.randomUUID();
        String path = "resources/teeth/" + patientDto.getId() + "_" + uuid +".png";

        File outputFile = new File(path);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
                connect = new Connect();
                Statement statement = connect.getConnection().createStatement();

                statement.execute("insert into picture (patient_id, path) values (" + patientDto.getId() +", '"+ path + "')");

                Main.alert(Alert.AlertType.INFORMATION, "Успешно", "Снимок загружен");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
