import com.test.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-solr.xml")
public class TestTemplate {
    @Autowired
    private SolrTemplate solrTemplate;
    /**
     * 测试添加
     */
    @Test
    public void testAdd(){
        TbItem item = new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为2号专卖店");
        item.setTitle("华为Mate9");
        item.setPrice(new BigDecimal(2000));

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }
    /**
     * 测试通过唯一域查找一个
     */
    @Test
    public void testFindOne(){
        TbItem item = solrTemplate.getById(1L, TbItem.class);
        System.out.println(item);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDele(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }

    /**
     * 往域中放入100条数据
     */
    @Test
    public void testAddList() {
        List<TbItem> list = new ArrayList<TbItem>();
        for (int i = 0; i < 100; i++) {
            TbItem item = new TbItem();
            item.setId(i+1L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为2号专卖店");
            item.setTitle("华为Mate" + i);
            item.setPrice(new BigDecimal(2000 + i));

            list.add(item);
        }
        //保存数据列表
        solrTemplate.saveBeans(list);
        //提交
        solrTemplate.commit();
    }

    /**
     * 测试分页
     */
    @Test
    public void testQueryPage(){
        //思路：
        //1.	设置查询条件对象Query，new SimpleQuery(查询语法)
        //2.	设置分页条件setOffset(起始行号),setRows(每页查询的数量)
        //3.	分页查询-solrTemplate.queryForPage(Query, TbItem.class)
        //4.	输出分页参数，getTotalElements-总记录数，getTotalPages-总页数
        //5.	输出列表for-page.getContent
        //设置查询条件
        Query query = new SimpleQuery("*:*");

        //设置分页条件
        query.setOffset(1);
        query.setRows(10);

        //分页查询
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);

        //总记录数
        System.out.println(page.getTotalElements());
        //总页数
        System.out.println(page.getTotalPages());

        for (TbItem tbItem : page.getContent()) {
            System.out.println(tbItem);
        }
    }

    /**
     *  Criteria 用于对条件的封装
     */
    @Test
    public void testQueryPageWhere(){
        //设置查询条件对象
        Query query = new SimpleQuery("*:*");
        //条件组装
        Criteria criteria = new Criteria("item_title").contains("2");
        criteria = criteria.and("item_title").contains("5");
        query.addCriteria(criteria);
        //设置分页条件
        //query.setOffset(20);
        //query.setRows(10);
        //分页查询
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        //输出分页参数
        System.out.println("总记录数" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        //输出列表
        for (TbItem item : page.getContent()) {
            System.out.println(item);
        }
    }

    /**
     * 测试删库跑路
     */
    @Test
    public void testDeleteAll(){
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
