package com.p6.toolkit;

import java.util.Vector;

/**
 * <p>Title: {字转拼音}</p>
 * <p>Description: {将汉字转换成拼音，不转换英文与不可识别的字符}</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */

public class CnToSpell {
    private static Vector<Spell> spellMap = null;

    static class  Spell{
    	public String spell="";
    	public int ascii=0;
    	Spell(String spell, int ascii){
    		this.spell = spell;
    		this.ascii = ascii;
    	}
    }
    
    static {
        if (spellMap == null) {
            spellMap = new Vector<Spell>();
        }
        initialize();
        //System.out.println("Chinese transfer Spell Done.");
    }

    private CnToSpell() {
    }

    private static void spellPut(String spell, int ascii) {
       	spellMap.addElement(new Spell(spell, new Integer(ascii)));
    }

    private static void initialize() {
    	  spellPut("a", -20319);
          spellPut("b", -20283);     
          spellPut("c", -19775);     
          spellPut("d", -19218);      
          spellPut("e", -18710);
          spellPut("f", -18526); 
          spellPut("g", -18239);
          spellPut("h", -17922);
          spellPut("j", -17417);
          spellPut("k", -16474);
          spellPut("l", -16212);
          spellPut("m", -15640);
          spellPut("n", -15165);
          spellPut("o", -14922);
          spellPut("p", -14914);
          spellPut("q", -14630);
          spellPut("r", -14149);
          spellPut("s", -14090);
          spellPut("t", -13318);
          spellPut("w", -12838);
          spellPut("x", -12556);
          spellPut("y", -11847);
          spellPut("z", -11055);
          spellPut("",-10247);
    }

    /**
     * 获得单个汉字的Ascii.
     * @param cn char
     * 汉字字符
     * @return int
     * 错误返回 0,否则返回ascii
     */
    public static int getCnAscii(char cn) {
        byte[] bytes = (String.valueOf(cn)).getBytes();
        if (bytes == null || bytes.length > 2 || bytes.length <= 0) { //错误
            return 0;
        }
        if (bytes.length == 1) { //英文字符
            return bytes[0];
        }
        if (bytes.length == 2) { //中文字符
            int hightByte = 256 + bytes[0];
            int lowByte = 256 + bytes[1];

            int ascii = (256 * hightByte + lowByte) - 256 * 256;
            return ascii;
        }

        return 0; //错误
    }

    /**
     * 根据ASCII码到SpellMap中查找对应的拼音
     * @param ascii int
     * 字符对应的ASCII
     * @return String
     * 拼音,首先判断ASCII是否>0&<160,如果是返回对应的字符,
     *
     否则到SpellMap中查找,如果没有找到拼音,则返回null,如果找到则返回拼音.
     */
    public static String getSpellByAscii(int ascii) {
        if (ascii > 0 && ascii < 160) { //单字符
            return String.valueOf((char) ascii);
        }

        if (ascii < -20319 || ascii > -10247) { //不知道的字符
            return null;
        } 
        String spell0 = null; ;
        String spell = null;

        int asciiRang0 = -20319;
        int asciiRang;
        for(int i=1; i<spellMap.size(); i++){
            spell = spellMap.get(i).spell;
            asciiRang = spellMap.get(i).ascii;
            
            if (ascii >= asciiRang0 && ascii < asciiRang) { //区间找到
        	   return (spell0 == null) ? spell : spell0;
            } else {
            	spell0 = spell;
            	asciiRang0 = asciiRang;
            }
        }

        return null;

    }

    /**
     * 返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换
     * @param cnStr String
     * 字符串
     * @return String
     * 转换成全拼后的字符串
     */
    public static String getFullSpell(String cnStr) {
        if (null == cnStr || "".equals(cnStr.trim())) {
            return cnStr;
        }

        char[] chars = cnStr.toCharArray();
        StringBuffer retuBuf = new StringBuffer();
        for (int i = 0, Len = chars.length; i < Len; i++) {
            int ascii = getCnAscii(chars[i]);
            if (ascii == 0) { 			//取ascii时出错
                retuBuf.append(chars[i]);
            } else {
                String spell = getSpellByAscii(ascii);
                if (spell == null) {
                    retuBuf.append(chars[i]);
                } else {
                    retuBuf.append(spell);
                } // end of if spell == null
            } // end of if ascii <= -20400
        } // end of for

        return retuBuf.toString();
    }

    public static String getFirstSpell(String cnStr) {
        return null;
    }
}

