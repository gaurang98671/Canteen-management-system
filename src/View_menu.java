import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class View_menu {

	private JFrame frame;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JLabel lblMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_menu window = new View_menu();
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
	public View_menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 442, 295);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 55, 314, 171);
		frame.getContentPane().add(scrollPane);
		
		list = new JList();
		list.setBackground(new Color(240, 255, 240));
		scrollPane.setViewportView(list);
		
		lblMenu = new JLabel("Menu");
		lblMenu.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblMenu.setBounds(177, 20, 112, 25);
		frame.getContentPane().add(lblMenu);
	}

	public void setVisible() 
	{
		frame.setVisible(true);
		// TODO Auto-generated method stub
		
	}

	public void showOrders()
	{
		try {
			DefaultListModel<String> list2=new DefaultListModel<String>();
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1962");  
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from food");
			list2.addElement("Item id            Item Name           Price");
			while(rs.next())
			{
				String s="      "+rs.getInt(1)+"               "+rs.getString(2)+"        \t "+rs.getInt(3)+" ";
				System.out.println(s);
				list2.addElement(s);
			}
			
		list.setModel(list2);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
