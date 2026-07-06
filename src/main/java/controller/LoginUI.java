package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.entity.Member;
import servise.MemeberService;
import servise.impl.MemberServiceImpl;
import util.DbConnection;
import util.Tool;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField uid;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		//取得cookie
		Member me=null;
		me=(Member) Tool.readFile("member.txt");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(0, 0, 434, 261);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("登入帳號");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblNewLabel.setBounds(175, 75, 56, 29);
		panel_1.add(lblNewLabel);
		
		uid = new JTextField();
		uid.setBounds(164, 109, 96, 21);
		panel_1.add(uid);
		uid.setColumns(10);
		
		password = new JTextField();
		password.setBounds(164, 140, 96, 21);
		panel_1.add(password);
		password.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblNewLabel_1.setBounds(120, 109, 34, 15);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("密碼:");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblNewLabel_2.setBounds(113, 140, 46, 15);
		panel_1.add(lblNewLabel_2);
		

		

		//------event-------
		if(me!=null)
		{
			uid.setText(me.getUid());
			password.setText(me.getPassword());
		}
		
		MemeberService ms = new MemberServiceImpl();
		
		JButton btnNewButton = new JButton("登入");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Uid =uid.getText().trim();
				String Password = password.getText().trim();
				
				Member result=ms.Login(Uid,Password);
				if(ms.checkUsername(Uid))
				{
					if(result!=null) {
						Tool.saveFile("member.txt",result);
						GameStoreUI frame = new GameStoreUI();
						frame.setVisible(true);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null,"密碼錯誤","登入錯誤!",JOptionPane.ERROR_MESSAGE);

					}
				}
				else {
					JOptionPane.showMessageDialog(null,"沒有此帳號請先註冊","登入錯誤!",JOptionPane.ERROR_MESSAGE);
					RegisterUI frame = new RegisterUI();
					frame.setVisible(true);
					dispose();
				}
				
				
			}
		});
		btnNewButton.setBounds(111, 171, 87, 23);
		panel_1.add(btnNewButton);
		//註冊
		JButton addmember = new JButton("註冊");
		addmember.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String formname="controller.RegisterUI";
				//String formname="controller.SnakeGame";
				try {
					Class<?> clazz= Class.forName(formname);
					JFrame form = (JFrame)clazz.getDeclaredConstructor().newInstance();
					form.setVisible(true);
					dispose();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//RegisterUI form = new RegisterUI();
				//form.setVisible(true);
				//dispose();
			}
		});
		addmember.setBounds(113, 208, 182, 23);
		panel_1.add(addmember);
		//判斷是否為管理員
		if("Y".equals(me.getAdmin()))
		{
			addmember.setVisible(false);
		}else {
			addmember.setVisible(true);
			
		}
		
		
		JButton btnNewButton_2 = new JButton("登出");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//初始化cookie
				Member temp =new Member();
				Tool.saveFile("member.txt",temp);
				uid.setText("");
				password.setText("");
				addmember.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(208, 171, 87, 23);
		panel_1.add(btnNewButton_2);
		

	}
}
