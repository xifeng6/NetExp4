package com.xifeng.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;

public class ClientUi extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTextField nickname;
	private JTable table;
	private JButton exit;
	private JButton refresh;
	private JButton share;
	private JButton receive;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUi frame = new ClientUi();
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
	public ClientUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblPp = new JLabel("P2P客户端");
		panel.add(lblPp);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 6, 275, 236);
		layeredPane.add(scrollPane);
		
		String[] title = {"文件名","分享者","校验和","大小"};
		Object[][] cellData = new Object[][]{};
		
		model = new DefaultTableModel(cellData, title) {

			  public boolean isCellEditable(int row, int column) {
			    return false;
			  }
		};
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		
		
		nickname = new JTextField();
		nickname.setBounds(343, 6, 91, 26);
		layeredPane.add(nickname);
		nickname.setColumns(10);
		
		JLabel label = new JLabel("用户名：");
		label.setBounds(293, 11, 61, 16);
		layeredPane.add(label);
		
		exit = new JButton("退出");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setBounds(343, 45, 91, 29);
		layeredPane.add(exit);
		
		refresh = new JButton("刷新");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		refresh.setBounds(343, 85, 91, 29);
		layeredPane.add(refresh);		
		
		share = new JButton("分享");
		share.setBounds(343, 125, 91, 29);
		layeredPane.add(share);
		
		receive = new JButton("获取");
		receive.setBounds(343, 165, 91, 29);
		layeredPane.add(receive);
	}

	public JTextField getNickname() {
		return nickname;
	}

	public void setNickname(JTextField nickname) {
		this.nickname = nickname;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getConnect() {
		return exit;
	}

	public void setConnect(JButton exit) {
		this.exit = exit;
	}

	public JButton getRefresh() {
		return refresh;
	}

	public void setRefresh(JButton refresh) {
		this.refresh = refresh;
	}

	public JButton getShare() {
		return share;
	}

	public void setShare(JButton share) {
		this.share = share;
	}

	public JButton getReceive() {
		return receive;
	}

	public void setReceive(JButton receive) {
		this.receive = receive;
	}
		
	public void addData(){
		String[] title = {"文件名","分享者","校验和","大小"};
		model.addRow(title);
		table.setModel(model);
	}
	
	public void addData(Info info){
		String[] row = {info.getFileName(),info.getNickName(),info.getMd5(),info.getLength()+""};
		model.addRow(row);
		table.setModel(model);
	}
}
