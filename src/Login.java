import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class Login extends  JFrame{

	private JFrame frame;
	private JTextField id_textField;
	private JTextField pss_textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 750, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(30, 144, 255));
		loginPanel.setBounds(0, 0, 734, 441);
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home = new Home();
				home.setVisible(true);
				setVisible(false);
			}
		});
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBackground(new Color(255, 0, 0));
		loginButton.setBounds(248, 321, 199, 85);
		loginButton.setFont(new Font("MS PGothic", Font.BOLD, 38));
		loginPanel.add(loginButton);
		
		JLabel lblNewLabel = new JLabel("Online Voting");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 50));
		lblNewLabel.setBounds(188, 11, 343, 120);
		loginPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_id = new JLabel("ID Card Number");
		lblNewLabel_id.setBackground(new Color(255, 255, 255));
		lblNewLabel_id.setForeground(new Color(255, 255, 255));
		lblNewLabel_id.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_id.setBounds(179, 128, 127, 17);
		loginPanel.add(lblNewLabel_id);
		
		JLabel lblNewLabel_password = new JLabel("Password");
		lblNewLabel_password.setForeground(new Color(255, 255, 255));
		lblNewLabel_password.setBackground(new Color(255, 255, 255));
		lblNewLabel_password.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_password.setBounds(179, 210, 79, 17);
		loginPanel.add(lblNewLabel_password);
		
		id_textField = new JTextField();
		id_textField.setBounds(179, 164, 342, 35);
		loginPanel.add(id_textField);
		id_textField.setColumns(10);
		
		pss_textField = new JTextField();
		pss_textField.setBounds(179, 257, 342, 35);
		loginPanel.add(pss_textField);
		pss_textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("**Password is your birth of date**");
		lblNewLabel_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(179, 224, 199, 14);
		loginPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("(Ex. If your birthday is 3 March 1999, Your password will be 03031999)");
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(179, 238, 434, 14);
		loginPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("(Your id card number 13 digits)");
		lblNewLabel_3.setForeground(new Color(255, 255, 0));
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(179, 145, 180, 14);
		loginPanel.add(lblNewLabel_3);
	}
}
