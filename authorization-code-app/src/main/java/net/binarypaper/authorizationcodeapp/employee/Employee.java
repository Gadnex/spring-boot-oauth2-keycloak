package net.binarypaper.authorizationcodeapp.employee;

import lombok.Data;

@Data
public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String department;
    private String manager;
    private String note;
}