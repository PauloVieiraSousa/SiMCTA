package view.decorator.class_decorator;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.ClassController;
import controller.CourseController;
import controller.TeacherController;
import exception.ClassException;
import exception.CourseException;
import exception.DateException;
import exception.TeacherException;
import model.Class;
import model.Course;
import model.Teacher;
import model.datatype.CPF;
import model.datatype.Date;
import view.ClassForm;

public class NewClassDecorator extends ClassDecorator {

	private static final String COULDNT_LOAD_COURSES = "Não foi possível carregar os cursos disponíveis";
	protected static final String CLASS_WAS_SAVED = "Turma aberta com sucesso";
	
	private Class classInstance;
	private Map<String, Integer> coursesMap = new HashMap<String, Integer>();
	private String calculatedEndDate;

	public NewClassDecorator(ClassForm classForm) {
		super(classForm);
	}

	@Override
	public void createLabelsAndFields(JFrame frame, Class classInstance) {
		
		this.classInstance = classInstance;
		
		super.createLabelsAndFields(frame, classInstance);
		
		fillTeachersDropdown();
		
		titleLbl = new JLabel("Abrir nova turma");
		titleLbl.setFont(new Font("Dialog", Font.BOLD, 18));
		titleLbl.setBounds(410, 0, 246, 30);
		frame.getContentPane().add(titleLbl);
			
		availableTeachers = new JComboBox<String>(availableTeachersModel);
		availableTeachers.setBounds(286, 188, 250, 24);
		frame.getContentPane().add(availableTeachers);
		availableTeachers.setEditable(true);		
		
		fillCoursesDropdown();
				
		availableCourses = new JComboBox<String>(availableCoursesModel);
		availableCourses.setBounds(286, 122, 250, 24);
		frame.getContentPane().add(availableCourses);
		availableCourses.setEditable(true);
			
		fillShiftsDropdown();

		shifts = new JComboBox<String>(shiftsModel);
		shifts.setBounds(600, 122, 121, 24);
		frame.getContentPane().add(shifts);
		
		classIdField = new JTextField();
		classIdField.setBounds(286, 65, 250, 24);
		frame.getContentPane().add(classIdField);
		classIdField.setColumns(10);
		classIdField.setText("");
		classIdField.setEditable(false);
		classIdField.setEnabled(false);
		
	}



	@Override
	public void createMasks(JFrame frame) {
		super.createMasks(frame);
		
		// Mask for birth date
		MaskFormatter startDateMask;
		
		try{
			startDateMask = new MaskFormatter("##/##/####");
			startDateMask.setValidCharacters("0123456789");
			startDateMask.setValueContainsLiteralCharacters(true);
						
			startDateField = new JFormattedTextField(startDateMask);
			startDateField.setBounds(600, 189, 150, 24);
			frame.getContentPane().add(startDateField);
			startDateField.setColumns(10);
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void createButtons(JFrame frame) {
		
		super.createButtons(frame);
		
		final JButton confirmData = new JButton("Confirmar Dados");
		confirmData.setBounds(415, 237, 140, 30);
		frame.getContentPane().add(confirmData);
		confirmData.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e){		
				calculateEndDate();
				generateClassId();			
				confirmData.setVisible(false);
			}

			private void calculateEndDate() {
				// TODO Auto-generated method stub
				
			}

			private void generateClassId() {
				
				
				classIdField.setText("");
			}
				
		});
		
		actionBtn = new JButton("Abrir Turma");
		actionBtn.setBounds(415, 237, 140, 30);
		frame.getContentPane().add(actionBtn);
		actionBtn.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseClicked(MouseEvent e){			
				
				String message = "";
				
				try{
					String shift = (String) shifts.getSelectedItem();
					
					String selectedTeacher = (String) availableTeachers.getSelectedItem();
					CPF teacherCpf = teachersMap.get(selectedTeacher);
					
					String selectedCourse = (String) availableCourses.getSelectedItem();
					Integer courseId = coursesMap.get(selectedCourse);
					
					String givenStartDate = startDateField.getText();
					
					Date startDate;
					
					startDate = new Date(givenStartDate);
					
					ClassController classController = new ClassController();
					classController.newClass(teacherCpf, shift, startDate, courseId);
					
					message = CLASS_WAS_SAVED;
				}
				catch(DateException e1){
					message = e1.getMessage();
				}finally{
					showInfoMessage(message);
				}
			}
		});
		
	}
	
	private void fillTeachersDropdown(){
		
		TeacherController teacherController = new TeacherController();
		
		availableTeachersModel = new DefaultComboBoxModel<String>();
				
		try{
			ArrayList<Teacher> teachers = teacherController.getTeachers();
			
			for(Teacher teacher : teachers){
				availableTeachersModel.addElement(teacher.getName());
				teachersMap.put(teacher.getName(), teacher.getCpf());
			}
		}
		catch(TeacherException e){
			showInfoMessage(COULDNT_LOAD_TEACHERS);
			availableTeachersModel.addElement(classInstance.getTeacher().getName());
		}
	}
	

	private void fillCoursesDropdown() {
		CourseController courseController = new CourseController();
		
		availableCoursesModel = new DefaultComboBoxModel<String>();
				
		try{
			ArrayList<Course> courses = courseController.showCourse();
			
			for(Course course: courses){
				availableCoursesModel.addElement(course.getName());
				coursesMap .put(course.getName(), course.getId());
			}
		}
		catch (CourseException e) {
			showInfoMessage(COULDNT_LOAD_COURSES);
		}
		
	}
	
	private void fillShiftsDropdown(){
		
		shiftsModel = new DefaultComboBoxModel<String>();
		
		shiftsModel.addElement(Class.MORNING_SHIFT);
		shiftsModel.addElement(Class.AFTERNOON_SHIFT);
		shiftsModel.addElement(Class.NIGHT_SHIFT);
	}

}
