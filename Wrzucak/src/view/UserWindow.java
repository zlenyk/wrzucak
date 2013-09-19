package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cont.RequestLogic;
import javax.swing.AbstractListModel;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class UserWindow extends JFrame implements MyFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UserWindow uw;
	private JFrame parent;
	JList<String> list;
	DefaultListModel<String> model;
	private static final Integer LOGOUT = 3;
	private static final Integer LIST = 4;
	private static final Integer SEND = 5;
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
		model = new DefaultListModel<String>();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RequestLogic.acceptRequest(uw, LIST, sessionID, login);
		
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
		
		list = new JList<String>(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBackground(Color.WHITE);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(10, 11, 125, 148);
		contentPane.add(list);
		
		JButton btnWrzu = new JButton("Wrzu\u0107");
		btnWrzu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.showOpenDialog(uw);
				File file = fileChooser.getSelectedFile();
				RequestLogic.acceptFile(sessionID, login, file);
			}
		});
		btnWrzu.setBounds(191, 89, 89, 23);
		contentPane.add(btnWrzu);
	}
	public void populateList(List<String> lista){
		for(String s : lista){
			System.out.println(s);
			model.addElement(s);
		}
	}
	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
		
	}
	@Override
	public void showUserWindow(String sessionID,String login) {
	}
	
}
