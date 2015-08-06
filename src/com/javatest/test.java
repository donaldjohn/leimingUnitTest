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
 * java方法的测试类型
 * */
public class test {

	@Test
	 //初始化数据库中的数据
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
    	 //读取本地文件的数据
    	 File file = new File("E:\\学习资料\\个人\\周伟app\\Leiming\\assets\\class\\"+fileName+".txt");
    	 BufferedReader read;
		try {
			//读取文件中的数据
			read = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
			String result = null;
			while ((result = read.readLine()) != null) {
				String[] split = result.split("&&");
				if(split.length > 1){
					sql = "insert into title(title,content,type,operateTime) values('"+split[0]+"','"+split[1]+"','"+fileName+"','"+sdf.format(new Date())+"')";//SQL语句  
				}else{
					sql = "insert into title(title,content,type,operateTime) values('"+split[0]+"','','"+fileName+"','"+sdf.format(new Date())+"')";//SQL语句  
				}
				db1 = new DBHelper(sql);//创建DBHelper对象  
				try {  
		        	 db1.pst.execute(sql);
		         } catch (SQLException e) {  
		             e.printStackTrace();  
		         } 
			}
			db1.close();//关闭连接  
	         
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void test1(){
		String oldName = "张春生d";
		//获取转为拼音的字符串
		HashMap<String,String> namePinMap = getPingYin(oldName);
		String my = "sdf";
		String spellString = namePinMap.get("spellString");
		if( spellString.contains(my) ){
			//如果有对应的数据，则获取对应拼音再汉子中的位置，然后显示
			String mys[] = my.split(" ");
			for(String myValue : mys){
				System.out.println("位置："+namePinMap.get(myValue));
				System.out.println(oldName.charAt(Integer.parseInt(namePinMap.get(myValue))));
			}
		}
		
	}
	
	//将汉字转为对应的拼音，返回一个拼音的map集合（拼音，汉字所在的index）
    public static HashMap<String,String> getPingYin(String src) {  
  	  
        char[] srcArray = null;  
        //获取字符串转为数组的变量
        srcArray = src.toCharArray();  
        //保存当期汉子转为的拼音
        StringBuilder spellString = new StringBuilder();
        //保存转换为拼音的字变量
        String[] temp = new String[srcArray.length];  
        //使用框架转拼音设置的参数
        HanyuPinyinOutputFormat params = new HanyuPinyinOutputFormat();  
        params.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        params.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        params.setVCharType(HanyuPinyinVCharType.WITH_V);  
        //保存一个转换为拼音的汉子的拼音，以及当期汉子所在的位置
        HashMap<String,String> spellMap = new HashMap<String,String>();
        //获取字符的长度
        int srcLength = srcArray.length;  
        try {  
            for (int i = 0; i < srcLength; i++) {  
                // 判断是否为汉字字符  
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
            //将当前字符转化为的拼音保存
            spellMap.put("spellString",spellString.toString() );
            //System.out.println(t4);  
            return spellMap;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return spellMap;  
    } 
    
    
}
