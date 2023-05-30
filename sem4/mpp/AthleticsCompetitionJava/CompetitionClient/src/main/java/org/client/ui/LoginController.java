package org.client.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.model.Organiser;
import org.model.exception.CompException;
import org.services.ICompetitionServices;

import java.io.IOException;
import java.util.UUID;

public class LoginController {
    private ICompetitionServices server;
    private OrganiserController organiserCtrl;
    Parent mainParent;

    public TextField emailTextField;
    public PasswordField passwordTextField;

    public void setServer(ICompetitionServices server) {
        this.server = server;
    }

    public void setParent(Parent p) {
        this.mainParent = p;
    }

    public void onLoginButtonClicked(ActionEvent actionEvent) throws IOException {
        try {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();

            server.login(new Organiser(UUID.randomUUID(), email, password), organiserCtrl);

            Stage stage = new Stage();
            stage.setTitle("Logat ca " + email);
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        System.out.println("TEST LOGOUTT");
                        server.logout(new Organiser(UUID.randomUUID(), email, password), organiserCtrl);
                    } catch (CompException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            });

            stage.show();
            organiserCtrl.reallyInitialize();
            organiserCtrl.setOrganiser(new Organiser(UUID.randomUUID(), email, password));

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (CompException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void setOrganiserController(OrganiserController organiserCtrl) {
        this.organiserCtrl = organiserCtrl;
    }
}
