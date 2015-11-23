package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import model.Course;
import model.Package;
import datatype.Address;
import datatype.Date;
import datatype.Phone;
import controller.CourseController;
import controller.PackageController;
import controller.StudentController;
import exception.CourseException;
import exception.PackageException;

@SuppressWarnings("serial")
public class EditStudents  extends View{
	
 	
	private JTextField nameField;
	private JTextField cellField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField cepField;
	private JTextField cityField;
	private JTextField emailField;
	private JFormattedTextField birthdateField;
	private JTextField motherField;
	private JTextField fatherField;
	private JTextField ddCellField;
	private JTextField ddPhoneField;

	
	public EditStudents() {
		getContentPane().setLayout(null);
		
		JLabel editStudentLbl = new JLabel("Editar Aluno");
		editStudentLbl.setBounds(399, 12, 275, 31);
		getContentPane().add(editStudentLbl);
		editStudentLbl.setFont(new Font("Dialog", Font.BOLD, 20));
		getContentPane().add(editStudentLbl);
		
		JLabel dataOfStudentLbl = new JLabel("DADOS DO ALUNO");
		dataOfStudentLbl.setBounds(189, 36, 150, 17);
        dataOfStudentLbl.setFont(new Font("Dialog", Font.BOLD, 12));
        getContentPane().add(dataOfStudentLbl);
        
        JLabel nameLbl = new JLabel("Nome");
        nameLbl.setBounds(40, 60, 70, 17);
        getContentPane().add(nameLbl);
        
        nameField = new JTextField();
        nameField.setBounds(94, 55, 434, 27);
        getContentPane().add(nameField);
        nameField.setColumns(10);
        
        JLabel birthdateLabel = new JLabel("Data de Nascimento");
        birthdateLabel.setBounds(30, 171, 200, 17);
        getContentPane().add(birthdateLabel);
        MaskFormatter birthdateMask;
		try{
			birthdateMask = new MaskFormatter("##/##/####");
			birthdateMask.setValidCharacters("0123456789");
			birthdateMask.setValueContainsLiteralCharacters(true);
	        birthdateField = new JFormattedTextField(birthdateMask);
	        birthdateField.setBounds(30, 195, 190, 27);
		}
		catch(ParseException e2){
			e2.printStackTrace();
		}
		
        birthdateField.setColumns(10);
        getContentPane().add(birthdateField);
        
        JLabel cellLabel = new JLabel("Celular");
        cellLabel.setBounds(285, 171, 70, 17);
        getContentPane().add(cellLabel);
               
        JLabel phoneLabel = new JLabel("Telefone");
        phoneLabel.setBounds(285, 205, 70, 17);
        getContentPane().add(phoneLabel);
        
        ddCellField = new JTextField();
        ddCellField.setBounds(359, 166, 40, 27);
        ddCellField.setColumns(10);
        getContentPane().add(ddCellField);
        
        cellField = new JTextField();
        cellField.setBounds(399, 166, 100, 27);
        cellField.setColumns(10);
        getContentPane().add(cellField);
        
        ddPhoneField = new JTextField();
        ddPhoneField.setBounds(359, 200, 40, 27);
        ddPhoneField.setColumns(10);
        getContentPane().add(ddPhoneField);
        
        phoneField = new JTextField();
        phoneField.setBounds(399, 200, 100, 27);
        phoneField.setColumns(10);
        getContentPane().add(phoneField);
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(30, 248, 70, 17);
        getContentPane().add(emailLabel);
        
        emailField = new JTextField();
        emailField.setBounds(85, 243, 334, 27);
        getContentPane().add(emailField);
		
        JLabel addressLabel = new JLabel("Endereço");
        addressLabel.setBounds(30, 282, 70, 17);
        getContentPane().add(addressLabel);
        
        addressField = new JTextField();
        addressField.setBounds(105, 277, 344, 27);
        getContentPane().add(addressField);

        JLabel cepLabel = new JLabel("CEP");
        cepLabel.setBounds(416, 321, 33, 17);
        getContentPane().add(cepLabel);
        
        cepField = new JTextField();
        cepField.setBounds(455, 316, 84, 27);
        getContentPane().add(cepField);

        JLabel cityLabel = new JLabel("Cidade");
        cityLabel.setBounds(266, 321, 70, 17);
        getContentPane().add(cityLabel);
        
        cityField = new JTextField();
        cityField.setBounds(326, 316, 85, 27);
        getContentPane().add(cityField);

        JLabel motherLabel = new JLabel("Nome da mãe");
        motherLabel.setBounds(30, 369, 95, 17);
        getContentPane().add(motherLabel);
        
        motherField = new JTextField();
        motherField.setBounds(137, 364, 402, 27);
        getContentPane().add(motherField);

        JLabel fatherLabel = new JLabel("Nome do pai");
        fatherLabel.setBounds(30, 409, 95, 17);
        getContentPane().add(fatherLabel);
        
        fatherField = new JTextField();
        fatherField.setBounds(137, 404, 402, 27);
        getContentPane().add(fatherField);
        	
		
		JButton registerStudentButton = new JButton("Alterar");
		registerStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		registerStudentButton.setBounds(557, 622, 117, 25);
		getContentPane().add(registerStudentButton);
		
registerStudentButton.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e){		
				
				
				String studentName = nameField.getText();
				Date birthdate = (Date) birthdateField.getValue();
				String email = emailField.getText();
				Address address = (Address) ((JFormattedTextField) addressField).getValue();
				Phone principalPhone = (Phone) ((JFormattedTextField)  cellField).getValue();
				Phone secondaryPhone = (Phone) ((JFormattedTextField) phoneField).getValue();
				String motherName = motherField.getText();
				String fatherName = fatherField.getText();
				
				try{
											
					StudentController studentController = new StudentController();
					
					boolean studentWasUpdated = studentController.updateStudent(
						studentName,
						birthdate,
						email,
						address,
						principalPhone,
						secondaryPhone,
						motherName,
						fatherName
					);
					
					String message = "";
					if(studentWasUpdated){
						message = "Aluno alterado com sucesso.";
					}
					else{
						message = "Não foi possível alterar o aluno informado. Tente novamente.";
					}
					
					showInfoMessage(message);
					
					dispose();
					
					SearchStudent searchStudent = new SearchStudent();
					searchStudent.setVisible(true);
					
				}catch(Exception caughtException){
					
					showInfoMessage(caughtException.getMessage());
				}
			}
		});
		registerStudentButton.setBounds(422, 631, 117, 25);
		getContentPane().add(registerStudentButton);
		
	}

}

