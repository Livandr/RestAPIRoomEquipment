package com.technobel.restapiroomequipment.utils;

import com.technobel.restapiroomequipment.models.entities.Address;
import com.technobel.restapiroomequipment.models.entities.Equipment;
import com.technobel.restapiroomequipment.models.entities.Request;
import com.technobel.restapiroomequipment.models.entities.Room;
import com.technobel.restapiroomequipment.models.entities.users.Admin;
import com.technobel.restapiroomequipment.models.entities.users.Person;
import com.technobel.restapiroomequipment.models.entities.users.Student;
import com.technobel.restapiroomequipment.models.entities.users.Teacher;
import com.technobel.restapiroomequipment.repositories.EquipmentRepository;
import com.technobel.restapiroomequipment.repositories.PersonRepository;
import com.technobel.restapiroomequipment.repositories.RequestRepository;
import com.technobel.restapiroomequipment.repositories.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Component
@Log4j2
public class DataInit implements InitializingBean {
    private final RequestRepository requestRepository;
    private final RoomRepository roomRepository;
    private final EquipmentRepository equipmentRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder encoder;


    public DataInit(PersonRepository personRepository,
                    EquipmentRepository equipmentRepository,
                    RoomRepository roomRepository,
                    RequestRepository requestRepository,
                    PasswordEncoder encoder){
        this.personRepository = personRepository;
        this.equipmentRepository = equipmentRepository;
        this.roomRepository = roomRepository;
        this.requestRepository = requestRepository;
        this.encoder = encoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("-- INITIALIZING DB DATA --");


        Admin admin1 = new Admin();
        admin1.setLastname("labelle");
        admin1.setFirstname("michelle");
        admin1.setEmail("ml@athenee.be");
        admin1.setUsername("secretary");
        admin1.setPassword(encoder.encode("test123."));
        admin1.setEnabled(true);

        Teacher teacher1 = new Teacher();
        teacher1.setLastname("leguet");
        teacher1.setFirstname("patrick");
        teacher1.setEmail("pl@athenee.be");
        teacher1.setUsername("director");
        teacher1.setPassword("test123.");
        teacher1.setEnabled(true);

        Student student1 = new Student();
        student1.setLastname("bono");
        student1.setFirstname("ralph");
        student1.setEmail("rb@gmail.com");
        student1.setUsername("rb2022");
        student1.setPassword("12345678");
        student1.setEnabled(true);


        admin1 = personRepository.save(admin1);
        teacher1 = personRepository.save(teacher1);
        student1 = personRepository.save(student1);

        Room room1 = new Room();
        room1.setRoomName("Mario");
        room1.setCapacity(20);
        room1.setTeacherOnly(false);

        Room room2 = new Room();
        room2.setRoomName("Mario 2");
        room2.setCapacity(30);
        room2.setTeacherOnly(false);

        Room room3 = new Room();
        room3.setRoomName("Mario 3");
        room3.setCapacity(15);
        room3.setTeacherOnly(false);

        room1 = roomRepository.save(room1);
        room2 = roomRepository.save(room2);
        room3 = roomRepository.save(room3);

        Equipment equipment1 = new Equipment();
        equipment1.setName("TV 85'");

        Equipment equipment2 = new Equipment();
        equipment2.setName("PC LENOVO");

        Equipment equipment3 = new Equipment();
        equipment3.setName("Projector EPSON");

        equipment1 = equipmentRepository.save(equipment1);
        equipment2 = equipmentRepository.save(equipment2);
        equipment3 = equipmentRepository.save(equipment3);

        Request request1 = new Request();
        request1.setDate(LocalDate.ofEpochDay(13/03/2023));
        request1.setBeginTime(LocalTime.of(9,00,00));
        request1.setEndTime(LocalTime.of(12,30,0));
        request1.setNeededCapacity(12);
        request1.setJustification("Team work");
        request1.setMadeBy(student1);
        request1.setRoom(room3);

        request1 = requestRepository.save(request1);


        log.info("-- DATA INIT FINISHED --");
    }
}
