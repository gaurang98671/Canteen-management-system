import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
//import java.awt.Window.Type;
//import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class Main {

	private JFrame frame;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JLabel lblOR;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.text);
		frame.setBackground(new Color(255, 255, 255));
		frame.getContentPane().setForeground(new Color(204, 255, 51));
		frame.setBounds(100, 100, 332, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setForeground(new Color(0, 0, 0));
		lblUserName.setBackground(new Color(0, 0, 0));
		lblUserName.setBounds(41, 94, 65, 20);
		frame.getContentPane().add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setForeground(new Color(0, 0, 0));
		txtUserName.setBackground(SystemColor.menu);
		txtUserName.setBounds(105, 95, 161, 19);
		frame.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password ");
		lblPassword.setForeground(SystemColor.infoText);
		lblPassword.setBounds(41, 148, 65, 13);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setForeground(new Color(0, 0, 0));
		txtPassword.setBackground(SystemColor.menu);
		txtPassword.setBounds(105, 145, 161, 19);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(new Color(173, 255, 47));
		btnLogin.setToolTipText("ggg");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			
				String UserName=txtUserName.getText().toString();
				String Pin=txtPassword.getText().toString();
				
				
				try{  
					  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
					//here sonoo is database name, root is username and password  
					Statement stmt=con.createStatement();
					ResultSet rs;
					
					rs=stmt.executeQuery("select user_name from login where user_name='"+UserName+"' and password='"+Pin+"'");
				
				

						if(rs.next()==false)
					{
						JOptionPane.showMessageDialog(null, "Login failed!!");
					}
					else
					{
						//ResultSet id=stmt.executeQuery("select user_id from login where user_name='"+UserName+"'");
						
						
						User_page user_page=new User_page();
						truncateLogged();
					
						user_page.setName(txtUserName.getText().toString());
					
						addToLogged(UserName);
						user_page.setVisible();
						frame.dispose();
					
					}
				
					}catch(Exception e1){ System.out.println(e1);
				}
		}

	/*		private int getBalance(String userName) throws SQLException 
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();
				int balance = 0;
				ResultSet bal=stmt.executeQuery("select balance from login where user_name='"+userName+"'");
				if(bal.next())
				{
					balance=bal.getInt(1);
				}
				return balance;
			}*/

			private void truncateLogged() throws SQLException  //truncates logged table
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement(); 
				stmt.executeUpdate("truncate logged");con.close();

			}

			private void addToLogged(String userName) throws SQLException  //adds user id to logged table
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement(); 
				ResultSet rs=stmt.executeQuery("select user_id from login where user_name='"+userName+"'");
				rs.next();
				stmt.executeUpdate("insert into logged values("+rs.getInt(1)+")");
				
				
			}
		});
		btnLogin.setBounds(123, 226, 85, 21);
		frame.getContentPane().add(btnLogin);
		
		lblOR = new JLabel("OR");
		lblOR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOR.setBounds(160, 257, 27, 13);
		frame.getContentPane().add(lblOR);
		
		JButton btnCreate = new JButton("Create Account");
		btnCreate.setBackground(new Color(173, 255, 47));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Sign_in signin = new Sign_in();
				signin.setvisible(true);
				frame.dispose();
				
				
			}
		});
		btnCreate.setBounds(105, 280, 127, 21);
		frame.getContentPane().add(btnCreate);
	}

	public void setVisible()           //sets login page visible
	{
		frame.setVisible(true);
		
	}
}
