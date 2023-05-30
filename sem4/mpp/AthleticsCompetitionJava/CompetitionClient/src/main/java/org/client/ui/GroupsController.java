package org.client.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.model.AgeGroup;
import org.model.AllowedGroup;
import org.model.Child;
import org.model.Event;
import org.model.exception.CompException;
import org.services.ICompetitionServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupsController {
    public ListView<AgeGroup> ageGroupsList;
    public ListView<Event> eventList;

    ObservableList<AgeGroup> modelAgeGroup = FXCollections.observableArrayList();
    ObservableList<Event> modelEvent = FXCollections.observableArrayList();

    public void updateEventList(AgeGroup newAgeGroup) {
        modelEvent.clear();
        for(AllowedGroup group : countByGroup.keySet()) {
            if(group.getAgeGroup().equals(newAgeGroup)) {
                modelEvent.add(group.getEvent());
            }
        }
    }

    public void childRegistered(Child child, AllowedGroup event) throws CompException {
        Platform.runLater(() -> {
            countByGroup.put(event, countByGroup.get(event) + 1);
            if(!ageGroupsList.getSelectionModel().isEmpty())
                updateEventList(ageGroupsList.getSelectionModel().getSelectedItem());
        });
    }

    List<Event> allEvents;
    HashMap<AllowedGroup, Integer> countByGroup = new HashMap<>();

    public void reallyInitialize(ICompetitionServices server, List<AgeGroup> allAgeGroupList, List<Event> allEventsList) {
        allEvents = allEventsList;
        modelAgeGroup.setAll(allAgeGroupList);
        try {
            countByGroup = server.getChildrenByEvent();
        } catch (CompException e) {
            throw new RuntimeException(e);
        }

        ageGroupsList.setItems(modelAgeGroup);
        eventList.setItems(modelEvent);

        ageGroupsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AgeGroup>() {
            @Override
            public void changed(ObservableValue<? extends AgeGroup> observable, AgeGroup oldValue, AgeGroup newValue) {
                if(newValue != null) {
                    updateEventList(newValue);
                }
            }
        });

        eventList.setCellFactory(param -> new ListCell<Event>() {
            @Override
            protected void updateItem(Event ev, boolean empty) {
            super.updateItem(ev, empty);

            if (empty || ev == null) {
                setText(null);
            } else {
                for(Map.Entry<AllowedGroup, Integer> entry : countByGroup.entrySet()) {
                    if(entry.getKey().getAgeGroup().equals(ageGroupsList.getSelectionModel().getSelectedItem()) && entry.getKey().getEvent().equals(ev)) {
                        setText(String.format("(%d) ", entry.getValue()) + ev);
                        return;
                    }
                }
            }
            }
        });
    }
}
