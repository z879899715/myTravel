package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceimpl;
import cn.itcast.travel.service.impl.RouteServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

	private RouteService routeService = new RouteServiceimpl();
	private FavoriteService favoriteService = new FavoriteServiceimpl();

	/**
	 * 写回一个pageBean对象
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void pageQuery(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//获取页面的参数
		String cidStr = req.getParameter("cid");//类别id
		String currentPageStr = req.getParameter("currentPage");//当前页码
		String pageSizeStr = req.getParameter("pageSize");//每页显示条数
		//获取 rname 搜索的线路名称
		String rname = req.getParameter("rname");
		//get请求的rname乱码需要重新编码
		if (rname != null && rname.length() > 0 && !"null".equals(rname)) {

		rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
		}
		//处理参数
		int cid = 0;
		if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
			cid = Integer.parseInt(cidStr);
		}
		int currentPage = 0;//如果不传递参数,则设置默认值
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		} else {
			currentPage = 1;
		}
		int pageSize = 0;//如果不传递参数,则设置默认值
		if (pageSizeStr != null && pageSizeStr.length() > 0 ) {
			pageSize = Integer.parseInt(pageSizeStr);
		} else {
			pageSize = 5;
		}
	/*	System.out.println("cid:"+cid);
		System.out.println("currentPage:"+currentPage);
		System.out.println("pageSize"+pageSize);*/
		//调用service
		PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

		//将pageBean写回页面
	/*	System.out.println("当前页"+	routePageBean.getCurrentPage());

		System.out.println("总记录数"+routePageBean.getTotalCount());

		System.out.println("总页数"+routePageBean.getTotalPage());
		System.out.println("每页显示条数"+routePageBean.getPageSize());*/
		writeValue(routePageBean, res);


	}


	/**
	 * 用于查询 单个旅游路线的信息
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOne(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//首先接收rid
		String ridStr = req.getParameter("rid");
		int rid = 0;
		if (ridStr != null && ridStr.length() > 0) {
			//将接收的参数转换为int
			rid = Integer.parseInt(ridStr);
		}
		//调用service
		Route route = routeService.findOne(rid);
		//将route 写回页面

		writeValue(route, res);


	}

	/**
	 * 查询用户是否收藏
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void isFavorite(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//从页面接收rid
		String rid = req.getParameter("rid");
		//从session域中获取user对象
		User user = (User) req.getSession().getAttribute("user");
		//获取uid
		int uid;
		if (user == null) {
			uid = 0;
		} else {
			uid = user.getUid();
		}
//		System.out.println("uid"+uid);
		//调用service
		boolean flag = favoriteService.isFavorite(rid, uid);
//		System.out.println(flag);
		//将数据写回压面
		writeValue(flag, res);

	}


	/**
	 * 根据uid查找收藏
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void myFavorite(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//从seesion中获取user对象
		User user = (User) req.getSession().getAttribute("user");
		//调用service
//		String uid = req.getParameter("uid");
		int uid;
		if (user == null) {
			return;
		} else {
			uid = user.getUid();
		}
		List<Route> routes = favoriteService.getMyFavorite(uid);
		writeValue(routes,res);
	}
	/**
	 * 添加收藏功能
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addFavorite(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//从页面接收rid
		String rid = req.getParameter("rid");
		//从session域中获取user对象
		User user = (User) req.getSession().getAttribute("user");
		//获取uid
		int uid;
		if (user == null) {
			return;
		} else {
			uid = user.getUid();
		}
		//调用service
		favoriteService.add(rid,uid);

	}

	/**
	 * 取消收藏功能
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delFavorite(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//从页面接收rid
		String rid = req.getParameter("rid");
		//从session域中获取user对象
		User user = (User) req.getSession().getAttribute("user");
		//获取uid
		int uid;
		if (user == null) {
			return;
		} else {
			uid = user.getUid();
		}
		//调用service
		favoriteService.del(rid,uid);

	}
}