package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cont.RequestLogic;

public class UserWindow extends JFrame implements MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UserWindow uw;
	private JFrame parent;
	private static final Integer LOGOUT = 3;
	private String sessionID = "";
	private String login = "";
	/**
	 * Create the frame.
	 */
	public UserWindow(String s,String l,JFrame p) {
		parent = p;
		uw = this;
		sessionID = s;
		login = l;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnWyloguj = new JButton("Wyloguj");
		btnWyloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RequestLogic.acceptRequest(uw,LOGOUT,sessionID,login);
				uw.setVisible(false);
				parent.setVisible(true);
			}
		});
		btnWyloguj.setBounds(214, 49, 89, 23);
		contentPane.add(btnWyloguj);
	}
	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
		
	}
	@Override
	public void showUserWindow(String sessionID,String login) {
	}
}
