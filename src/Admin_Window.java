import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class Admin_Window {


	private JFrame frame;
	private JTextField txtOrder;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_Window window = new Admin_Window();
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
	public Admin_Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 599, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList list2 = new JList();
		list2.setBounds(268, 66, 285, 167);
		frame.getContentPane().add(list2);
		
		JButton btnSeeOrders = new JButton("See Orders");
		btnSeeOrders.setBackground(new Color(173, 255, 47));
		btnSeeOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			
				try {
					DefaultListModel<String> list=new DefaultListModel<String>();
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from orders");
					list.addElement("Order no.      User Name      Food Item");
					while(rs.next())
					{
						String s="      "+rs.getInt(1)+"               "+rs.getString(2)+"        \t "+rs.getString(3)+" ";
						System.out.println(s);
						list.addElement(s);
					}
					
					list2.setModel(list);     //sets list to DefaultListModel
				
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			
		});
		btnSeeOrders.setBounds(31, 63, 169, 21);
		frame.getContentPane().add(btnSeeOrders);
		
		JButton btnAddBalance = new JButton("Add Balance");
		btnAddBalance.setBackground(new Color(173, 255, 47));
		btnAddBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String user_name=JOptionPane.showInputDialog("Enter the user name");
				int amount=Integer.parseInt(JOptionPane.showInputDialog("Enter the amount"));
				try {
					add_balance(user_name,amount);       //adds balance to user account 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}

			private void add_balance(java.lang.String user_name, int amount) throws SQLException //add balance function
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();
				ResultSet balance=stmt.executeQuery("select balance from login where user_name='"+user_name+"'");
				int final_balance = 0;
				if(balance.next())
				{
					 final_balance=balance.getInt(1)+amount;

				}
								
				System.out.println(final_balance);
				stmt.executeUpdate("update login set balance="+final_balance+" where user_name='"+user_name+"'");
				con.close();
				JOptionPane.showMessageDialog(null, "Amount added into user account");
				
			}
		});
		btnAddBalance.setBounds(31, 110, 169, 21);
		frame.getContentPane().add(btnAddBalance);
		
		JButton btnAddItem = new JButton("Add New Item");      //adds item to database
		btnAddItem.setBackground(new Color(173, 255, 47));
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String item_name=JOptionPane.showInputDialog("Enter the item name");
				int item_price=Integer.parseInt(JOptionPane.showInputDialog("Enter item price"));
				try {
					insert_into_food(item_name,item_price);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}

			private void insert_into_food(String s,int p) throws SQLException
			{
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
				//here sonoo is database name, root is username and password  
				Statement stmt=con.createStatement();  
				stmt.executeUpdate("insert into food(fname,price) values('"+s+"',"+p+")");
				con.close();
				JOptionPane.showMessageDialog(null, "Item added successfully");

				
			}
		});
		btnAddItem.setBounds(31, 159, 169, 21);
		frame.getContentPane().add(btnAddItem);
		
		JButton btnRemoveItem = new JButton("Remove Item");       //removes items from food database
		btnRemoveItem.setBackground(new Color(173, 255, 47));
		btnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Connection con;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");
					 
					Statement stmt=con.createStatement();  
					ResultSet rs;  
					String item=JOptionPane.showInputDialog("Enter the item name you want to delete");
					rs=stmt.executeQuery("select * from food where fname='"+item+"'");
					if(rs.next()==false)
					{
						JOptionPane.showMessageDialog(null,"No such item exists!!");
					}
					else
					{
						stmt.executeUpdate("delete from food where fname='"+item+"'");
						JOptionPane.showMessageDialog(null,"Item Deleted!!!");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}  
			
			}
		});
		btnRemoveItem.setBounds(31, 210, 169, 21);
		frame.getContentPane().add(btnRemoveItem);
		
		txtOrder = new JTextField();
		txtOrder.setBackground(new Color(240, 255, 240));
		txtOrder.setBounds(335, 36, 96, 19);
		frame.getContentPane().add(txtOrder);
		txtOrder.setColumns(10);
		
		JButton btnCompleted = new JButton("Completed");
		btnCompleted.setBackground(new Color(173, 255, 47));
		btnCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String OrderNo=txtOrder.getText().toString().trim();
				int orderno=Integer.parseInt(OrderNo);
				try {
					completeOrder(orderno);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnSeeOrders.doClick();
				
			}

			private void completeOrder(int orderno) throws SQLException 
			{
				
			   	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");
			    Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from orders where order_no="+orderno);
				if(rs.next()==false)
				{
					JOptionPane.showMessageDialog(null, "Invalid Order Number");
				}
				else
				{
					stmt.executeUpdate("delete from orders where order_no="+orderno);
				}
				
			}
		});
		btnCompleted.setBounds(441, 35, 102, 21);
		frame.getContentPane().add(btnCompleted);
		
		JLabel lblNewLabel = new JLabel("Order Number");
		lblNewLabel.setBounds(268, 39, 76, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ORDERS");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(372, 240, 87, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		
		
	}

	
}
