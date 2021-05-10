package com.quaero.qbcTest.util;

import java.io.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Properties;

public class LoadPropertiesFileUtil {
    private static String webPath = System.getProperty("user.dir"); // E:\Program Files (x86)\workspace\oms
    private static String fileName = "conf/config.yml";
    private static  String basePath = webPath  + File.separator + fileName;

/**
 * 一、 使用java.util.Properties类的load(InputStream in)方法加载properties文件
 * @return
 */
public static Properties getPropertie1(String basePath) {
        Properties prop = new Properties();
        try {
        InputStream in = new BufferedInputStream(new FileInputStream(new File(basePath)));
        prop.load(in);

        } catch (FileNotFoundException e) {
        System.out.println("properties文件路径书写有误，请检查！");
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        return prop;
        }
    public static void updateProperties(String keyname,String keyvalue) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(basePath));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(basePath);
            prop.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            prop.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
    public static String getPropert(String key){
        String basePath = webPath  + File.separator + fileName;
        return LoadPropertiesFileUtil.getPropertie1(basePath).getProperty(key);
    }

    public static void main(String[] args) {
        String basePath = webPath + File.separator + "resources" + File.separator + fileName;
        System.out.println("getPropertie1===>>>>>>" + LoadPropertiesFileUtil.getPropertie1(basePath).getProperty("MachineIP1"));
        }
}

