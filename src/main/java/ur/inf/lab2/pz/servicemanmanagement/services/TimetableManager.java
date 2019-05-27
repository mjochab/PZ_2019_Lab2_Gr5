package ur.inf.lab2.pz.servicemanmanagement.services;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.timetable.AllocatedTaskRaportPrinter;
import ur.inf.lab2.pz.servicemanmanagement.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.TimetableTaskEditDialogData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.impl.ManagerTimetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.impl.ServicemanTimetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.impl.UnallocatedTaskTableItem;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.ClientData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.TimetableDatasource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TimetableManager {

    @Autowired
    private TimetableDatasource datasource;


    public TreeItem<UnallocatedTaskTableItem> getUnallocatedTasksAsTreeItem() {
        Set<UnallocatedTaskTableItem> unallocatedTasks = datasource.getUnallocatedTasks().stream()
                .map(UnallocatedTaskTableItem::new)
                .collect(Collectors.toSet());

        ObservableList<UnallocatedTaskTableItem> unallocatedTasksList = FXCollections.observableArrayList(unallocatedTasks);
        return new RecursiveTreeItem<>(unallocatedTasksList, RecursiveTreeObject::getChildren);
    }

    public TreeItem<UnallocatedTaskTableItem> convertToUnallocatedTreeItemTask(Task newTask) {
        TaskConverter taskConverter = new TaskConverter();
        return new TreeItem(
                new UnallocatedTaskTableItem(
                        taskConverter.convertToUnallocated(newTask)
                )
        );
    }

    public Timetable createTimetable(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable, StackPane rootStackPane, Parent editTaskDialogBody) {
        TimetableTaskEditDialogData dialogData = new ManagerTaskEditDialogData(editTaskDialogBody, rootStackPane);

        return new ManagerTimetable(unallocatedTaskTable, dialogData, new MockPrinter());
    }

    public Set<AllocatedTask> getAllAllocatedTasks(Long leaderId) {
        return datasource.getAllocatedTasks(leaderId);
    }

    public void save(Long leaderId, Set<AllocatedTask> allocatedTasks, Set<UnallocatedTask> unallocatedTasks) {
        datasource.saveAllocated(leaderId, allocatedTasks);
        datasource.saveUnallocated(unallocatedTasks);
    }

    public void saveAllocated(Long leaderId, Set<AllocatedTask> allocatedTasks) {
        datasource.saveAllocated(leaderId, allocatedTasks);
    }

    public List<GroupData> getAllGroups() {
        return datasource.getGroups();
    }

    public Timetable createServicemanTimetable(StackPane rootStackPane, Parent editTaskDialog) {
        TimetableTaskEditDialogData dialogData = new ManagerTaskEditDialogData(editTaskDialog, rootStackPane);

        return new ServicemanTimetable(dialogData, new MockPrinter());
    }



    private class MockPrinter implements AllocatedTaskRaportPrinter {

        @Override
        public void print(AllocatedTask taskToPrint, String absolutePath) {
            System.out.println("Save task: " + taskToPrint + " to path: " + absolutePath);
        }
    }

    private class ManagerTaskEditDialogData implements TimetableTaskEditDialogData {

        private static final String EXIT_NODE_ID = "exit";
        private static final String TASK_ID_NODE_ID = "taskId";
        private static final String TASK_TAG_NODE_ID = "taskTag";
        private static final String DESCRIPTION_NODE_ID = "taskDescription";
        private static final String DATE_FROM_NODE_ID = "dateFrom";
        private static final String TIME_FROM_NODE_ID = "timeFrom";
        private static final String DATE_TO_NODE_ID = "dateTo";
        private static final String TIME_TO_NODE_ID = "timeTo";
        private static final String WHOLE_DAY_NODE_ID = "wholeDay";
        private static final String DETACH_TASK_NODE_ID = "detachTask";
        private static final String SAVE_TASK_NODE_ID = "saveTask";
        private static final String PRINT_NODE_ID = "print";
        private static final String CLIENT_FIRSTNAME_LABEL_ID = "clientFirstname";
        private static final String CLIENT_SURNAME_LABEL_ID = "clientSurname";
        private static final String CLIENT_PHONE_NUMBER_LABEL_ID = "clientPhoneNumber";
        private static final String CLIENT_STREET_LABEL_ID = "clientStreet";
        private static final String CLIENT_HOUSE_NUMBER_LABEL_ID = "clientHouseNumber";
        private static final String CLIENT_FLAT_NUMBER_LABEL_ID = "clientFlatNumber";
        private static final String CLIENT_CITY_LABEL_ID = "clientCity";
        private static final String STATE_BUTTON_ID = "stateButton";
        private Parent dialogBody;
        private StackPane stackPaneForDialog;

        public ManagerTaskEditDialogData(Parent dialogBody, StackPane stackPane) {
            this.dialogBody = dialogBody;
            this.stackPaneForDialog = stackPane;

            initTaskDurationChanges();
        }

        private void initTaskDurationChanges() {
            CheckBox wholeDayCheckbox = (CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID);
            wholeDayCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    disableNodeById(DATE_TO_NODE_ID);
                    disableNodeById(TIME_TO_NODE_ID);
                    disableNodeById(TIME_FROM_NODE_ID);
                } else {
                    enableNodeById(DATE_TO_NODE_ID);
                    enableNodeById(TIME_TO_NODE_ID);
                    enableNodeById(TIME_FROM_NODE_ID);
                }
            });

        }

        private void enableNodeById(String timeFromNodeId) {
            findNodeInDialogBody(timeFromNodeId).setDisable(false);
        }

        private void disableNodeById(String dateToNodeId) {
            findNodeInDialogBody(dateToNodeId).setDisable(true);
        }

        @Override
        public StackPane getStackPane() {
            return stackPaneForDialog;
        }

        @Override
        public Node getView() {
            return dialogBody;
        }

        @Override
        public Node getExitNode() {
            return findNodeInDialogBody(EXIT_NODE_ID);
        }

        private Node findNodeInDialogBody(String nodeId) {
            return dialogBody.lookup("#" + nodeId);
        }

        @Override
        public void setTaskId(String id) {
            ((Label) findNodeInDialogBody(TASK_ID_NODE_ID)).setText(id);
        }

        @Override
        public void setTaskTag(String tag) {
            ((Label) findNodeInDialogBody(TASK_TAG_NODE_ID)).setText(tag);
        }

        @Override
        public String getTaskDescription() {
            return ((TextArea) findNodeInDialogBody(DESCRIPTION_NODE_ID)).getText();
        }

        @Override
        public void setTaskDescription(String description) {
            ((TextArea) findNodeInDialogBody(DESCRIPTION_NODE_ID)).setText(description);
        }

        @Override
        public LocalDateTime getDateTimeFrom() {
            LocalDate date = ((DatePicker) findNodeInDialogBody(DATE_FROM_NODE_ID)).getValue();
            LocalTime time = ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_FROM_NODE_ID)).getValue();
            return LocalDateTime.of(date, time);
        }

        @Override
        public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
            ((DatePicker) findNodeInDialogBody(DATE_FROM_NODE_ID)).setValue(dateTimeFrom.toLocalDate());
            ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_FROM_NODE_ID)).setValue(dateTimeFrom.toLocalTime());
        }

        @Override
        public LocalDateTime getDateTimeTo() {
            LocalDate date = ((DatePicker) findNodeInDialogBody(DATE_TO_NODE_ID)).getValue();
            LocalTime time = ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_TO_NODE_ID)).getValue();
            return LocalDateTime.of(date, time);
        }

        @Override
        public void setDateTimeTo(LocalDateTime dateTimeTo) {
            ((DatePicker) findNodeInDialogBody(DATE_TO_NODE_ID)).setValue(dateTimeTo.toLocalDate());
            ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_TO_NODE_ID)).setValue(dateTimeTo.toLocalTime());
        }

        @Override
        public Node getPrintNode() {
            return findNodeInDialogBody(PRINT_NODE_ID);
        }

        @Override
        public void setClientData(ClientData clientData) {
            setLabelNode(CLIENT_FIRSTNAME_LABEL_ID, clientData.getFirstname());
            setLabelNode(CLIENT_SURNAME_LABEL_ID, clientData.getSurname());
            setLabelNode(CLIENT_PHONE_NUMBER_LABEL_ID, clientData.getPhoneNumber());
            setLabelNode(CLIENT_STREET_LABEL_ID, clientData.getStreet());
            setLabelNode(CLIENT_HOUSE_NUMBER_LABEL_ID, clientData.getHouseNumber());
            setLabelNode(CLIENT_FLAT_NUMBER_LABEL_ID, clientData.getFlatNumber());
            setLabelNode(CLIENT_CITY_LABEL_ID, clientData.getCity());
        }

        @Override
        public void setState(String state) {
            ((Button) findNodeInDialogBody(STATE_BUTTON_ID)).setText(state);
        }

        @Override
        public void disableDetachNode() {
            getDetachNode().setDisable(true);
        }

        @Override
        public void disableDescriptionNode() {
            findNodeInDialogBody(DESCRIPTION_NODE_ID).setDisable(true);
        }

        @Override
        public void disableDateFromNode() {
            findNodeInDialogBody(DATE_FROM_NODE_ID).setDisable(true);
        }

        @Override
        public void disableTimeFromNode() {
            findNodeInDialogBody(TIME_FROM_NODE_ID).setDisable(true);
        }

        @Override
        public void disableDateToNode() {
            findNodeInDialogBody(DATE_TO_NODE_ID).setDisable(true);
        }

        @Override
        public void disableTimeToNode() {
            findNodeInDialogBody(TIME_TO_NODE_ID).setDisable(true);
        }

        @Override
        public void disableWholeDayNode() {
            findNodeInDialogBody(WHOLE_DAY_NODE_ID).setDisable(true);
        }

        @Override
        public void disableSaveNode() {
            findNodeInDialogBody(SAVE_TASK_NODE_ID).setDisable(true);
        }

        @Override
        public void clean() {
            enableNodes(
                    DETACH_TASK_NODE_ID,
                    DESCRIPTION_NODE_ID,
                    DATE_FROM_NODE_ID,
                    TIME_FROM_NODE_ID,
                    DATE_TO_NODE_ID,
                    TIME_TO_NODE_ID,
                    WHOLE_DAY_NODE_ID,
                    SAVE_TASK_NODE_ID);
        }

        private void enableNodes(String... ids) {
            for (String id : ids)
                findNodeInDialogBody(id).setDisable(false);
        }

        private void setLabelNode(String nodeId, String value) {
            ((Label) findNodeInDialogBody(nodeId)).setText(value);
        }

        @Override
        public boolean isWholeDayTask() {
            return ((CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID)).isSelected();
        }

        @Override
        public void setWholeDay(boolean wholeDayTask) {
            ((CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID)).setSelected(wholeDayTask);
        }

        @Override
        public Node getDetachNode() {
            return findNodeInDialogBody(DETACH_TASK_NODE_ID);
        }

        @Override
        public Node getSaveNode() {
            return findNodeInDialogBody(SAVE_TASK_NODE_ID);
        }


    }
}
