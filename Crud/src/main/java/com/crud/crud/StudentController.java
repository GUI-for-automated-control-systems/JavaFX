package com.crud.crud;

import com.crud.crud.data.AppQuery;
import com.crud.crud.model.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;


public class StudentController {

    private Student student;

    @FXML
    private TextField fieldFirstName;

    @FXML
    private TextField fieldLastName;

    @FXML
    private TextField fieldMiddletName;

    @FXML
    private static Text DBMassage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    public TableView<Student> tableView;

    @FXML
    public TableColumn<Student, Integer> colId;

    @FXML
    public TableColumn<Student, String> colFN;

    @FXML
    public TableColumn<Student, String> colMN;

    @FXML
    public TableColumn<Student, String> colLN;

    @FXML
    private MenuBar menuBar;





    @FXML
    private void addStudent(){
        com.crud.crud.model.Student student =  new com.crud.crud.model.Student(fieldFirstName.getText(), fieldMiddletName.getText(), fieldLastName.getText());
        com.crud.crud.data.AppQuery query = new AppQuery();
        query.addStudent(student);
    }

    @FXML
    private void showStudent(){
        com.crud.crud.data.AppQuery query = new com.crud.crud.data.AppQuery();
        ObservableList<Student> list =  query.getStidentList();
        colId.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colFN.setCellValueFactory(new PropertyValueFactory<Student, String>("FirstName"));
        colMN.setCellValueFactory(new PropertyValueFactory<Student, String>("MiddleName"));
        colLN.setCellValueFactory(new PropertyValueFactory<Student, String>("LastName"));
        tableView.setItems(list);
    }

//    @FXML
//    private void mouseClick(MouseEvent e){
//        try{
//            Student student = tableView.getSelectionModel().getSelectedItem();
//            student = new Student(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName());
//            this.student = student;
//            fieldFirstName.setText(student.getFirstName());
//            fieldMiddletName.setText(student.getMiddleName());
//            fieldLastName.setText(student.getLastName());
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }

    @FXML
    void initialize() {
//        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'student.fxml'.";
//        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'student.fxml'.";
//        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'student.fxml'.";
//        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'student.fxml'.";
//        assert fieldFirstName != null : "fx:id=\"fieldFirstName\" was not injected: check your FXML file 'student.fxml'.";
//        assert fieldLastName != null : "fx:id=\"fieldLastName\" was not injected: check your FXML file 'student.fxml'.";
//        assert fieldMiddletName != null : "fx:id=\"fieldMiddletName\" was not injected: check your FXML file 'student.fxml'.";
        showStudent();
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

    }
    @FXML
    public void mouseClick(javafx.scene.input.MouseEvent mouseEvent) {
        try{
            Student student = tableView.getSelectionModel().getSelectedItem();
            student = new Student(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName());
            this.student = student;
            fieldFirstName.setText(student.getFirstName());
            fieldMiddletName.setText(student.getMiddleName());
            fieldLastName.setText(student.getLastName());
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void upadteStudent(){
        try{
            AppQuery query = new AppQuery();
            Student student = new Student(this.student.getId(), fieldFirstName.getText(), fieldMiddletName.getText(), fieldLastName.getText());
            query.updateStudentInfo(student);
            showStudent();
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void deliteSrudent(){
        try{
            AppQuery query = new AppQuery();
            Student student = new Student(this.student.getId(), fieldFirstName.getText(), fieldMiddletName.getText(), fieldLastName.getText());
            query.deleteStudent(student);
            showStudent();
//            btnUpdate.setDisable(true);
//            btnDelete.setDisable(true);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
