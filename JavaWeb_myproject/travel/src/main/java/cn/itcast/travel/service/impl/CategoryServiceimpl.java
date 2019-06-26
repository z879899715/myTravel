package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoimpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.untils.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceimpl implements CategoryService {

	private CategoryDao dao = new CategoryDaoimpl();

	/**
	 * tab_category所有数据
	 * @return
	 */
	@Override
	public List<Category> findAll() {
		//使用nosql,从jedis中获取连接
		Jedis jedis = new JedisUtil().getJedis();
		//获取数据
		Set<Tuple> jedis_cat = jedis.zrangeWithScores("category", 0, -1);
		//判断
		List<Category> cs = null;
		if(jedis_cat == null || jedis_cat.size() == 0){
			//redis中没有数据,查询数据库
			 cs = dao.findAll();

			//将数据存入redis中
			for (int i = 0; i < cs.size(); i++) {
				jedis.zadd("category",cs.get(i).getCid(),
						cs.get(i).getCname());
			}
		}else {
			//如果不为空  将redis中的数据存在cs中
			cs = new ArrayList<Category>();
			//遍历redis中查出的数据
			for (Tuple tuple : jedis_cat) {
				//创建category对象
				Category category = new Category();
				//category存入数据
				category.setCname(tuple.getElement());
				category.setCid((int)tuple.getScore());
				//使用list集合 添加对象
				cs.add(category);

			}
		}

		return cs;
	}
}
