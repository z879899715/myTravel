package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

	private CategoryService service = new CategoryServiceimpl();

	/**
	 *查询tab_category所有数据
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAll(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//调用service
		List<Category> categories = service.findAll();
		//写回页面
		writeValue(categories,res);


	}

}
