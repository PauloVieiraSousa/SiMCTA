package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.PackageDAO;
import dao.StudentDAO;
import exception.AddressException;
import exception.CPFException;
import exception.CourseException;
import exception.DateException;
import exception.PhoneException;
import exception.RGException;
import exception.ServiceException;
import exception.StudentException;
import model.Student;
import model.Service;
import model.datatype.Address;
import model.datatype.Date;
import model.datatype.Phone;
import model.datatype.RG;
import model.datatype.CPF;

public class StudentController {
	
	private static final String STUDENT_WITHOUT_SERVICE = "Um aluno deve possuir um serviço associado";
	private StudentDAO studentDAO;
	
	public StudentController(){
		studentDAO = new StudentDAO();
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}
	
	public void newStudent(String studentName, CPF studentCpf, RG studentRg, Date birthdate, String email, Address address,
			 			   Phone principalPhone, Phone secondaryPhone, String motherName, String fatherName,
			 			   ArrayList<String> courses, ArrayList<String> packages, int paymentType, int paymentForm, Integer installments) throws StudentException{
		
		Student student = new Student(studentName, studentCpf, studentRg, birthdate, email, address, principalPhone, secondaryPhone, motherName, fatherName);
		
		ServiceController serviceController = new ServiceController();
		serviceController.newService(student, courses, packages, paymentType, paymentForm, installments);
	}

	/**
	 * Search the student with the entered name
	 * @param studentName - the entered name by user
	 * @return an arraylist with the found students
	 * @throws StudentException
	 * @throws CPFException
	 */
	public ArrayList<Student> searchStudent(String studentName) throws StudentException, CPFException {
			
		ArrayList <Student> foundStudents = studentDAO.get(studentName);
		
		return foundStudents;
	}

	/**
	 * Search the student selected by the user
	 * @param studentCPF - the 'cpf' of the selected student 
	 * @return an object with the data of the selected student
	 * @throws SQLException
	 * @throws StudentException
	 * @throws PhoneException
	 * @throws CPFException
	 * @throws DateException
	 * @throws AddressException
	 * @throws RGException
	 * @throws CourseException
	 * @throws ServiceException
	 */
	public Student searchStudent(CPF studentCPF) throws SQLException, StudentException, PhoneException, CPFException, DateException, AddressException, RGException, CourseException, ServiceException {
		
		Student basicDataOfStudent = studentDAO.get(studentCPF);
		Student student = null;
		
		if(basicDataOfStudent != null){
			
			ArrayList<Service> servicesOfStudent = new ArrayList<Service>();
			ServiceController serviceController = new ServiceController(); 
			
			servicesOfStudent = serviceController.searchService(basicDataOfStudent);
			
			student = new Student(basicDataOfStudent, servicesOfStudent);

			
		}
		else{
			throw new StudentException(STUDENT_WITHOUT_SERVICE);
		}
				
		
		
		return student;
	}
}