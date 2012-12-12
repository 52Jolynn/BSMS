package com.p6.frames.dialogs.other;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */

public class About extends OtherDialog{
	
	/**
	 * 佌衄凳婖滲杅
	 */
	@SuppressWarnings("unused")
	private About() {
		
	}
	
	/**
	 * 凳婖滲杅
	 * @param owner 虜敦极
	 */
	public About(JFrame owner){
		//扢离敦极扽俶
		setSize(400, 300);
		setModal(true);
		setTitle("壽衾扂蠅");
		setLocationRelativeTo(owner);
		setResizable(false);
		
		JPanel panel = new JPanel(new BorderLayout());
		add(panel);
		
		JLabel banner = new JLabel(new ImageIcon(About.class.getResource("/images/about_banner.jpg")));
		panel.add(banner, BorderLayout.NORTH);
		
		Box content = Box.createVerticalBox();
		panel.add(content, BorderLayout.CENTER);
		
		Box author = Box.createHorizontalBox(); //釬氪
		Box authors = Box.createHorizontalBox(); //釬氪蹈桶1
		Box authorsTwo = Box.createHorizontalBox(); //釬氪蹈桶1
		Box teacher = Box.createHorizontalBox(); //硌絳橾呇
		Box line = Box.createHorizontalBox(); //煦路盄
		Box copyright = Box.createHorizontalBox(); //唳�汒隴
		
		content.add(Box.createVerticalStrut(10));
		content.add(author);
		content.add(Box.createVerticalStrut(8));
		content.add(authors);
		content.add(Box.createVerticalStrut(8));
		content.add(authorsTwo);
		content.add(Box.createVerticalStrut(8));
		content.add(teacher);
		content.add(Box.createVerticalStrut(10));
		content.add(line);
		content.add(Box.createVerticalStrut(8));
		content.add(copyright);
		
		JLabel title = new JLabel("�璃釬氪: 攣皏簿 酴毞肅");
		title.setFont(font);
		
		content.add(Box.createVerticalStrut(20));
		author.add(Box.createHorizontalStrut(5));
		author.add(title);
		author.add(Box.createHorizontalGlue());

		JLabel lineOne = new JLabel("苳試砳 抪牉湴");
		lineOne.setFont(font);
		
		JLabel lineTwo = new JLabel("賀④澄 麻  鞎");
		lineTwo.setFont(font);
		
		JButton ok = new JButton("�隅");
		ok.setFont(font);
		ok.requestFocus();
		
		authors.add(Box.createHorizontalStrut(65));
		authors.add(lineOne);
		authors.add(Box.createHorizontalGlue());
		
		authorsTwo.add(Box.createHorizontalStrut(65));
		authorsTwo.add(lineTwo);
		authorsTwo.add(Box.createHorizontalGlue());
		
		JLabel teachers = new JLabel("硌絳橾呇: 豻  栠 攣簿輿");
		teachers.setFont(font);
		teacher.add(Box.createHorizontalStrut(5));
		teacher.add(teachers);
		teacher.add(Box.createHorizontalStrut(180));
		teacher.add(ok);
		teacher.add(Box.createHorizontalGlue());
		
		//煦路盄
		JLabel hLine = new JLabel(new ImageIcon(About.class.getResource("/images/line.jpg")));
		line.add(hLine);
		
		//唳�汒隴
		JLabel dLine = new JLabel("Copyright \u00A9 2007 Power Six All Rights Reserved");
		dLine.setFont(font);
		copyright.add(Box.createHorizontalStrut(50));
		copyright.add(dLine);
		copyright.add(Box.createHorizontalGlue());
		
		//敦諳壽敕岈璃揭燴
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				About.this.dispose();
			}
			
		});
		
		//�隅偌聽揭燴
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				About.this.dispose();
				
			}
			
		});
	}
	
	@Override
	public void create() {
		setVisible(true);	
	}

	@Override
	public JDialog getThis() {
		return this;
	}

	@Override
	public boolean isUpdate() {
		return true;
	}
	
	//敦极笢腔趼极
	private Font font = new Font("冼极", Font.PLAIN, 12);
}
