package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Client;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.repository.ServicemanRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;
import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.*;
import ur.inf.lab2.pz.servicemanmanagement.timetable.TimetableDatasource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimetableTaskService implements TimetableDatasource {

    @Autowired private TaskRepository taskRepository;
    @Autowired private ServicemanRepository usersRepository;
    @Autowired private ServicemanRepository servicemanRepository;

    private TaskConverter taskConverter = new TaskConverter();

    @Override
    public Set<AllocatedTask> getAllocatedTasks(Long leaderId) {
        List<Task> allocatedEntities = taskRepository.findAllocated(leaderId);

        return allocatedEntities.stream()
                .map(entity -> taskConverter.convertToAllocated(entity))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UnallocatedTask> getUnallocatedTasks() {
        List<Task> unallocatedEntities = taskRepository.findUnallocated();

        return unallocatedEntities.stream()
                .map(entity -> taskConverter.convertToUnallocated(entity))
                .collect(Collectors.toSet());
    }

    @Override
    public void saveAllocated(Long leaderId, Collection<AllocatedTask> allocatedTasks) {
        Serviceman groupLeader = usersRepository.findById(leaderId).orElseThrow(() -> new IllegalStateException());

        allocatedTasks.forEach(task -> {
            Long entityId = taskConverter.convertTagIdToEntityId(task.getId(), task.getTag());

            Task taskEntity = taskRepository.findById(entityId).orElseThrow(() -> new IllegalStateException());
            taskEntity.setLeader(groupLeader);
            taskEntity.setDescription(task.getDetails());
            taskEntity.setDateTimeFrom(task.getDateTimeFrom());
            taskEntity.setDateTimeTo(task.getDateTimeTo());
            taskEntity.setState(task.getState());
            taskEntity.setWholeDayTask(task.isWholeDayTask());

            taskRepository.save(taskEntity);
        });
    }

    @Override
    public void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks) {
        unallocatedTasks.forEach(task -> {
            Long entityId = taskConverter.convertTagIdToEntityId(task.getId(), task.getTag());

            Task taskEntity = taskRepository.findById(entityId).orElseThrow(() -> new IllegalStateException());
            taskEntity.setLeader(null);
            taskEntity.setDescription(task.getDetails());
            taskEntity.setDateTimeFrom(null);
            taskEntity.setDateTimeTo(null);
            taskEntity.setState(TaskState.TODO);
            taskEntity.setWholeDayTask(false);

            taskRepository.save(taskEntity);
        });
    }

    @Override
    public List<GroupData> getGroups() {
        return servicemanRepository.findAllActivated().stream()
                .map(serviceman -> new GroupData(serviceman.getId(), serviceman.getGroupName()))
                .collect(Collectors.toList());
    }

}


class TaskConverter {

    public UnallocatedTask convertToUnallocated(Task entity) {
        UnallocatedTaskImpl unallocatedTask = new UnallocatedTaskImpl();
        unallocatedTask.id = createTagId(entity.getId(), entity.getTag());
        unallocatedTask.tag = entity.getTag();
        unallocatedTask.description = entity.getDescription();
        unallocatedTask.clientData = convertToClientData(entity.getClient());
        return unallocatedTask;
    }

    public AllocatedTask convertToAllocated(Task entity) {
        AllocatedTaskImpl allocatedTask = new AllocatedTaskImpl();
        allocatedTask.id = createTagId(entity.getId(), entity.getTag());
        allocatedTask.tag = entity.getTag();
        allocatedTask.details = entity.getDescription();
        allocatedTask.clientData = convertToClientData(entity.getClient());
        allocatedTask.dateTimeFrom = entity.getDateTimeFrom();
        allocatedTask.dateTimeTo = entity.getDateTimeTo();
        allocatedTask.isWholeDayTask = entity.isWholeDayTask();
        allocatedTask.state = entity.getState();

        return allocatedTask;
    }

    public String createTagId(Long id, String tag) {
        return tag.substring(0, 1) + id;
    }

    public Long convertTagIdToEntityId(String tagId, String tag) {
        return Long.parseLong(
                tagId.toLowerCase()
                        .replace(tag.toLowerCase().substring(0, 1), "")
        );
    }

    public ClientData convertToClientData(Client client) {
        return new ClientDataImpl(
                client.getFirstName(),
                client.getLastName(),
                client.getStreet(),
                String.valueOf(client.getHouseNumber()),
                String.valueOf(client.getApartmentNumber()),
                client.getCity(),
                String.valueOf(client.getPhoneNumber()));
    }



    private class AllocatedTaskImpl implements AllocatedTask {
        public String id;
        public String tag;
        public String details;
        public ClientData clientData;
        public LocalDateTime dateTimeFrom;
        public LocalDateTime dateTimeTo;
        public boolean isWholeDayTask;
        public TaskState state;


        @Override
        public LocalDateTime getDateTimeFrom() {
            return dateTimeFrom;
        }

        @Override
        public LocalDateTime getDateTimeTo() {
            return dateTimeTo;
        }

        @Override
        public boolean isWholeDayTask() {
            return isWholeDayTask;
        }

        @Override
        public TaskState getState() {
            return state;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public String getDetails() {
            return details;
        }

        @Override
        public ClientData getClientData() {
            return clientData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AllocatedTaskImpl that = (AllocatedTaskImpl) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    private class ClientDataImpl implements ClientData{

        private String firstName;
        private String lastName;
        private String street;
        private String houseNr;
        private String apartmentNumber;
        private String city;
        private String phone;

        public ClientDataImpl(String firstName, String lastName, String street, String houseNr, String apartmentNumber, String city, String phone) {

            this.firstName = firstName;
            this.lastName = lastName;
            this.street = street;
            this.houseNr = houseNr;
            this.apartmentNumber = apartmentNumber;
            this.city = city;
            this.phone = phone;
        }

        @Override
        public String getFirstname() {
            return firstName;
        }

        @Override
        public String getSurname() {
            return lastName;
        }

        @Override
        public String getPhoneNumber() {
            return phone;
        }

        @Override
        public String getStreet() {
            return street;
        }

        @Override
        public String getHouseNumber() {
            return houseNr;
        }

        @Override
        public String getFlatNumber() {
            return apartmentNumber;
        }

        @Override
        public String getCity() {
            return city;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientDataImpl that = (ClientDataImpl) o;
            return Objects.equals(firstName, that.firstName) &&
                    Objects.equals(lastName, that.lastName) &&
                    Objects.equals(street, that.street) &&
                    Objects.equals(houseNr, that.houseNr) &&
                    Objects.equals(apartmentNumber, that.apartmentNumber) &&
                    Objects.equals(city, that.city) &&
                    Objects.equals(phone, that.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, street, houseNr, apartmentNumber, city, phone);
        }
    }

    private class UnallocatedTaskImpl implements UnallocatedTask {
        public String id;
        public String tag;
        public String description;
        public ClientData clientData;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public String getDetails() {
            return description;
        }

        @Override
        public ClientData getClientData() {
            return clientData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UnallocatedTaskImpl that = (UnallocatedTaskImpl) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }



}