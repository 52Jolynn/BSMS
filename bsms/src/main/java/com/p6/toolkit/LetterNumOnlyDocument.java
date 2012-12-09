package com.p6.toolkit;


import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * 限制文本框中输入的只能是数字
 * 用法：
 * <p>	JTextField text = new JTextField();
 * 		text.setDocument(new NumOnlyDocument());
 * </p>
 * @author xiaogy	
 */
public class LetterNumOnlyDocument extends PlainDocument{
	 public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException 
	 {  
		str = str.trim();
		 for(int i=0; i<str.length(); i++){
			 if(!Character.isDigit(str.charAt(i)) && !Character.isLetter(str.charAt(i))) //如果不是数字和字母就返回
					return; 	
		 }
			 
		 super.insertString(offs, str, a);
	}
}
