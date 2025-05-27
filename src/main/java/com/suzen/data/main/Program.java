package com.suzen.data.main;

import com.suzen.data.DAo.StudentDAO;
import com.suzen.data.model.Student;

import java.util.List;
import java.util.Optional;

public class Program {

    public static void main(String[] args) {
        StudentDAO studentDAO=new StudentDAO();
        Student newStudent=new Student();
        newStudent.setName("Kabelo");
        newStudent.setEmail("kbemai@gmail.com");
        newStudent.setCourse("Java");
        newStudent.setMarks(12);
        newStudent=studentDAO.create(newStudent);
        System.out.println("\n-----Create Student-----\n"+ newStudent);
        List<Student> studentList=studentDAO.getAll();
        System.out.println("\n-----Get_ALL Students-----");
        studentList.forEach(System.out::println);
        Optional<Student> student=studentDAO.getOne(studentList.get(0).getStudentID());
        System.out.println("\n-----Get_One Students-----\n"+ student.get());


    }
}
