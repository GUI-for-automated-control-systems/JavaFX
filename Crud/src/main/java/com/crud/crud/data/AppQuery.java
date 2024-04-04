package com.crud.crud.data;

import com.crud.crud.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class AppQuery {
    private DBConnection c = new DBConnection();

    public void addStudent(com.crud.crud.model.Student student){
        try {
        c.getDBConnect();
        java.sql.PreparedStatement ps = c.getConnection().prepareStatement("insert into student_list(FirstName,MiddleName,LastName)values(?,?,?)");
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getMiddleName());
        ps.setString(3, student.getLastName());
        System.out.println("Log -> Add student: " + student);

        ps.execute();
        ps.close();
        c.closeConnection();
        }catch (Exception e){
            System.out.println("Error to add data on db: " + e.getMessage());;
        }
    }

    public void updateStudentInfo(Student student){
        try {
            c.getDBConnect();
            java.sql.PreparedStatement ps = c.getConnection().prepareStatement("UPDATE `student_list` SET `FirstName` = ?, `MiddleName` = ?, `LastName` = ? WHERE id = ? ");
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getMiddleName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getId().toString());
            ps.execute();
            ps.close();
            System.out.println("Log -> Update student: " + student);
            DBConnection.closeConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteStudent(Student student) {
        try {
            c.getDBConnect();
            java.sql.PreparedStatement ps = c.getConnection().prepareStatement("DELETE FROM `student_list` WHERE `id` = ?");
            ps.setInt(1, student.getId());
            ps.execute();
            ps.close();
            DBConnection.closeConnection();
            System.out.println("Log -> Delete student: " + student);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public ObservableList <com.crud.crud.model.Student> getStidentList(){
        ObservableList <com.crud.crud.model.Student> studentList = FXCollections.observableArrayList();
        try {
            String query = "select id, FirstName, MiddleName, LastName from student_list order by LastName";
            c.getDBConnect();
            Statement st = c.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            com.crud.crud.model.Student s;
            while (rs.next()){
                s = new com.crud.crud.model.Student(rs.getInt("id"), rs.getString("FirstName"), rs.getString("MiddleName"), rs.getString("LastName"));
                studentList.add(s);
            }
            rs.close();
            st.close();
            c.closeConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }


}
