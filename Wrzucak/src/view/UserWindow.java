package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cont.RequestLogic;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import java.awt.Color;

import javax.swing.JScrollBar;

import java.awt.Choice;

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
		btnWyloguj.setBounds(191, 21, 89, 23);
		contentPane.add(btnWyloguj);
		
		final JFileChooser fileChooser = new JFileChooser();
		
		JButton btncignij = new JButton("\u015Aci\u0105gnij");
		btncignij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btncignij.setBounds(191, 55, 89, 23);
		contentPane.add(btncignij);
		
		JList list = new JList();
		list.setBounds(33, 203, 143, -178);
		contentPane.add(list);
		
		JButton btnWrzu = new JButton("Wrzu\u0107");
		btnWrzu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showOpenDialog(uw);
			}
		});
		btnWrzu.setBounds(191, 89, 89, 23);
		contentPane.add(btnWrzu);
	}
	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
		
	}
	@Override
	public void showUserWindow(String sessionID,String login) {
	}
}
