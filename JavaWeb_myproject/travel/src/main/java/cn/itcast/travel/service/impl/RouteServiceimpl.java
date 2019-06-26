package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoimpl;
import cn.itcast.travel.dao.impl.RouteDaoimpl;
import cn.itcast.travel.dao.impl.RouteImgDaoimpl;
import cn.itcast.travel.dao.impl.SellerDaoimpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceimpl implements RouteService {

	private RouteDao routedao = new RouteDaoimpl();
	private RouteImgDao routeimgdao = new RouteImgDaoimpl();
	private SellerDao sellerDao = new SellerDaoimpl();
	private FavoriteDao favoriteDao = new FavoriteDaoimpl();

	@Override
	public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
		//创建PageBean
		PageBean<Route> pb = new PageBean<>();
		//设置参数
		//设置当前页码currentPage
		pb.setCurrentPage(currentPage);
		//设置每页显示条数pageSize
		pb.setPageSize(pageSize);
		//设置totalCount 总记录数
		int totalCount = routedao.findTotalCount(cid,rname);
		pb.setTotalCount(totalCount);
		//设置totalPage总页数
		int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
		pb.setTotalPage(totalPage);
		//设置每页显示的数据集合List<route>
		int start = (currentPage - 1 ) * pageSize ;//开始的印记
		List<Route> list = routedao.pageQuery(cid,start,pageSize,rname);
		pb.setList(list);
		/*System.out.println("s当前页"+	pb.getCurrentPage());

		System.out.println("s总记录数"+pb.getTotalCount());

		System.out.println("s总页数"+pb.getTotalPage());
		System.out.println("s每页显示条数"+pb.getPageSize());
		System.out.println("s list集合"+list);*/
		return pb;
	}


	@Override
	public Route findOne(int rid) {
		//根据rid 查询route对象
		Route route = routedao.findOne(rid);

		//根据rid 查询 routeimg集合,将集合存到route中

		List<RouteImg> routeImg = routeimgdao.findByRid(rid);
		route.setRouteImgList(routeImg);

		//根据sid查询 seller对象,将此对象存入route中

		Seller seller = sellerDao.findByid(route.getSid());
		route.setSeller(seller);
		//查询收藏次数
		int count = favoriteDao.findCountByRid(rid);
		//将次数存入route
		route.setCount(count);
		return route;
	}
}
