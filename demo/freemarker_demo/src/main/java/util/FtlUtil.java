package util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class FtlUtil {
    public static void main(String[] args) throws Exception {
        //1.创建核心类 Configuration-new, 构造方法的参数就是 freemarker的版本号
        Configuration cfg = new Configuration(Configuration.getVersion());
        //2.设置模板所在的位置,cfg.setDirectoryForTemplateLoading()
        cfg.setDirectoryForTemplateLoading(new File("E:\\IdeaWorkSpace\\test\\freemarker_demo\\src\\main\\java\\ftl"));
        //3.设置模板文件使用的字符集,cfg.setDefaultEncoding。一般就是 utf-8.
        cfg.setDefaultEncoding("utf-8");
        //4.获取模板,参数是模板名称cfg.getTemplate，得到template，注意模板类型，
        // 官方要求ftl后缀,实际上jsp,html,java,itcast...后缀都可以
        Template template = cfg.getTemplate("test.ftl");
        //5.指定数据模型，一般都用Map
        Map map = new HashMap();
        map.put("name", "lj");
        map.put("message", "欢迎进入Freemarker课程内容....");
        map.put("success", true);

        //循环遍历
        List list = new ArrayList();
        HashMap hm1 = new HashMap();
        hm1.put("name", "苹果");
        hm1.put("price", 5.8);
        HashMap hm2 = new HashMap();
        hm2.put("name", "香蕉");
        hm2.put("price", 2.5);
        HashMap hm3 = new HashMap();
        hm3.put("name", "橘子");
        hm3.put("price", 3.2);

        list.add(hm1);
        list.add(hm2);
        list.add(hm3);

        //添加要遍历的集合
        map.put("list", list);

        //添加时间
        map.put("now", new Date());

        //数字转换为字符串
        map.put("point", 1234567895);

        map.put("testNull",null);

        map.put("one",10);
        map.put("two", 20);

        //6.创建一个Writer对象，一般new FileWriter对象，指定生成的文件名。
        Writer out = new FileWriter("D:/test.html");
        //7.使用模板输出template.process
        template.process(map,out);
        //8.关闭输出对象out
        out.close();



    }
}
