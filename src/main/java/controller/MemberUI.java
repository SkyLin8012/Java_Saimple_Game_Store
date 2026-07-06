package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.entity.Games;
import model.entity.Member;
import servise.impl.MemberServiceImpl;
import servise.MemeberService;
import util.TableButtonSupport;
import util.Tool;
import javax.swing.JScrollPane;

//1.負責把按鈕【畫】出來
class ButtonRenderer extends JButton implements TableCellRenderer{
	public ButtonRenderer(String label) {
		setText(label);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,Object value,
			boolean isSelected,boolean hasFocus,int row,int column) {
		    return this;
	}
}
//2.負責處理按鈕點擊事件的編輯器



public class MemberUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberUI frame = new MemberUI();
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
	public MemberUI() {
		Member me=null;
		me=(Member) Tool.readFile("member.txt");
		//載入方法
		MemeberService ms = (MemeberService) new MemberServiceImpl();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 10, 551, 37);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel namemsg = new JLabel("");
		namemsg.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		namemsg.setBounds(10, 11, 143, 17);
		panel.add(namemsg);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(10, 57, 551, 44);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		panel_2.setBounds(10, 111, 551, 260);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 531, 207);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		//====event=====
		//載入table資料		
		table.setModel(ms.findAllMemberTable());
		//重新整理
		JButton btnNewButton_2 = new JButton("重新整理");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.setModel(ms.findAllMemberTable());
				//=== 將欄位變成按鈕===	
				//修改
				table.getColumnModel().getColumn(8).setCellRenderer(
					  new TableButtonSupport(table,"修改",row->{
						 Object id = table.getValueAt(row, 0);
						 System.out.println("修改 ID"+id);
						 MemberUpdateUI frame = new MemberUpdateUI((String)id);
						// 關鍵：設定關閉這個視窗時，只釋放該視窗資源，而不結束整個程式
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.setVisible(true);
					  })			
				);
				//刪除
				table.getColumnModel().getColumn(9).setCellRenderer(
						  new TableButtonSupport(table,"刪除",row->{
							 Object id = table.getValueAt(row, 0);
							//System.out.println("刪除 ID"+id);
							MemberDelete frame = new MemberDelete((String)id);
							// 關鍵：設定關閉這個視窗時，只釋放該視窗資源，而不結束整個程式
							frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							frame.setVisible(true);
						  })			
				);
				//設定CellEditor，讓按鈕可點擊		
				table.getColumnModel().getColumn(8).setCellEditor((TableCellEditor) table.getColumnModel().getColumn(8).getCellRenderer());
				table.getColumnModel().getColumn(9).setCellEditor((TableCellEditor) table.getColumnModel().getColumn(9).getCellRenderer());
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(5).setMaxWidth(50);
			}
		});
		btnNewButton_2.setBounds(10, 10, 87, 23);
		panel_2.add(btnNewButton_2);
		
		
	
		//=== 將欄位變成按鈕===	
		//修改
		table.getColumnModel().getColumn(8).setCellRenderer(
			  new TableButtonSupport(table,"修改",row->{
				 Object id = table.getValueAt(row, 0);
				 System.out.println("修改 ID"+id);
				 MemberUpdateUI frame = new MemberUpdateUI((String)id);
				// 關鍵：設定關閉這個視窗時，只釋放該視窗資源，而不結束整個程式
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			  })			
		);
		//刪除
		table.getColumnModel().getColumn(9).setCellRenderer(
				  new TableButtonSupport(table,"刪除",row->{
					 Object id = table.getValueAt(row, 0);
					//System.out.println("刪除 ID"+id);
					MemberDelete frame = new MemberDelete((String)id);
					// 關鍵：設定關閉這個視窗時，只釋放該視窗資源，而不結束整個程式
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				  })			
		);
		//設定CellEditor，讓按鈕可點擊		
		table.getColumnModel().getColumn(8).setCellEditor((TableCellEditor) table.getColumnModel().getColumn(8).getCellRenderer());
		table.getColumnModel().getColumn(9).setCellEditor((TableCellEditor) table.getColumnModel().getColumn(9).getCellRenderer());
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(5).setMaxWidth(50);
		
		
		namemsg.setText(me.getName()+"歡迎您!!");
		
		JLabel timemsg = new JLabel("0000-00-00 00:00:00");
		timemsg.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		timemsg.setBounds(416, 11, 125, 19);
		panel.add(timemsg);
		
		//計時器
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timemsg.setText(sdf.format(new Date()));	
		
		JLabel lblNewLabel = new JLabel("會員管理");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		lblNewLabel.setBounds(235, 10, 67, 17);
		panel.add(lblNewLabel);
		//建立Time，每1000毫秒(1秒)執行一次
		Timer timer = new Timer(1000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				timemsg.setText(sdf.format(new Date()));
			}
		});
		timer.start();
		
		JButton btnNewButton = new JButton("遊樂場");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameStoreUI form = new GameStoreUI();
				form.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(10, 10, 87, 23);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("新增");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterUI form = new RegisterUI();
				form.setLocationRelativeTo(null);
				form.setVisible(true);
				//dispose();
			}
		});
		btnNewButton_1.setBounds(105, 10, 87, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("回遊樂場");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameStoreUI form = new GameStoreUI();
				form.setVisible(true);
				dispose();
			}
		});
		btnNewButton_3.setBounds(454, 10, 87, 23);
		panel_1.add(btnNewButton_3);

	}

}
