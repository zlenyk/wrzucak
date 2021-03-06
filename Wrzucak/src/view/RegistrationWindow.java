package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cont.RequestLogic;

public class RegistrationWindow extends JFrame implements MyFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer REGISTRATION = 2;
	private JFrame parent;
	private JPanel contentPane;
	private RegistrationWindow rw;

	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public RegistrationWindow(JFrame window) {
		rw = this;
		parent = window;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 202, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RequestLogic.acceptRequest(rw,REGISTRATION,textField.getText(),new String(passwordField.getPassword()));
				rw.setVisible(false);
				parent.setVisible(true);
			}
		});
		btnNewButton.setBounds(45, 104, 89, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(66, 31, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 34, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(10, 65, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(66, 62, 86, 20);
		contentPane.add(passwordField);
	}
	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
	}
	@Override
	public void showUserWindow(String sessionID,String login) {
	}
}
