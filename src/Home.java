import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Color;

public class Home extends  JFrame {

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
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
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setBounds(100, 100, 750, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel homePanel = new JPanel();
		homePanel.setBackground(new Color(30, 144, 255));
		homePanel.setBounds(0, 0, 734, 441);
		getContentPane().add(homePanel);
		homePanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Online Voting");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 50));
		lblNewLabel.setBounds(188, 11, 343, 120);
		homePanel.add(lblNewLabel);
	}


}
