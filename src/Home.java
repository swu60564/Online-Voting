import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.crypto.Cipher;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.awt.event.ActionEvent;


public class Home extends  JFrame {
	private JTextField textField;
	private JTextField textField_1;


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

    public static KeyPair generateKeyPair() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048, new SecureRandom());

        KeyPair pair = generator.generateKeyPair();



        return pair;

    }



    public static KeyPair getKeyPairFromKeyStore() throws Exception {

        //Generated with:

        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks



        InputStream ins = Home.class.getResourceAsStream("/keystore.jks");



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
		
		JButton vote = new JButton("vote");
		vote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					
					
					KeyPair pair = generateKeyPair();
					String plainText = textField.getText();
					
					String cipherText = encrypt(plainText, pair.getPublic());
					String decipheredMessage = decrypt(cipherText, pair.getPrivate());
				    System.out.print(decipheredMessage);
				    textField_1.setText(cipherText);
				    
				    Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useTimezone=true&serverTimezone=UTC","root","ploy");
					Statement stmt = conn.createStatement();
					String sql ="INSERT INTO user"+ "(Vote)" +" VALUES ('"+cipherText+"')";
					stmt.executeUpdate(sql);
				 
				}catch(Exception e1){
					e1.printStackTrace();
					
				}
				
				
				
			}
		});
		vote.setBounds(316, 264, 85, 21);
		homePanel.add(vote);
		
		JLabel user = new JLabel("New label");
		user.setBounds(613, 106, 45, 13);
		homePanel.add(user);
		
		textField = new JTextField();
		textField.setBounds(301, 217, 116, 19);
		homePanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(301, 321, 116, 19);
		homePanel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(316, 374, 85, 21);
		homePanel.add(btnNewButton);
	}
}
