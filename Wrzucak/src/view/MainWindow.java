 package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cont.RequestLogic;

public class MainWindow implements MyFrame{
	
	private static final Integer LOGIN = 1;
	private JFrame frmWrzucak;
	private JTextField textField;
	private JPasswordField passwordField;
	private MainWindow mw;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmWrzucak.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		mw = this;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWrzucak = new JFrame();
		frmWrzucak.setTitle("Wrzucak");
		frmWrzucak.setBounds(100, 100, 206, 201);
		frmWrzucak.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWrzucak.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 23, 46, 14);
		frmWrzucak.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 51, 46, 14);
		frmWrzucak.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(59, 20, 86, 20);
		frmWrzucak.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(59, 48, 86, 20);
		frmWrzucak.getContentPane().add(passwordField);
		
		JButton btnZaloguj = new JButton("Zaloguj");
		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RequestLogic.acceptRequest(mw,LOGIN,textField.getText(),new String(passwordField.getPassword()));
			}
		});
		btnZaloguj.setBounds(10, 76, 89, 23);
		frmWrzucak.getContentPane().add(btnZaloguj);
		
		JButton btnRejestracja = new JButton("Rejestracja");
		btnRejestracja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrationWindow reg = new RegistrationWindow(frmWrzucak);
				frmWrzucak.setVisible(false);
				reg.setVisible(true);
			}
		});
		btnRejestracja.setBounds(56, 120, 89, 23);
		frmWrzucak.getContentPane().add(btnRejestracja);
	}

	public void showUserWindow(String sessionID,String login){
		UserWindow uw = new UserWindow(sessionID,login,frmWrzucak);
		frmWrzucak.setVisible(false);
		uw.setVisible(true);
		
	}
	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
	}
}
