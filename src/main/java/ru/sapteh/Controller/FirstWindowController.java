package ru.sapteh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.sapteh.DAO.DAO;
import ru.sapteh.DaoImp.DaoImpUsers;
import ru.sapteh.Model.Users;

import java.io.IOException;
import java.util.List;

public class FirstWindowController {

    private final SessionFactory factory;

    public FirstWindowController(){
        factory = new Configuration().configure().buildSessionFactory();
    }


    @FXML
    TextField txtLogin;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label labelSuccess;

    ObservableList<Users> observableUsers = FXCollections.observableArrayList();


    public void buttonExit(){
        System.exit(0);
    }

    public void initialize(){
    }

    public void buttonOpenProgram(){

        DAO<Users,Integer> userService = new DaoImpUsers(factory);

        List<Users> listUsers = userService.readByAll();
        observableUsers.addAll(listUsers);

        String login = txtLogin.getText();
        String password = txtPassword.getText();

        for (Users users : observableUsers) {

            if (login.equals(users.getLogin()) && password.equals(users.getPassword())){

                    labelSuccess.setTextFill(Color.GREEN);
                    labelSuccess.setText("Authorization success");

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/View/WindowView.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Data window");
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                labelSuccess.setText("Authorization fail");
                labelSuccess.setTextFill(Color.RED);
            }
        }
    }


}
