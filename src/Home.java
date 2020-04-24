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
import javax.swing.ImageIcon;

public class Home extends  JFrame {

	public JRadioButton cd1RadioButton;
	public JRadioButton cd2RadioButton;
	public String val;
	public String message;
	public JTextField sign_textField;
	public String signt;
	
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

	/**
	 * Create the application.
	 */
	/**
	 * Initialize the contents of the frame.
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
		logOutButton.setBounds(607, 23, 104, 37);
		homePanel.add(logOutButton);
		
		JLabel candidateLabel_1 = new JLabel("");
		candidateLabel_1.setIcon(new ImageIcon("./images/taylor3.jpg"));
		candidateLabel_1.setBounds(123, 136, 155, 177);
		homePanel.add(candidateLabel_1);
		
		JLabel candidateLabel_2 = new JLabel("");
		candidateLabel_2.setIcon(new ImageIcon("./images/justin.jpg"));
		candidateLabel_2.setBounds(423, 136, 155, 177);
		homePanel.add(candidateLabel_2);
		
		JButton voteButton = new JButton("Vote");
		voteButton.setForeground(new Color(255, 255, 255));
		voteButton.setBackground(new Color(0, 128, 0));
		voteButton.setFont(new Font("MS PGothic", Font.BOLD, 35));
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String qual = " ";
				  
		           if (cd1RadioButton.isSelected()) { 

		               qual = "(1) Taylor Swift";
		               val = cd1RadioButton.getText().toString();
		               //JOptionPane.showMessageDialog(Home.this, val);
		               JOptionPane.showMessageDialog(Home.this, "Thank you for your voting");
		               Login logout = new Login();
		               logout.setVisible(true);
		               setVisible(false);
		           } 

		           else if (cd2RadioButton.isSelected()) { 

		               qual = "(2) Donal Trump"; 
		               val = cd2RadioButton.getText().toString();
		               //JOptionPane.showMessageDialog(Home.this, val);
		               JOptionPane.showMessageDialog(Home.this, "Thank you for your voting");
		               Login logout = new Login();
						logout.setVisible(true);
						setVisible(false);
		           } 
		           else { 
		               qual = "You don't want to vote. Are you sure?";
		               val = "Vote no";
		               JOptionPane.showMessageDialog(Home.this, qual);
		           } 
		           
		           message = val;
		           System.out.println(message);
		           try {
					KeyPair pair = generateKeyPair();
					System.out.println("Insert vote " + name);
					String cipherText = encrypt(message, pair.getPublic());
					System.out.println(cipherText);
					signt = sign_textField.getText().toString();
					String signature = sign(signt, pair.getPrivate());
					System.out.println(signature);
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinevoting?useTimezone=true&serverTimezone=UTC","root","");
					PreparedStatement st = (PreparedStatement) conn.prepareStatement("Update users set Result=?, Signature=? where CardID=?");
					st.setString(1, cipherText);
                    st.setString(2, signature);
                    st.setString(3, name);
                    st.executeUpdate();
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinevoting?useTimezone=true&serverTimezone=UTC","root","");
                    PreparedStatement sel = (PreparedStatement) connec.prepareStatement("Select Result, Signature, Verify from users where CardID=?");
                    sel.setString(1, name);
                    ResultSet rs = sel.executeQuery();
                    while (rs.next()) {
                    	String resul = rs.getString("Result");
                    	String signa = rs.getString("Signature");
                        String verif = rs.getString("Verify");
                        System.out.println(resul);
                        System.out.println(signa);
                        String decipheredMessage = decrypt(resul, pair.getPrivate());
                        System.out.println("User selected: " + decipheredMessage);
                        boolean isCorrect = verify(verif, signa, pair.getPublic());
                        System.out.println("Signature is: " + verif);
                        System.out.println("Signature correct: " + isCorrect);
                        
                        Class.forName("com.mysql.cj.jdbc.Driver");
    					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinevoting?useTimezone=true&serverTimezone=UTC","root","");
    					PreparedStatement stm = (PreparedStatement) connect.prepareStatement("Insert INTO results (CardID,Selected,Verify) Values (?,?,?)");
    					stm.setString(1, name);
                        stm.setString(2, decipheredMessage);
                        stm.setBoolean(3, isCorrect);
                        stm.executeUpdate();
                    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		voteButton.setBounds(289, 382, 120, 48);
		homePanel.add(voteButton);
		
		cd1RadioButton = new JRadioButton("(1) Taylor Swift");
		cd1RadioButton.setBounds(146, 316, 114, 23);
		homePanel.add(cd1RadioButton);
		
		cd2RadioButton = new JRadioButton("(2) Donal Trump");
		cd2RadioButton.setBounds(444, 316, 120, 23);
		homePanel.add(cd2RadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(cd1RadioButton);
		group.add(cd2RadioButton);
		
		JLabel lblNewLabel_1 = new JLabel("Who do you need to become the President?");
		lblNewLabel_1.setForeground(new Color(255, 255, 0));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 15));
		lblNewLabel_1.setBounds(212, 111, 287, 14);
		homePanel.add(lblNewLabel_1);
		
		sign_textField = new JTextField();
		sign_textField.setBounds(289, 353, 120, 23);
		homePanel.add(sign_textField);
		sign_textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Your Signature");
		lblNewLabel_2.setFont(new Font("Calibri", Font.BOLD, 13));
		lblNewLabel_2.setForeground(new Color(255, 255, 0));
		lblNewLabel_2.setBounds(310, 337, 81, 14);
		homePanel.add(lblNewLabel_2);
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
