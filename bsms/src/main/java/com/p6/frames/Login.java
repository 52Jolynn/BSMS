/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.xml.DOMConfigurator;

import com.p6.frames.dialogs.Function;
import com.p6.frames.dialogs.other.ModifyPassword;
import com.p6.toolkit.LinkLabel;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.SystemProperties;
import com.p6.user.DoLogin;
import com.p6.user.Handler;
import com.p6.user.infoBean.LogInfo;

public class Login extends JFrame {
	private static final long serialVersionUID = 5449413690129751592L;

	/**
	 * 构造函数
	 */
	public Login() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		SystemProperties sp = SystemProperties.getInstance();
		setLocation(sp.getScreenWidth() / 4, sp.getScreenWidth() / 4);
		setUndecorated(true);

		// 加入背景
		JPanel panel = new BackgroundPalne();
		add(panel);

		content = Box.createVerticalBox();
		panel.add(content);

		center = Box.createVerticalBox();
		Box user = Box.createHorizontalBox();
		Box pwd = Box.createHorizontalBox();
		underCenter = Box.createHorizontalBox();

		content.add(Box.createVerticalStrut(80));
		content.add(center);
		content.add(Box.createVerticalStrut(10));
		content.add(underCenter);
		content.add(Box.createVerticalStrut(10));

		username = new JTextField(10);
		username.requestFocus();
		password = new JPasswordField();
		JLabel userLabel = new JLabel("用户姓名");
		JLabel passwordLabel = new JLabel("用户密码");
		userLabel.setFont(font);
		userLabel.setForeground(Color.white);
		passwordLabel.setForeground(Color.white);
		passwordLabel.setFont(font);
		modifyPassword = new LinkLabel("修改密码", Color.white, Color.red,
				Color.white);
		exit = new LinkLabel("退出系统", Color.white, Color.red, Color.white);
		modifyPassword.setFont(font);
		exit.setFont(font);

		// 用户名称 修改密码
		user.add(Box.createHorizontalStrut(50));
		user.add(userLabel);
		user.add(Box.createHorizontalStrut(10));
		user.add(username);
		user.add(Box.createHorizontalStrut(10));
		user.add(modifyPassword);
		user.add(Box.createHorizontalStrut(160));

		// 用户密码 退出系统
		pwd.add(Box.createHorizontalStrut(50));
		pwd.add(passwordLabel);
		pwd.add(Box.createHorizontalStrut(10));
		pwd.add(password);
		pwd.add(Box.createHorizontalStrut(10));
		pwd.add(exit);
		pwd.add(Box.createHorizontalStrut(160));

		center.add(user);
		center.add(Box.createVerticalStrut(10));
		center.add(pwd);

		// 登陆 重置按钮
		login = new JButton("登陆");
		login.setFont(font);
		reset = new JButton("重置");
		reset.setFont(font);
		underCenter.add(Box.createHorizontalStrut(50));
		underCenter.add(login);
		underCenter.add(Box.createHorizontalStrut(10));
		underCenter.add(reset);
		underCenter.add(Box.createHorizontalStrut(150));

		// 修改密码事件响应
		modifyPassword.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				Function function = new Function();
				function.setFunctionDialog(new ModifyPassword(Login.this));
				function.create();
			}

		});

		// 退出系统事件响应
		exit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.exit(0);
			}

		});

		// 登陆按钮事件响应
		login.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				logInfo.setUserID(username.getText());
				logInfo.setUserPassWord(String.valueOf(password.getPassword()));
				Handler handler = new Handler();
				handler.setDoHandler(new DoLogin());
				handler.process(logInfo);

				if (handler.isSucced() == true) {
					if (logInfo.getStaffTitle().equals("总经理")) {
						Role role = new TMFrame(logInfo.getStaffID());
						role.create();
						Login.this.dispose();
					} else if (logInfo.getStaffTitle().equals("部门经理")) {
						Role role = new DMFrame(logInfo.getStaffID());
						role.create();
						Login.this.dispose();
					} else if (logInfo.getStaffTitle().equals("销售经理")) {
						Role role = new SMFrame(logInfo.getStaffID());
						role.create();
						Login.this.dispose();
					} else {
						Role role = new SAFrame(logInfo.getStaffID());
						role.create();
						Login.this.dispose();
					}
				} else {
					new MessageBox("登陆失败, 请确认您的用户名和密码!", "登陆失败", Login.this);
					username.setText("");
					username.requestFocus();
					password.setText("");
				}

			}

		});

		// 重置按钮事件响应
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				username.setText("");
				username.requestFocus();
				password.setText("");

			}

		});

	}

	static {
		DOMConfigurator
				.configure(Login.class.getResource("/loggingConfig.xml"));
	}

	private Box content = null;
	private Box center = null;
	private Box underCenter = null;

	private JTextField username = null;
	private JPasswordField password = null;
	private JButton login = null;
	private JButton reset = null;
	private LinkLabel exit = null;
	private LinkLabel modifyPassword = null;

	private Font font = new Font("宋体", Font.PLAIN, 12);

	private LogInfo logInfo = new LogInfo();

	private static final int DEFAULT_WIDTH = 480;
	private static final int DEFAULT_HEIGHT = 240;

	public static void main(String[] args) {
		Login login = new Login();
		login.setVisible(true);
	}
}

class BackgroundPalne extends JPanel {
	private static final long serialVersionUID = -4896268253963680651L;

	public BackgroundPalne() {
		try {
			img = ImageIO.read(Login.class.getResource("/images/login.jpg"));
		} catch (IOException e) {
			logger.warn("images/login.jpg载入错误! - " + Login.class.getName());
		}

		// 下面的方法载入图像的时候有延迟
		// img = Toolkit.getDefaultToolkit().createImage("images/login.jpg");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

	private Image img;
	private static Logger logger = Logger.getLogger(Login.class);
}
