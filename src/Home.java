import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
					
					Home window = new Home(null);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	 * Create the application.
	 * @param string 

	 */
	public Home(String name) {
		

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
					
					System.out.println("Insert vote " + name);
					String cipherText = encrypt(plainText, pair.getPublic());
					String decipheredMessage = decrypt(cipherText, pair.getPrivate());
				    System.out.print(decipheredMessage);
				    textField_1.setText(cipherText);
				    
				    
				    Connection con = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useTimezone=true&serverTimezone=UTC","root","");
				    PreparedStatement st = (PreparedStatement) con
	                        .prepareStatement("Update user set Vote=? where ID=?");

	                    st.setString(1, cipherText);
	                    st.setString(2, name);
	                    st.executeUpdate();
	                   
				}catch(Exception e1){
					e1.printStackTrace(); 
					
				}
				
				
				
			}
		});
		vote.setBounds(316, 264, 85, 21);
		homePanel.add(vote);
		
		
		textField = new JTextField();
		textField.setBounds(301, 217, 116, 19);
		homePanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(301, 321, 116, 19);
		homePanel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    Login obj = new Login();
                    
                    obj.setVisible(true);
                    setVisible(false);
                }
               
            }
			
		});
		btnNewButton.setBounds(639, 395, 85, 21);
		homePanel.add(btnNewButton);
	}
}
