package org.client.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.client.StartObjectClient;
import org.model.*;
import org.model.exception.CompException;
import org.model.exception.RegistrationException;
import org.services.ICompetitionObserver;
import org.services.ICompetitionServices;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrganiserController implements Initializable, ICompetitionObserver {
    public Button logoutBtn;
    private ICompetitionServices server;
    private Organiser organiser;
    public ComboBox<AgeGroup> comboAgeGroups;
    public ComboBox<Event> comboEvents;
    public ListView<Child> childrenList;
    public TableView<Child> childrenTable;
    public TableColumn<Child, String> nameColumn;
    public TableColumn<Child, Integer> ageColumn;
    public TableColumn<Child, Integer> eventsColumn;
    public ComboBox<Event> comboRegisterEvent;
    public Button registerButton;
    public Spinner<Integer> spinner;
    public TextField textName;

    ObservableList<AgeGroup> modelAgeGroup = FXCollections.observableArrayList();
    ObservableList<Event> modelEvents = FXCollections.observableArrayList();
    ObservableList<Event> modelFilteredEvents = FXCollections.observableArrayList();
    ObservableList<Child> modelChildren = FXCollections.observableArrayList();
    ObservableList<Child> modelFilteredChildren = FXCollections.observableArrayList();

    public void setServer(ICompetitionServices server) {
        this.server = server;
    }

    public void setOrganiser(Organiser organiser) {
        this.organiser = organiser;
    }

    public void updateEventTypeList(AgeGroup newAgeGroup) {
        try {
            modelFilteredEvents.setAll(server.getEventTypesForAgeGroup(newAgeGroup));
        } catch (CompException e) {
            e.printStackTrace();
        }
    }

    public void updateFilteredChildren(AgeGroup ageGroup, Event event) {
        try {
            modelFilteredChildren.setAll(server.getChildrenFromAgeGroupAndEvent(ageGroup, event));
        } catch (CompException e) {
            e.printStackTrace();
        }
    }

    HashMap<Child, Integer> eventsByChildren = new HashMap<>();
    public Integer getEventsOfChild(Child child) {
        if(eventsByChildren.containsKey(child)) {
            return eventsByChildren.get(child);
        }
        return 0;
    }

    private GroupsController groupsController;
    public void openGroupsView() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Grupele de varsta si probe");
        FXMLLoader fxmlLoader = new FXMLLoader(StartObjectClient.class.getResource("groups-view.fxml"));
        Scene sceneGroups = new Scene(fxmlLoader.load());
        stage.setScene(sceneGroups);
        stage.show();
        groupsStage = stage;
        groupsController = fxmlLoader.getController();
        groupsController.reallyInitialize(server, modelAgeGroup.stream().collect(Collectors.toList()), modelEvents.stream().collect(Collectors.toList()));
    }

    public void reallyInitialize() {
        comboAgeGroups.setItems(modelAgeGroup);
        comboEvents.setItems(modelFilteredEvents);
        childrenTable.setItems(modelChildren);
        childrenList.setItems(modelFilteredChildren);

        try {
            eventsByChildren = server.getEventsByChildren();
        } catch (CompException e) {
            e.printStackTrace();
        }
        try {
            modelChildren.setAll(server.getChildren());
        } catch (CompException e) {
            e.printStackTrace();
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        eventsColumn.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Child, Integer> param) {
                if (param.getValue() != null) {
                    Integer events = getEventsOfChild(param.getValue());
                    return new SimpleIntegerProperty(events).asObject();
                }
                return new SimpleIntegerProperty(0).asObject();
            }
        });
        try {
            modelAgeGroup.setAll(server.getAgeGroups());
        } catch (CompException e) {
            e.printStackTrace();
        }

        comboAgeGroups.valueProperty().addListener(new ChangeListener<AgeGroup>() {
            @Override
            public void changed(ObservableValue<? extends AgeGroup> observable, AgeGroup oldValue, AgeGroup newValue) {
                if(newValue != null) {
                    updateEventTypeList(newValue);
                }
            }
        });

        comboEvents.valueProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                AgeGroup sel = comboAgeGroups.getSelectionModel().getSelectedItem();
                if(newValue != null && sel != null) {
                    updateFilteredChildren(sel, newValue);
                }
            }
        });
        comboRegisterEvent.setItems(modelEvents);
        try {
            modelEvents.setAll(server.getAllEvents());
        } catch (CompException e) {
            throw new RuntimeException(e);
        }

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));

        try {
            openGroupsView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    Stage groupsStage = null;
    public void onLogoutButtonClick(ActionEvent actionEvent) throws IOException, CompException {
        System.out.println("Logging out ...");

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        if(groupsStage != null) {
            groupsStage.close();
        }

        stage.close();
        server.logout(organiser, this);
    }

    public void onRegisterButtonClick(ActionEvent actionEvent) {
        String name = textName.getText();
        Integer age = spinner.getValue();
        Event event = comboRegisterEvent.getValue();
        try {
            server.register(name, age, event);
        } catch (RegistrationException | CompException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void childRegistered(Child child, AllowedGroup group) {
        Platform.runLater(() -> {
            System.out.println("Child registered " + child + " | " + group);
            if(eventsByChildren.containsKey(child)) {
                eventsByChildren.put(child, eventsByChildren.get(child) + 1);
            } else {
                eventsByChildren.put(child, 1);
            }

            if(!modelChildren.contains(child)) {
                modelChildren.add(child);
            } else {
                childrenTable.refresh();
            }

            try {
                groupsController.childRegistered(child, group);
            } catch (CompException ignored) {
            }
        });
    }
}
