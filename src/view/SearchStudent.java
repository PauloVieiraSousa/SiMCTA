package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Course;
import model.Payment;
import model.PaymentDescription;
import model.Service;
import model.Student;
import model.Package;
import model.datatype.Address;
import model.datatype.CPF;
import model.datatype.Date;
import model.datatype.Phone;
import model.datatype.RG;
import controller.CourseController;
import controller.ServiceController;
import controller.StudentController;
import util.ButtonColumn;
import exception.AddressException;
import exception.CPFException;
import exception.CourseException;
import exception.DateException;
import exception.PaymentException;
import exception.PersonException;
import exception.PhoneException;
import exception.RGException;
import exception.ServiceException;
import exception.StudentException;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;


public class SearchStudent extends View {
	
	private JPanel contentPane;
	private JTextField searchedStudentField;
	private JTable tableOfStudents;
	private DefaultTableModel tableModel;
	private Component scrollPane;
	private JButton searchButtton;
	private JInternalFrame internalFrame;
	private JTextField nameField;
	private JTextField cpfField;
	private JTextField rgField;
	private JTextField cellField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField cepField;
	private JTextField cityField;
	private JTextField emailField;
	private JTextField birthdateField;
	private JTextField motherField;
	private JTextField fatherField;
	private JLabel studentNameLbl;
	private JButton backButton;
	private JLabel firstListLabel;
	private JLabel secondListLabel;
	private JList<String> firstList;
	private JList<String> secondList;
	private JLabel dateLabel;
	private JTextField paymentFormField;
	private JTextField paymentValueField;
	private JLabel label;
	private JTextField textField;
	private JTextField installmentsValueField;
	private JTextField paymentInstallmentsField;
	private JButton deactivateOrActivateButton;
	private Student student;
	private int status;
	private String action;
	
	public SearchStudent(){
		
		super();
		
		createLabelsAndFields();
		
		showStudent();
		
	}

	/**
	 * Used to get the name of the searched student
	 */
	private void showStudent() {
		searchButtton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String searchedStudent = searchedStudentField.getText();
				
				boolean enteredStudent = !searchedStudent.isEmpty();
				
				if(enteredStudent){
					try {
						buildTableWithSearchedStudent(searchedStudent);
					}
					catch(StudentException e1){
						
					} 
					catch(CPFException| PersonException e1){

					} 
				}
				else{
					JOptionPane.showMessageDialog(null, "Digite o nome de um aluno");
				}
			}


		});	

		
	}

	/**
	 * Creates the labels and fields of the frame
	 */
	protected void createLabelsAndFields() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchedStudentField = new JTextField();
		searchedStudentField.setBounds(141, 24, 446, 30);
		contentPane.add(searchedStudentField);
		searchedStudentField.setColumns(10);
		
		searchButtton = new JButton("Pesquisar");
		searchButtton.setBounds(599, 26, 117, 25);
		contentPane.add(searchButtton);
		
		internalFrame = new JInternalFrame();
		internalFrame.setBounds(113, 64, 882, 618);
		contentPane.add(internalFrame);
			
		studentNameLbl = new JLabel("New label");
		studentNameLbl.setBounds(176, 12, 348, 23);
		studentNameLbl.setFont(new Font("Dialog", Font.BOLD, 14));
		internalFrame.getContentPane().setLayout(null);
		internalFrame.getContentPane().add(studentNameLbl);
		
		JLabel dataOfStudentLbl = new JLabel("DADOS DO ALUNO");
		dataOfStudentLbl.setFont(new Font("Dialog", Font.BOLD, 12));
		dataOfStudentLbl.setBounds(186, 53, 150, 17);
		internalFrame.getContentPane().add(dataOfStudentLbl);
		
		JLabel nameLbl = new JLabel("Nome");
		nameLbl.setBounds(30, 73, 70, 17);
		internalFrame.getContentPane().add(nameLbl);
		
		nameField = new JTextField();
		nameField.setBounds(85, 68, 434, 27);
		internalFrame.getContentPane().add(nameField);
		nameField.setColumns(10);
		nameField.setEditable(false);
		
		JLabel cpfLabel = new JLabel("CPF");
		cpfLabel.setBounds(30, 128, 70, 17);
		internalFrame.getContentPane().add(cpfLabel);
		
		JLabel rgLabel = new JLabel("RG");
		rgLabel.setBounds(319, 128, 70, 17);
		internalFrame.getContentPane().add(rgLabel);
		
		cpfField = new JTextField();
		cpfField.setBounds(85, 123, 140, 27);
		internalFrame.getContentPane().add(cpfField);
		cpfField.setColumns(10);
		cpfField.setEditable(false);
		
		rgField = new JTextField();
		rgField.setBounds(359, 123, 140, 27);
		internalFrame.getContentPane().add(rgField);
		rgField.setColumns(10);
		rgField.setEditable(false);

		JLabel birthdateLabel = new JLabel("Data de Nascimento");
		birthdateLabel.setBounds(30, 171, 200, 17);
		internalFrame.getContentPane().add(birthdateLabel);
		
		birthdateField = new JTextField();
		birthdateField.setBounds(30, 195, 190, 27);
		internalFrame.getContentPane().add(birthdateField);
		birthdateField.setColumns(10);
		birthdateField.setEditable(false);
		
		JLabel cellLabel = new JLabel("Celular");
		cellLabel.setBounds(285, 171, 70, 17);
		internalFrame.getContentPane().add(cellLabel);
		
		JLabel phoneLabel = new JLabel("Telefone");
		phoneLabel.setBounds(285, 205, 70, 17);
		internalFrame.getContentPane().add(phoneLabel);
		
		cellField = new JTextField();
		cellField.setBounds(359, 166, 140, 27);
		internalFrame.getContentPane().add(cellField);
		cellField.setColumns(10);
		cellField.setEditable(false);
		
		phoneField = new JTextField();
		phoneField.setBounds(359, 200, 140, 27);
		internalFrame.getContentPane().add(phoneField);
		phoneField.setColumns(10);
		phoneField.setEditable(false);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(30, 248, 70, 17);
		internalFrame.getContentPane().add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(85, 243, 334, 27);
		internalFrame.getContentPane().add(emailField);
		emailField.setEditable(false);

		JLabel addressLabel = new JLabel("Endereço");
		addressLabel.setBounds(30, 282, 70, 17);
		internalFrame.getContentPane().add(addressLabel);
		
		addressField = new JTextField();
		addressField.setBounds(105, 277, 434, 27);
		internalFrame.getContentPane().add(addressField);
		addressField.setEditable(false);

		JLabel cepLabel = new JLabel("CEP");
		cepLabel.setBounds(319, 321, 70, 17);
		internalFrame.getContentPane().add(cepLabel);
		
		cepField = new JTextField();
		cepField.setBounds(354, 316, 105, 27);
		internalFrame.getContentPane().add(cepField);
		cepField.setEditable(false);

		JLabel cityLabel = new JLabel("Cidade");
		cityLabel.setBounds(30, 321, 70, 17);
		internalFrame.getContentPane().add(cityLabel);
		
		cityField = new JTextField();
		cityField.setBounds(85, 321, 106, 27);
		internalFrame.getContentPane().add(cityField);
		cityField.setEditable(false);

		JLabel motherLabel = new JLabel("Nome da mãe");
		motherLabel.setBounds(30, 369, 95, 17);
		internalFrame.getContentPane().add(motherLabel);
		
		motherField = new JTextField();
		motherField.setBounds(137, 364, 402, 27);
		internalFrame.getContentPane().add(motherField);
		motherField.setEditable(false);

		JLabel fatherLabel = new JLabel("Nome do pai");
		fatherLabel.setBounds(30, 409, 95, 17);
		internalFrame.getContentPane().add(fatherLabel);
		
		fatherField = new JTextField();
		fatherField.setBounds(137, 404, 402, 27);
		internalFrame.getContentPane().add(fatherField);
		fatherField.setEditable(false);
		
		firstListLabel = new JLabel();
		firstListLabel.setBounds(576, 73, 120, 17);
		internalFrame.getContentPane().add(firstListLabel);
		
		secondListLabel = new JLabel("Pacotes");
		secondListLabel.setBounds(576, 308, 70, 17);
		internalFrame.getContentPane().add(secondListLabel);
		
		firstList = new JList<String>();
		firstList.setBackground(Color.WHITE);
		firstList.setBounds(574, 112, 240, 153);
		internalFrame.getContentPane().add(firstList);
		
		secondList = new JList<String>();
		secondList.setBackground(Color.WHITE);
		secondList.setBounds(576, 337, 238, 153);
		internalFrame.getContentPane().add(secondList);
		
		JLabel lblDataDaMatrcula = new JLabel("Data da matrícula");
		lblDataDaMatrcula.setBounds(576, 44, 140, 17);
		internalFrame.getContentPane().add(lblDataDaMatrcula);
		
		dateLabel = new JLabel("New label");
		dateLabel.setBounds(718, 44, 100, 17);
		internalFrame.getContentPane().add(dateLabel);
		
		JLabel dataOfPaymentLbl = new JLabel("DADOS DO PAGAMENTO");
		dataOfPaymentLbl.setFont(new Font("Dialog", Font.BOLD, 12));
		dataOfPaymentLbl.setBounds(189, 443, 200, 17);
		internalFrame.getContentPane().add(dataOfPaymentLbl);
		
		JLabel paymentForm = new JLabel("Forma de pagamento");
		paymentForm.setBounds(30, 473, 200, 17);
		internalFrame.getContentPane().add(paymentForm);
		
		paymentFormField = new JTextField();
		paymentFormField.setBounds(182, 472, 380, 27);
		internalFrame.getContentPane().add(paymentFormField);
		paymentFormField.setEditable(false);

		JLabel paymentValue = new JLabel("Valor total");
		paymentValue.setBounds(30, 517, 200, 17);
		internalFrame.getContentPane().add(paymentValue);
			
		paymentValueField = new JTextField();
		paymentValueField.setBounds(105, 511, 120, 27);
		internalFrame.getContentPane().add(paymentValueField);
		paymentValueField.setEditable(false);
		
		JLabel paymentInstallments = new JLabel("Quantidade de Parcelas");
		paymentInstallments.setBounds(229, 511, 165, 17);
		internalFrame.getContentPane().add(paymentInstallments);
		
		paymentInstallmentsField = new JTextField();
		paymentInstallmentsField.setBounds(404, 511, 27, 27);
		internalFrame.getContentPane().add(paymentInstallmentsField);
		paymentInstallmentsField.setEditable(false);
		
		JLabel valueInstallments = new JLabel("Valor das Parcelas");
		valueInstallments.setBounds(443, 511, 165, 17);
		internalFrame.getContentPane().add(valueInstallments);
		
		installmentsValueField = new JTextField();
		installmentsValueField.setBounds(576, 511, 120, 27);
		internalFrame.getContentPane().add(installmentsValueField);
		installmentsValueField.setEditable(false);
		
		deactivateOrActivateButton = new JButton("Desativar matrícula");
		deactivateOrActivateButton.setBounds(576, 11, 208, 25);
		internalFrame.getContentPane().add(deactivateOrActivateButton);
		deactivateOrActivateButton.setVisible(false);

		
		internalFrame.setVisible(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 141, 557, 317);
		contentPane.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);
		
		String [] columns = { "Aluno", "Ação", "CPF"};
		
		tableModel = new DefaultTableModel(null, columns);
		tableOfStudents = new JTable(tableModel);
		
		tableOfStudents.removeColumn(tableOfStudents.getColumnModel().getColumn(2));
		tableOfStudents.setBackground(Color.WHITE);
		
		Action visualizeStudent = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int selectedRow = table.getSelectedRow();
				
				String cpfSelectedStudent  = table.getModel().getValueAt(selectedRow,2).toString();
				CPF selectedStudent;
				try{
					selectedStudent = new CPF(cpfSelectedStudent);
					visualizeStudent(selectedStudent);
				}
				catch(CPFException | StudentException | PhoneException | DateException |
						AddressException | RGException | CourseException | ServiceException |PaymentException e1){
					
					showInfoMessage(e1.getMessage());
				}
				catch(SQLException e2){
					e2.printStackTrace();
				} 
				catch(PersonException e1){
					e1.printStackTrace();
				}
			}

		};
		
		ButtonColumn buttonColumn2 = new ButtonColumn(tableOfStudents, visualizeStudent, 1);
		
		((JScrollPane) scrollPane).setViewportView(tableOfStudents);
		
		backButton = new JButton("Voltar");
		backButton.setBounds(727, 26, 117, 25);
		contentPane.add(backButton);
		backButton.setVisible(false);
	}

	/**
	 * Builds the table to show the found students
	 * @param searchedStudent - Name of the searched student
	 * @throws StudentException 
	 * @throws CPFException 
	 * @throws PersonException 
	 */
	private void buildTableWithSearchedStudent(String searchedStudent) throws StudentException, CPFException, PersonException {

		StudentController studentController = new StudentController();
		tableModel.setRowCount(0);
		ArrayList<Student> resultOfTheSearch = studentController.searchStudent(searchedStudent);
		int arrayIndex = 0;
		
		if(!resultOfTheSearch.isEmpty()){
			while(arrayIndex < resultOfTheSearch.size()){
				String[] student = new String[4];
				student[0] = resultOfTheSearch.get(arrayIndex).getName();
				student[1] = ("Ver");
				CPF cpf = resultOfTheSearch.get(arrayIndex).getCpf();
				student[2] = cpf.getCpf();
				tableModel.addRow(student);
				arrayIndex++;
			}
		}
		else{
			showInfoMessage("Nenhum aluno com esse nome foi encontrado");
		}
		
	}
	
	/**
	 * Show the information of the selected student
	 * @param studentCPF - CPF of the selected student
	 * @throws StudentException 
	 * @throws SQLException 
	 * @throws RGException 
	 * @throws AddressException 
	 * @throws DateException 
	 * @throws CPFException 
	 * @throws PhoneException 
	 * @throws ServiceException 
	 * @throws CourseException 
	 * @throws PaymentException 
	 * @throws PersonException 
	 */
	private void visualizeStudent(CPF studentCPF) throws SQLException, StudentException, PhoneException, 
												CPFException, DateException, AddressException, RGException, CourseException, ServiceException, PaymentException, PersonException {
		
		final StudentController studentController = new StudentController();
		ArrayList<Service> servicesOfStudent = new ArrayList<Service>();
		servicesOfStudent = studentController.searchStudent(studentCPF);	

		if(servicesOfStudent != null){
			
			internalFrame.setVisible(true);
			tableOfStudents.setVisible(false);
			backButton.setVisible(true);

			/**
			 * Basic data of student
			 */			
			int i = 0;
			
			while(i < servicesOfStudent.size()){
				
				Service service = servicesOfStudent.get(i);
				
				student = service.getStudent();
				
				String studentName = student.getName();		
				String email = student.getEmail();
				String motherName = student.getMotherName();
				String fatherName = student.getFatherName();

				studentNameLbl.setText(studentName);
				nameField.setText(studentName);
				emailField.setText(email);
				motherField.setText(motherName);
				fatherField.setText(fatherName);

				//CPF
				CPF cpf = student.getCpf();
				String studentCpf  = cpf.getFormattedCpf();
				cpfField.setText(studentCpf);
				
				//RG
				
				RG rg = student.getRg();
				String studentRg = rg.getFormattedRg();
				rgField.setText(studentRg);
				
				// Birthdate
				Date date = student.getBirthdate();
				String birthdate = date.getSlashFormattedDate();
				birthdateField.setText(birthdate);
				
				//Address
				Address address = student.getAddress();
				String city = address.getCity();
				String cep = address.getCep();
				String completeAddress = address.getCompleteAddress();
				
				addressField.setText(completeAddress);
				cepField.setText(cep);
				cityField.setText(city);
				
				//Phones
				Phone principalPhone = student.getPrincipalPhone();
				Phone secondaryPhone = student.getSecondaryPhone();

				String cellPhone = principalPhone.getFormattedPhone();
				cellField.setText(cellPhone);

				if(secondaryPhone != null){
					String residencePhone = secondaryPhone.getFormattedPhone();
					phoneField.setText(residencePhone);
				}
				else{
					phoneField.setText("");
				}
				
				visualizeServicesAndPayments(service);
				
				status = student.getStatus();
				
				action = setTextToTheDeactiveOrActiveButton(status);
								
				deactivateOrActivateButton.setVisible(true);
				deactivateOrActivateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						
						int confirm = 0;				
						
						confirm = JOptionPane.showConfirmDialog(internalFrame, "Tem certeza que deseja que a matrícula seja " + action + "?", action, JOptionPane.YES_NO_OPTION);
						
						if (confirm == JOptionPane.YES_OPTION) {
							
							boolean wasAltered = false;
							try {
								wasAltered = studentController.alterStatusOfTheStudent(student);
							} 
							catch(StudentException e1) {

							}	
							if(wasAltered){
								showInfoMessage("A matrícula do aluno está " + action + "!");	
								changeStatus();
								action = setTextToTheDeactiveOrActiveButton(status);
							}
							else{
								showInfoMessage("Um erro ocorreu, a matrícula não foi " + action);
							}
						}
						else{
							// Nothing to do
						}
					
					}

					private void changeStatus() {
						
						if(status == student.ACTIVE){
							status = 0;
						}
						else{
							status = 1;
						}
						
					}
				});
				
				i++;
			}
			
			
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					internalFrame.setVisible(false);
					searchedStudentField.setText("");
					firstList.removeAll();
					secondList.removeAll();
					backButton.setVisible(false);
					tableOfStudents.setVisible(true);
				}
			});
			
			
		}
		else{
			showInfoMessage("Não foi possível mostrar os dados do aluno");
		}
		
	}

	private String setTextToTheDeactiveOrActiveButton(int status) {
		
		String enrollmentStatus = "";
		if(status == student.ACTIVE){
			deactivateOrActivateButton.setText("Desativar matrícula");
			enrollmentStatus = "desativada";
		}
		else{
			deactivateOrActivateButton.setText("Ativar matrícula");
			enrollmentStatus = "ativada";
		}
		
		return enrollmentStatus;
		
	}

	/**
	 * Show the data of the services contracts by the selected student
	 * @param service
	 * @param onlyHasPackages 
	 */
	private void visualizeServicesAndPayments(Service service) {
		
		boolean onlyHasPackages = false;
		
		Date date = service.getContractsDate();
		String contractsDate = date.getSlashFormattedDate();
		dateLabel.setText(contractsDate);
		
		Payment payment = service.getPayment();
		PaymentDescription paymentDescription = payment.getDescription();
		String paymentFormAndType = paymentDescription.getDescription();
		paymentFormField.setText(paymentFormAndType);
		
		String totalValue = service.getTotalValueFormatted();
		paymentValueField.setText(totalValue);
		
		Integer installments = payment.getInstallments();
		paymentInstallmentsField.setText(installments.toString());
		
		String installmentsValue = service.getInstallmentsValue();
		installmentsValueField.setText(installmentsValue);
		
		/**
		 * Courses and packages of a student
		 */
		ArrayList<Course> courses = service.getCourses();			
		if(!courses.isEmpty()){
			DefaultListModel<String> courseListModel = new DefaultListModel<String>();
			ArrayList<String> coursesName = new ArrayList<String>();

			int i = 0;
			
			// Building the arraylist with the courses name
			while (i < courses.size()){
				String courseName = courses.get(i).getCourseName();
				coursesName.add(courseName);
				i++;
			}
			
			// Adding the courses name to the list
			i = 0;
			while (i < coursesName.size()){
				courseListModel.addElement(coursesName.get(i));
				i++;
			}
			
			firstListLabel.setText("Cursos");
			firstListLabel.setVisible(true);
			firstList.setVisible(true);
			firstList.setModel(courseListModel);
			
		}
		else{
			onlyHasPackages = true;
		}
		
		ArrayList<Package> packages = service.getPackages();
		if(!packages.isEmpty()){
			
			DefaultListModel<String> packageListModel = new DefaultListModel<String>();
			ArrayList<String> packagesName = new ArrayList<String>();
			
			int i = 0;
			
			// Building the arraylist with the courses name
			while (i < packages.size()){
				String packageName = packages.get(i).getPackageName();
				packagesName.add(packageName);
				i++;
			}
			
			// Adding the courses name to the list
			i = 0;
			while (i < packagesName.size()){
				packageListModel.addElement(packagesName.get(i));
				i++;
			}

			if(onlyHasPackages){
				firstListLabel.setText("Pacotes");
				firstListLabel.setVisible(true);
				firstList.setVisible(true);
				firstList.setModel(packageListModel);
				secondListLabel.setVisible(false);
				secondList.setVisible(false);
			}
			else{
				secondListLabel.setVisible(true);
				secondList.setVisible(true);
				secondList.setModel(packageListModel);
			}

		}
		
	}
}
