package controllers;

import client.Client;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import utils.ClientMessages;
import utils.FxmlLoader;
import utils.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Client implements Initializable {
    private boolean isAuthorizationSkipped = true;
    private Stage stage;

    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXCheckBox rememberLogin;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXTextField loginField;
    @FXML
    private JFXPasswordField passField;

    @FXML
    private void loginAction(ActionEvent event){
        if (isAuthorizationSkipped){
            new ProgramController();
        } else {
            if (loginField.getText().equals("") || passField.getText().equals("")){
                showNotification("Login and password field are not filled");
            } else {
                sendMessage((Messages.LOGIN + " " + loginField.getText() + " " + passField.getText()).getBytes());

                if (isAuthenticated){
                    if (rememberLogin.isSelected()) rememberLogin();

                    showNotification("Welcome, " + loginField.getText() + "!");
                    new ProgramController();
                } else {
                    loginField.clear();
                    passField.clear();
                    showNotification("Sorry, invalid credentials!");
                }
            }
        }
    }

    @FXML
    private void registerAction(ActionEvent event) throws IOException{
        loginButton.setText("Register");
        registerButton.setVisible(false);

        if (loginField.getText().equals("") || passField.getText().equals("")){
            showNotification(ClientMessages.FIELDS_NOT_FILEED);
        } else {
            if (rememberLogin.isSelected()) rememberLogin();

            sendMessage(("/register " + loginField.getText() + " " + passField.getText()).getBytes());
            showNotification("Welcome, " + loginField.getText() + " !");
        }
    }

    @FXML
    private void rememberLogin(){
        //todo
        //loginField.setPromptText(loginField.getText());
        //passField.setPromptText(passField.getText());
    }

    public static void showNotification(String str){
        Notifications.create()
                .text("Warning!")
                .text(str)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new FxmlLoader().loadFXML(loginButton,"/resources/Login.fxml");
        new Client();
    }
}
