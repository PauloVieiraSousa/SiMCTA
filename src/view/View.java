package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exception.AuthenticationException;

import java.sql.SQLException;

import view.SearchCourse;

public class View extends JFrame {
	
	protected JMenuBar menuBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	protected void instantiateMenuBar(){
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu courseMenu = new JMenu("Cursos");
		menuBar.add(courseMenu);
		
		JMenuItem registerCourse = new JMenuItem("Cadastrar Curso");
		registerCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean permissionToAccess = false;
				
				permissionToAccess = getPermissionToAccess();
				if(permissionToAccess == true){
					dispose();
					NewCourse newCourseFrame = new NewCourse();
					newCourseFrame.setVisible(true);
				}
				else{
					View frame = new View();
					frame.setVisible(true);
				}
			}

			private boolean getPermissionToAccess() {
				boolean canAccess = false;
				String messageToUser = "";
				String enteredPassword = "senha digitada";
				final Object [] inputPassword;
				final JPasswordField enteredPasswordField;
				final JLabel passwordLabel;
									
				passwordLabel = new JLabel("Digite a senha:");
				enteredPasswordField = new JPasswordField();
				inputPassword = new Object[]{passwordLabel, enteredPasswordField};
				
				while(canAccess == false){
					AuthenticationView authenticationFrame = new AuthenticationView();
					int verify = authenticationFrame.showConfirmDialog(null, inputPassword, "Senha:",
													  authenticationFrame.OK_CANCEL_OPTION,
													  authenticationFrame.PLAIN_MESSAGE);
					enteredPassword = enteredPasswordField.getText();
					if (verify == JOptionPane.OK_OPTION) {
						if(enteredPassword != null){
							try {
								canAccess = authenticationFrame.authenticateUser(enteredPassword);
							} 
							catch(AuthenticationException | SQLException e){	
								messageToUser = e.toString();
								int indexToSepare = messageToUser.indexOf(":");
								messageToUser = messageToUser.substring(indexToSepare + 2);
								authenticationFrame.showMessageDialog(null, messageToUser);
							}
						}
					}
					else if (verify == JOptionPane.CANCEL_OPTION) {
						break;
					}
				}

				return canAccess;
			}
		});
		courseMenu.add(registerCourse);
		
		JMenuItem searchCourse = new JMenuItem("Visualizar Curso");
		searchCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();			
				try {
					SearchCourse searchCourseFrame = new SearchCourse();
					searchCourseFrame.setVisible(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		courseMenu.add(searchCourse);
	}
	/**
	 * Create the frame.
	 */
	public View(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		instantiateMenuBar();
	}
	protected void showInfoMessage(String message){
		
		JOptionPane.showMessageDialog(null, message, "", JOptionPane.INFORMATION_MESSAGE);
	}
}
