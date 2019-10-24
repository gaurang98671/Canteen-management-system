//import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;

public class User_page 
{
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		System.out.println();
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					User_page window = new User_page();
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
	public User_page()  
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	
	private void initialize() 
	{
		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setForeground(SystemColor.info);
		frame.setBounds(100, 100, 238, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setForeground(new Color(0, 0, 0));
		btnLogout.setBackground(new Color(173, 255, 47));
		btnLogout.addActionListener(new ActionListener()   //logout of user page 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					clearLogged();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Main login=new Main();
				login.setVisible();
				frame.dispose();
			}

			private void clearLogged() throws SQLException    //truncates logged table
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement(); 
				stmt.executeUpdate("truncate logged");
				con.close();

				
			}
		});
		btnLogout.setBounds(53, 195, 118, 21);
		frame.getContentPane().add(btnLogout);
		JButton btnOrder = new JButton("Order");	              //order food item
		btnOrder.setForeground(new Color(0, 0, 0));
		btnOrder.setBackground(new Color(173, 255, 47));
		btnOrder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String item=JOptionPane.showInputDialog("Enter item name");
				try {
					if(checkItem(item)==false)
					{
						JOptionPane.showMessageDialog(null, "Item does not exist");
					}
					else
					{
						insertOrder(item);
						
					}
				}
				catch (HeadlessException | SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void insertOrder(String item) throws SQLException     //add argumented food item and current logged name into orders table
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement(); 
				ResultSet current_logged_id=stmt.executeQuery("select * from logged");
				int current_logged = 0;
				String user_name=null;
				int item_price = 0;
				int user_balance = 0;

				
				if(current_logged_id.next())
				{
					 current_logged=current_logged_id.getInt(1);
				}
				current_logged_id.close();
				System.out.println(current_logged);
				ResultSet current_logged_user=stmt.executeQuery("select user_name from login where user_id="+current_logged);
				
				if(current_logged_user.next())
				{
					user_name=current_logged_user.getString(1);
					System.out.println(user_name);
				}
				current_logged_user.close();
				
				//fetching user balance
				ResultSet balance=stmt.executeQuery("select balance from login where user_name='"+user_name+"'");
				if(balance.next())
				{
					user_balance=balance.getInt(1);
					System.out.println(user_balance);
					balance.close();
				}
	
				//fetching cost of item
				
				ResultSet cost=stmt.executeQuery("select price from food where fname='"+item+"'");
				if(cost.next())
				{
					item_price=cost.getInt(1);
					System.out.println(item_price);
					cost.close();
				}
				int new_balance=user_balance-item_price;
				System.out.println(new_balance);
				
				if(item_price<user_balance)
				{
					stmt.executeUpdate("insert into orders(user_name,food_item) values('"+user_name+"','"+item+"')");
					
					stmt.executeUpdate("update login set balance="+new_balance+" where user_name='"+user_name+"'");
					con.close();
					JOptionPane.showMessageDialog(null, "Order Placed");
					Admin_Window aw=new Admin_Window();
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You dont have sufficient account balance");
				}

			}

			private boolean checkItem(String item) throws SQLException  //this function checks weather argumented item is present in food table
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement(); 
				ResultSet rs=stmt.executeQuery("select fname from food where fname='"+item+"'");
				if(rs.next())
					return true;
				else
					return false;
			}
		});
		btnOrder.setBounds(53, 132, 118, 21);
		frame.getContentPane().add(btnOrder);
		
		
		JButton btnViewMenu = new JButton("View Menu");         //shows all food items
		btnViewMenu.setForeground(new Color(0, 0, 0));
		btnViewMenu.setBackground(new Color(173, 255, 47));
		btnViewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				View_menu menu=new View_menu();
				menu.setVisible();
				menu.showOrders();
			}
		});
		btnViewMenu.setBounds(53, 101, 118, 21);
		frame.getContentPane().add(btnViewMenu);
		
		JButton btnCheckBalance = new JButton("Check Balance");    //shows users current balance
		btnCheckBalance.setForeground(new Color(0, 0, 0));
		btnCheckBalance.setBackground(new Color(173, 255, 47));
		btnCheckBalance.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int logged_id = 0;
				int bal;
				try{  
					  
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
					//here sonoo is database name, root is username and password  
					Statement stmt=con.createStatement();  
					ResultSet rs=stmt.executeQuery("select * from logged");
					if(rs.next())
					{
						logged_id=rs.getInt(1);
						rs.close();
					}
					ResultSet rsbal=stmt.executeQuery("select balance from login where user_id="+logged_id);
					if(rsbal.next())
					{
						bal=rsbal.getInt(1);
						rsbal.close();
						JOptionPane.showMessageDialog(null, "Your current balance is Rs "+bal);
					}
					}
				catch(Exception e1)
				{ 
					System.out.println(e1);
				}  

			}
		});
		btnCheckBalance.setBounds(53, 164, 118, 21);
		frame.getContentPane().add(btnCheckBalance);
	
		
		
	}

	public void setVisible()             //sets userpage visible
	{
		frame.setVisible(true);
	}

	public void setName(String string)    //adds greet label
	{
		String greet="Hii "+string;
		
		JLabel lblWelcome = new JLabel(greet);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWelcome.setBounds(75, 50, 109, 32);
		//lblWelcome.setForeground(new Color(255, 255, 255));
		frame.getContentPane().add(lblWelcome);
	}
}
