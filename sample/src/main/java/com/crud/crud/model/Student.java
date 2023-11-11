package com.crud.crud.model;

public class Student {
    private Integer id;
    private String FirstName;
    private String LastName;
    private String MiddleName;

    @Override
    public String toString(){
        return ":" + this.FirstName + ", " + this.MiddleName + ", " + this.LastName;
    }

    public Student(Integer id, String firstName, String lastName, String middleName){
        this.id = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.MiddleName = middleName;
    }
    public Student(String firstName, String lastName, String middleName){
        this.FirstName = firstName;
        this.LastName = lastName;
        this.MiddleName = middleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }
}
