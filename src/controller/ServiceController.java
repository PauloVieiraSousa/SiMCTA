package controller;

import java.util.ArrayList;

import dao.ServiceDAO;
import exception.CourseException;
import exception.DateException;
import exception.PaymentException;
import exception.ServiceException;
import model.Payment;
import model.Service;
import model.Student;
import model.datatype.CPF;

public class ServiceController {
	
	public void newService(Student student, ArrayList<String> courses, ArrayList<String> packages,
						   int paymentType, int paymentForm){
		
		try{
			Service service = new Service(student, courses, packages);
			
			PaymentController paymentController = new PaymentController();
			Payment payment = paymentController.newPayment(service, paymentType, paymentForm);
			
			service.addPayment(payment);
		}
		catch(ServiceException e){
			
		}
		catch(PaymentException e){
		}
	}

	public ArrayList<Service> searchService(Student basicDataOfStudent) throws CourseException, DateException, ServiceException {
		
		ArrayList<Service> services = new ArrayList<Service>();
		ServiceDAO serviceDao = new ServiceDAO();
		
		services = serviceDao.get(basicDataOfStudent);
		
		return services;
	
	}
	
	
}
