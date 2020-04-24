import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.awt.Color;
import java.awt.Component;
import javax.crypto.Cipher;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.io.InputStream;
import java.security.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Home extends  JFrame {

	public JRadioButton cd1RadioButton;
	public JRadioButton cd2RadioButton;
	public String val;
	public String message;
	
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
		
		JLabel lblNewLabel_name = new JLabel("Name");
		lblNewLabel_name.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
		});
		lblNewLabel_name.setBounds(597, 26, 46, 14);
		homePanel.add(lblNewLabel_name);
		
		JLabel lblNewLabel_surname = new JLabel("Surname");
		lblNewLabel_surname.setBounds(652, 26, 46, 14);
		homePanel.add(lblNewLabel_surname);
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login logout = new Login();
				logout.setVisible(true);
				setVisible(false);
			}
		});
		logOutButton.setForeground(new Color(255, 255, 255));
		logOutButton.setBackground(new Color(255, 0, 0));
		logOutButton.setFont(new Font("MS PGothic", Font.BOLD, 17));
		logOutButton.setBounds(597, 51, 104, 37);
		homePanel.add(logOutButton);
		
		JLabel candidateLabel_1 = new JLabel("pic");
		candidateLabel_1.setBounds(113, 142, 136, 164);
		homePanel.add(candidateLabel_1);
		
		JLabel candidateLabel_2 = new JLabel("pic2");
		candidateLabel_2.setBounds(406, 142, 136, 164);
		homePanel.add(candidateLabel_2);
		
		JButton voteButton = new JButton("Vote");
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 jRadioButt();
			}
		});
		voteButton.setBounds(289, 395, 89, 23);
		homePanel.add(voteButton);
		
		cd1RadioButton = new JRadioButton("(1) Taylor Swift");
		cd1RadioButton.setBounds(113, 316, 109, 23);
		homePanel.add(cd1RadioButton);
		
		cd2RadioButton = new JRadioButton("(2) Donal Trump");
		cd2RadioButton.setBounds(406, 316, 109, 23);
		homePanel.add(cd2RadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(cd1RadioButton);
		group.add(cd2RadioButton);
	}
	
	public void displayName() {
		/*Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinevoting?useTimezone=true&serverTimezone=UTC","root","");
		Statement stmt = conn.createStatement();
		String sql ="Select * from users where CardID='"+id_textField.getText()+"' and DOB='"+pss_textField.getText()+"'";
		ResultSet rs =stmt.executeQuery(sql);*/
	}
	
	public void jRadioButt() {
		String qual = " ";
		//val = " ";
		//String val1;
		  
           if (cd1RadioButton.isSelected()) { 

               qual = "(1) Taylor Swift";
               val = cd1RadioButton.getText().toString();
               JOptionPane.showMessageDialog(Home.this, val);
               JOptionPane.showMessageDialog(Home.this, "Thank you for your voting");
               Login logout = new Login();
               logout.setVisible(true);
               setVisible(false);
               //RsaExample enc = new RsaExample();
               //val = enc.message;
               //JOptionPane.showMessageDialog(Home.this, val);
               //System.out.println(val);
           } 

           else if (cd2RadioButton.isSelected()) { 

               qual = "(2) Donal Trump"; 
               val = cd2RadioButton.getText().toString();
               JOptionPane.showMessageDialog(Home.this, val);
               JOptionPane.showMessageDialog(Home.this, "Thank you for your voting");
               Login logout = new Login();
				logout.setVisible(true);
				setVisible(false);
           } 
           else { 

               qual = "Please select";
               JOptionPane.showMessageDialog(Home.this, qual);
           } 
           
           message = val;
           System.out.println(message);
           try {
			KeyPair pair = generateKeyPair();
			String cipherText = encrypt(message, pair.getPublic());
			System.out.println(cipherText);
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinevoting?useTimezone=true&serverTimezone=UTC","root","");
			Statement stmt = conn.createStatement();
			String sql ="UPDATE users SET Result='"+cipherText.toString()+"' where Name = 'Livia' ";
			stmt.execute(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
	}
	
	public String getVal() {
		return this.message;
	}
	
	public static KeyPair generateKeyPair() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048, new SecureRandom());

        KeyPair pair = generator.generateKeyPair();



        return pair;

    }



    public static KeyPair getKeyPairFromKeyStore() throws Exception {

        //Generated with:

        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks



        InputStream ins = RsaExample.class.getResourceAsStream("/keystore.jks");



        KeyStore keyStore = KeyStore.getInstance("JCEKS");

        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password

        KeyStore.PasswordProtection keyPassword =       //Key password

                new KeyStore.PasswordProtection("s3cr3t".toCharArray());



        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);



        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");

        PublicKey publicKey = cert.getPublicKey();

        PrivateKey privateKey = privateKeyEntry.getPrivateKey();



        return new KeyPair(publicKey, privateKey);

    }



    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {

        Cipher encryptCipher = Cipher.getInstance("RSA");

        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);



        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));



        return Base64.getEncoder().encodeToString(cipherText);

    }



    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {

        byte[] bytes = Base64.getDecoder().decode(cipherText);



        Cipher decriptCipher = Cipher.getInstance("RSA");

        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);



        return new String(decriptCipher.doFinal(bytes), UTF_8);

    }



    public static String sign(String plainText, PrivateKey privateKey) throws Exception {

        Signature privateSignature = Signature.getInstance("SHA256withRSA");

        privateSignature.initSign(privateKey);

        privateSignature.update(plainText.getBytes(UTF_8));



        byte[] signature = privateSignature.sign();



        return Base64.getEncoder().encodeToString(signature);

    }



    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {

        Signature publicSignature = Signature.getInstance("SHA256withRSA");

        publicSignature.initVerify(publicKey);

        publicSignature.update(plainText.getBytes(UTF_8));



        byte[] signatureBytes = Base64.getDecoder().decode(signature);



        return publicSignature.verify(signatureBytes);

    }
}
