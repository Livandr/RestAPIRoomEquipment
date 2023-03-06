package com.technobel.restapiroomequipment.models.forms;

import com.technobel.restapiroomequipment.models.entities.Address;
import com.technobel.restapiroomequipment.models.entities.users.Admin;
import com.technobel.restapiroomequipment.models.entities.users.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserForm {
    @NotBlank
    private String lastname;
    @NotBlank
    private String firstname;
    @NotBlank
    @Email
    private String email;

    private String phone;
    private Address address;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    public Student toEntity(){

        Student student = new Student();

        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setUsername(username);
        student.setPassword(password);
        student.setEmail(email);
        student.setPhone(phone);
        student.setAddress(address);
        student.setEnabled(true);

        return student;
    }


}
