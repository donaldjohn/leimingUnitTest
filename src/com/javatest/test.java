package com.javatest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;

/**
 * java�����Ĳ�������
 * */
public class test {

	@Test
	 //��ʼ�����ݿ��е�����
    public void initData() {
		try {
			initDataNow("teacher");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	public void initDataNow(String fileName) throws IOException{
		 String sql = null;  
    	 DBHelper db1 = null;  
    	 ResultSet ret = null;
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 //��ȡ�����ļ�������
    	 File file = new File("E:\\ѧϰ����\\����\\��ΰapp\\Leiming\\assets\\class\\"+fileName+".txt");
    	 BufferedReader read;
		try {
			//��ȡ�ļ��е�����
			read = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
			String result = null;
			while ((result = read.readLine()) != null) {
				String[] split = result.split("&&");
				if(split.length > 1){
					sql = "insert into title(title,content,type,operateTime) values('"+split[0]+"','"+split[1]+"','"+fileName+"','"+sdf.format(new Date())+"')";//SQL���  
				}else{
					sql = "insert into title(title,content,type,operateTime) values('"+split[0]+"','','"+fileName+"','"+sdf.format(new Date())+"')";//SQL���  
				}
				db1 = new DBHelper(sql);//����DBHelper����  
				try {  
		        	 db1.pst.execute(sql);
		         } catch (SQLException e) {  
		             e.printStackTrace();  
		         } 
			}
			db1.close();//�ر�����  
	         
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void test1(){
		String oldName = "�Ŵ���d";
		//��ȡתΪƴ�����ַ���
		HashMap<String,String> namePinMap = getPingYin(oldName);
		String my = "sdf";
		String spellString = namePinMap.get("spellString");
		if( spellString.contains(my) ){
			//����ж�Ӧ�����ݣ����ȡ��Ӧƴ���ٺ����е�λ�ã�Ȼ����ʾ
			String mys[] = my.split(" ");
			for(String myValue : mys){
				System.out.println("λ�ã�"+namePinMap.get(myValue));
				System.out.println(oldName.charAt(Integer.parseInt(namePinMap.get(myValue))));
			}
		}
		
	}
	
	//������תΪ��Ӧ��ƴ��������һ��ƴ����map���ϣ�ƴ�����������ڵ�index��
    public static HashMap<String,String> getPingYin(String src) {  
  	  
        char[] srcArray = null;  
        //��ȡ�ַ���תΪ����ı���
        srcArray = src.toCharArray();  
        //���浱�ں���תΪ��ƴ��
        StringBuilder spellString = new StringBuilder();
        //����ת��Ϊƴ�����ֱ���
        String[] temp = new String[srcArray.length];  
        //ʹ�ÿ��תƴ�����õĲ���
        HanyuPinyinOutputFormat params = new HanyuPinyinOutputFormat();  
        params.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        params.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        params.setVCharType(HanyuPinyinVCharType.WITH_V);  
        //����һ��ת��Ϊƴ���ĺ��ӵ�ƴ�����Լ����ں������ڵ�λ��
        HashMap<String,String> spellMap = new HashMap<String,String>();
        //��ȡ�ַ��ĳ���
        int srcLength = srcArray.length;  
        try {  
            for (int i = 0; i < srcLength; i++) {  
                // �ж��Ƿ�Ϊ�����ַ�  
                if (java.lang.Character.toString(srcArray[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                	temp = PinyinHelper.toHanyuPinyinStringArray(srcArray[i], params);  
                	spellMap.put(temp[0],i+"");
                	spellString.append(temp[0]+" ");
                } else{
                	spellMap.put(java.lang.Character.toString(srcArray[i]), i+"");
                	spellString.append(java.lang.Character.toString(srcArray[i])+" ");
                }
            }  
            //����ǰ�ַ�ת��Ϊ��ƴ������
            spellMap.put("spellString",spellString.toString() );
            //System.out.println(t4);  
            return spellMap;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return spellMap;  
    } 
    
    
}
