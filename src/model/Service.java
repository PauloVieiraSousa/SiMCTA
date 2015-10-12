package model;

import java.util.ArrayList;

import controller.CourseController;
import controller.PackageController;
import exception.ServiceException;
import model.Package;

public class Service extends Model{
	
	private static final String STUDENT_OF_SERVICE_CANT_BE_NULL = "O serviço deve ser vinculado a um estudante. Informe um estudante válido.";
	private static final String SERVICE_MUST_CONTAIN_AT_LEAST_A_COURSE_OR_PACKAGE = "O estudante deve estar vinculado a um curso ou pacote, pelo menos.";
	
	private Integer serviceId; 
	private Student student;
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Package> packages = new ArrayList<Package>();
	
	public Service(Student student, ArrayList<String> courses, ArrayList<String> packages) throws ServiceException{
		
		boolean thereIsCourses = courses != null && !courses.isEmpty();
		boolean thereIsPackages = packages != null && !packages.isEmpty(); 
		
		// At least one of it must contains something
		if(thereIsCourses || thereIsPackages){
		
			setStudent(student);
			addCoursesToService(courses);
			addPackagesToService(packages);
		}
		else{
			throw new ServiceException(SERVICE_MUST_CONTAIN_AT_LEAST_A_COURSE_OR_PACKAGE);
		}
	}
	
	private void addPackagesToService(ArrayList<String> packages){
		
		if(packages != null){
		
			boolean isNotEmpty = !packages.isEmpty(); 
			
			if(isNotEmpty){
				
				PackageController packageController = new PackageController();
				
				int i = 0;
				for(i = 0; i < packages.size(); i++){
					
					int packageId = Integer.parseInt(packages.get(i));
					
					Package currentPackage;
					
					currentPackage = packageController.getPackage(packageId);
					
					if(currentPackage != null){
						this.packages.add(currentPackage);
					}else{
						// Nothing to do because the package is invalid
					}
				}
			}
			else{
				// Nothing to do because there is no packages contracted
			}
		}
		else{
			// Nothing to do because the packages array is already empty
		}
	}
	
	private void addCoursesToService(ArrayList<String> courses){
		
		if(courses != null){
			
			boolean isNotEmpty = !courses.isEmpty(); 
			
			if(isNotEmpty){
				
				CourseController courseController = new CourseController();
				
				int i = 0;
				for(i = 0; i < courses.size(); i++){
					
					int courseId = Integer.parseInt(courses.get(i));
					Course course;
					
					course = courseController.get(courseId);
					
					if(course != null){
						this.courses.add(course);
					}else{
						// Nothing to do because the course is invalid
					}
				}
			}else{
				// Nothing to do because there is no courses contracted
			}
		}
		else{
			// Nothing to do because the course array is already empty
		}
	}
	
	private void setStudent(Student student) throws ServiceException{
		
		if(student != null){
			this.student = student;
		}
		else{
			throw new ServiceException(STUDENT_OF_SERVICE_CANT_BE_NULL);
		}
	}

	public Integer getServiceId(){
		return this.serviceId;
	}

	public Student getStudent(){
		return this.student;
	}

	public ArrayList<Course> getCourses(){
		return this.courses;
	}

	public ArrayList<Package> getPackages(){
		return this.packages;
	}
}
