package com.suzen.data.DAo;

import com.suzen.data.model.Student;
import com.suzen.data.utilities.DataConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentDAO implements DAO<Student, UUID> {
    private static final Logger logs = Logger.getLogger(StudentDAO.class);

    private static final String get_All="select * from students";
    private static final String getID="select * from students where studentID=?";
    private static final String createStudent="INSERT INTO students (studentID, name, email, course, marks) VALUES (?, ?, ?, ?, ?)";
    private static final Connection connection=DataConnection.getConnect();
    private static final String updateStudent="UPDATE students set name=?,email=?,course=?,marks=?";
    private static final String deleteStudent="DELETE from students where studentID=?";

  //get all students
    @Override
    public List<Student> getAll() {
        List<Student> students=new ArrayList<>();
        try(Statement statement=connection.createStatement()){
            ResultSet set=statement.executeQuery((get_All));
            students=this.processResults(set);
        } catch (SQLException e) {
            DataConnection.handleSQLException("StudentDAO.getAll",e,logs);
        }

        return students;
    }
    //create a new student
    @Override
    public Student create(Student entity) {
        UUID id= UUID.randomUUID();
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement= connection.prepareStatement(createStudent);
            statement.setObject(1,id.toString());
            statement.setObject(2,entity.getName());
            statement.setObject(3,entity.getEmail());
            statement.setObject(4,entity.getCourse());
            statement.setObject(5,entity.getMarks());
            statement.execute();
            connection.commit();
        }catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                DataConnection.handleSQLException("StudentDAO.create.rollback",e,logs);
            }
            DataConnection.handleSQLException("StudentDAO.create",e,logs);

        }
        Optional<Student> st=this.getOne(UUID.fromString(id.toString()));
        return st.orElse(null);

    }

    //get student by id
    @Override
    public Optional<Student> getOne(UUID uuid) {

        try(PreparedStatement statement=connection.prepareStatement(getID))
        {
            statement.setObject(1,uuid.toString());
            ResultSet resultSet=statement.executeQuery();
            List<Student> students=this.processResults(resultSet);
            if(students.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(students.get(0));
        } catch (SQLException e) {
            DataConnection.handleSQLException("StudentDAO.getOne",e,logs);
        }
        return Optional.empty();
    }

    //update student
    @Override
    public Student update(Student entity) {
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement=connection.prepareStatement(updateStudent);
            statement.setObject(1,entity.getName());
            statement.setObject(2,entity.getEmail());
            statement.setObject(3,entity.getCourse());
            statement.setObject(4,entity.getMarks());
            statement.execute();
            connection.commit();
        }catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                DataConnection.handleSQLException("StudentDAO.update.rollback",e,logs);
            }
            DataConnection.handleSQLException("StudentDAO.update",e,logs);

        }

        return this.getOne(entity.getStudentID()).get();
    }

    //delete student
    @Override
    public void delete(UUID uuid) {

        try{
            connection.setAutoCommit(false);
            PreparedStatement statement=connection.prepareStatement(deleteStudent);
            statement.setObject(1,uuid);
            statement.executeUpdate();
            connection.commit();

        }catch (SQLException e) {
            try{
                connection.rollback();
            } catch (SQLException ex) {
                DataConnection.handleSQLException("StudentDAO.delete.rollback",e,logs);
            }
            DataConnection.handleSQLException("StudentDAO.delete",e,logs);

        }

    }


    private List<Student> processResults(ResultSet rs) throws SQLException{
        List<Student> studends=new ArrayList<>();

        while (rs.next()){
            Student student=new Student();
            student.setStudentID(UUID.fromString(rs.getString("studentID")));            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setCourse(rs.getString("course"));
            student.setMarks(rs.getInt("marks"));
            studends.add(student);
        }
        return studends;
    }
}
