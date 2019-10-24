import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Sign_in {

	private JFrame frame;
	private JTextField txtRetype;
	private JTextField txtName;
	private JTextField txtPassword;
	private JTextField txtDOB;
	private JTextField txtPhn;
    private String gender=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sign_in window = new Sign_in();
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
	public Sign_in() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 440, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(62, 29, 46, 13);
		frame.getContentPane().add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(63, 149, 79, 13);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblreType = new JLabel("Re-type Password");
		lblreType.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblreType.setBounds(62, 209, 111, 13);
		frame.getContentPane().add(lblreType);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGender.setBounds(62, 274, 46, 13);
		frame.getContentPane().add(lblGender);
		
		JLabel lblDOB = new JLabel("D.O.B.");
		lblDOB.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDOB.setBounds(62, 329, 46, 13);
		frame.getContentPane().add(lblDOB);
		
		JButton btnSignIn = new JButton("Create Account");
		btnSignIn.setBackground(new Color(173, 255, 47));
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
			    String name=txtName.getText().toString().trim();
				String password=txtPassword.getText().toString().trim();
				String retype=txtRetype.getText().toString().trim();
				String phn=txtPhn.getText().toString().trim();
				String dob=txtDOB.getText().toString().trim();
				
				try {
					if(name.isEmpty() || password.isEmpty() || retype.isEmpty() || phn.isEmpty() || dob.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "All fields should be filled");
					}
					else
					{
					if(password.compareTo(retype)!=0)
					{
						JOptionPane.showMessageDialog(null, "Password dont match");
					}
					else
					{
					if(phn.length()!=10)
					{
						JOptionPane.showMessageDialog(null, "Invalid phone number");
					}
					else
					{
						
					if(NoMatch()==false)
					{
						JOptionPane.showMessageDialog(null, "Username alredy exists");
					}
					else
					{
						try
						{  
							  
							Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
							//here sonoo is database name, root is username and password  
							Statement stmt=con.createStatement();  
							stmt.executeUpdate("insert into login(user_name,password,gender,phn_number,dob) values('"+name+"','"+password+"','"+gender+"','"+phn+"','"+dob+"')") ;
							con.close();
							Main login=new Main();
							login.setVisible();
							frame.dispose();
							JOptionPane.showMessageDialog(null, "Succesfully created accound!!");  
							
							
							}catch(Exception e1){ System.out.println(e1);
					   } 
					}
					}
					}
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}

			private boolean NoMatch() throws SQLException    //checks if username alredy exists
			{
				
					  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
					//here sonoo is database name, root is username and password  
					Statement stmt=con.createStatement();  
					ResultSet rs=stmt.executeQuery("select user_name from login where user_name='"+txtName.getText().toString()+"'");
				    if(rs.next())
				    {
				    	return false;
				    }
				    else
				    {
				    	return true;
				    }
				    
				
				
			}
});
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(141, 412, 146, 21);
		frame.getContentPane().add(btnSignIn);
		
		txtRetype = new JTextField();
		txtRetype.setBackground(new Color(240, 255, 240));
		txtRetype.setBounds(173, 207, 184, 19);
		frame.getContentPane().add(txtRetype);
		txtRetype.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBackground(new Color(240, 255, 240));
		txtName.setBounds(173, 27, 184, 19);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBackground(new Color(240, 255, 240));
		txtPassword.setBounds(173, 147, 184, 19);
		frame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		txtDOB = new JTextField();
		txtDOB.setBackground(new Color(240, 255, 240));
		txtDOB.setBounds(173, 327, 184, 19);
		frame.getContentPane().add(txtDOB);
		txtDOB.setColumns(10);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBackground(new Color(255, 255, 255));
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(rdbtnMale.isSelected())
				{
					
					 gender="M";
				}
				
			}
		});
		rdbtnMale.setBounds(173, 271, 69, 21);
		frame.getContentPane().add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBackground(new Color(255, 255, 255));
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(rdbtnFemale.isSelected())
				{
					gender="F";
				}
				
			}
		});
		rdbtnFemale.setBounds(254, 271, 111, 21);
		frame.getContentPane().add(rdbtnFemale);
		ButtonGroup g =new ButtonGroup();
		g.add(rdbtnMale);
		g.add(rdbtnFemale);
		JLabel lblPhn = new JLabel("Phone Number");
		lblPhn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPhn.setBounds(62, 84, 93, 13);
		frame.getContentPane().add(lblPhn);
		
		txtPhn = new JTextField();
		txtPhn.setBackground(new Color(240, 255, 240));
		txtPhn.setBounds(173, 82, 184, 19);
		frame.getContentPane().add(txtPhn);
		txtPhn.setColumns(10);
	}

	public void setvisible(boolean b)
	{
		frame.setVisible(true);
		
	}
}