package ru.sapteh.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DaoImp.DaoImpClient;
import ru.sapteh.Model.Client;
import ru.sapteh.Model.Gender;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class WindowViewController {
    private final SessionFactory factory;

    public WindowViewController(){
        factory = new Configuration().configure().buildSessionFactory();
    }

    ObservableList<Client> observableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, Integer> idColumn;

    @FXML
    private TableColumn<Client, String> firstNameColumn;

    @FXML
    private TableColumn<Client, String> lastNameColumn;

    @FXML
    private TableColumn<Client, String> patronymicColumn;

    @FXML
    private TableColumn<Client, Date> birthdayColumn;

    @FXML
    private TableColumn<Client, Date> registrationColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, Gender> genderCodeColumn;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private Label numberOfRecordsLbl;

    @FXML
    private Pagination pagination;

    private int dataBaseSize;
    private int totalPage;

    @FXML
    public void initialize(){

        initData();

        dataBaseSize = observableList.size();

        ObservableList<Integer> option = FXCollections.observableArrayList(10,20,50,dataBaseSize);
        comboBox.setItems(option);
        comboBox.setValue(option.get(0));
        comboBox.valueProperty().addListener(
                (observable,oldValue,newValue) ->{
                    int valueComboBox = comboBox.getValue();

                    totalPage = (int)(Math.ceil(dataBaseSize * 1.0 / comboBox.getValue()));

                    pagination.setPageCount(totalPage);
                    pagination.setCurrentPageIndex(0);
                    clientTableView.setItems(
                            FXCollections.observableArrayList(
                                    observableList.subList(pagination.getCurrentPageIndex(),newValue))

                    );
                    pagination.currentPageIndexProperty()
                            .addListener((observable1, oldValue1,newValue1) -> {
                                clientTableView.setItems(
                                        FXCollections.observableArrayList(
                                                observableList.subList(valueComboBox * (newValue1.intValue()+1) - valueComboBox,
                                                        valueComboBox * (newValue1.intValue()+1))));
                            });
                }
        );
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

        numberOfRecordsLbl.setText("number of records"+ " " + observableList.size());

    }

    private void initData() {

        DAO<Client,Integer> clientIntegerDAO = new DaoImpClient(factory);
        List<Client> clientList = clientIntegerDAO.readByAll();

        idColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getId()));
        firstNameColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getLastName()));
        patronymicColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getPatronymic()));
        birthdayColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getBirthday()));
        registrationColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getRegistrationDate()));
        emailColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getEmail()));
        phoneColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getPhone()));
        genderCodeColumn.setCellValueFactory(p->new SimpleObjectProperty<>(p.getValue().getGender()));

        observableList.addAll(clientList);
        clientTableView.setItems(observableList);

    }

    public void buttonSaveToPdf() throws IOException, DocumentException {

        String font = "./src/main/resources/font/arial.ttf";
        BaseFont bs = BaseFont.createFont(font,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

        Document document = new Document();
        String fileName = "test.pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        Image image = Image.getInstance("./src/main/resources/images/service_logo.png");
        image.scaleAbsoluteHeight(20);
        image.scaleAbsoluteWidth(130);
        image.setAlignment(Element.ALIGN_RIGHT);
        document.add(image);


        Paragraph paragraph = new Paragraph("Hello world ");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(20);
        document.add(paragraph);


        //table
        PdfPTable table = new PdfPTable(3);

        ObservableList<TableColumn<Client, ?>> columns = clientTableView.getColumns();
        columns.forEach(c -> table.addCell(new PdfPCell(new Phrase(c.getText()))));


        table.setHeaderRows(1);

        for (int i = 0; i <= clientTableView.getItems().size(); i++) {
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().get(i).getId())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().get(i).getFirstName())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getLastName())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getPatronymic())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getBirthday().toString())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getRegistrationDate().toString())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getEmail())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getPhone())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getGender().toString())));
            table.addCell(new PdfPCell(new Phrase(clientTableView.getItems().iterator().next().getPhotoPath())));
        }


        document.add(table);

        document.close();
        System.out.println("success");
    }

}
