package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class RegistrationWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame parent;
	private JPanel contentPane;

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Create the frame.
	 */
	public RegistrationWindow(JFrame window) {
		frame = this;
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
				frame.setVisible(false);
				parent.setVisible(true);
			}
		});
		btnNewButton.setBounds(45, 104, 89, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(66, 31, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(66, 62, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 34, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(10, 65, 46, 14);
		contentPane.add(lblNewLabel_1);
	}

}