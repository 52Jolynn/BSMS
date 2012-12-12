/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;

import javax.swing.JLabel;

public class LinkLabel extends JLabel {

	/**
	 * 默认构造函数
	 */
	public LinkLabel() {
		super();
		display();
	}
	
	/**
	 * 构造函数
	 * @param text 显示的字符
	 * @param linkColor 字符的颜色
	 */
	public LinkLabel(String text, Color linkColor) {
		super();
		super.setText(text);
		super.setForeground(linkColor);
		
		this.text = text;
		this.linkColor = linkColor;
		this.hoverColor = linkColor;
		this.visitedColor = linkColor;
		
		display();
	}
	
	/**
	 * 构造函数
	 */
	public LinkLabel(String text, Color linkColor, Color hoverColor, Color visitedColor) {
		super();
		super.setText(text);
		super.setForeground(linkColor);
		
		this.text = text;
		this.linkColor = linkColor;
		this.hoverColor = hoverColor;
		this.visitedColor = visitedColor;
		
		display();
	}
	
	/**
	 * 构造函数
	 * @param text 显示的字符
	 * @param url 链接地址
	 */
	public LinkLabel(String text, String url) {
		super();
		super.setText(text);
		
		this.text = text;
		this.url = url;
		
		display();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		super.setText(text);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Color getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(Color linkColor) {
		this.linkColor = linkColor;
		super.setForeground(linkColor);
	}

	public Color getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
		super.setForeground(hoverColor);
	}

	public Color getVisitedColor() {
		return visitedColor;
	}

	public void setVisitedColor(Color visitedColor) {
		this.visitedColor = visitedColor;
		super.setForeground(visitedColor);
	}
	
	private void display() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				LinkLabel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				setHoverColor(hoverColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				setVisitedColor(visitedColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setLinkColor(linkColor);
			}
			
		});
	}

	private String text = null;
	private String url = null;
	private Color linkColor = null;
	private Color hoverColor = null;
	private Color visitedColor = null;	
}
