package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
	private UserService service = new UserServiceimpl();

	/**
	 * 注册
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void regist(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//获取页面中的验证码
		String check = req.getParameter("check");
		//获取session中的验证码
		HttpSession session = req.getSession();
		String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
		session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
		//判断
		if (check == null || !check.equalsIgnoreCase(checkcode_server)) {
			//验证码不通过
			ResultInfo info = new ResultInfo();
			info.setFlag(false);
			info.setErrorMsg("验证码错误!!");
			/*//将info对象序列化json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(info);
			//将数据写回页面
			res.setContentType("application/json;charset=utf-8");
			res.getWriter().write(json);*/
			writeValue(info,res);

			return;
		}

		//获取页面数据
		Map<String, String[]> map = req.getParameterMap();
		//将数据封装为user对象
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//调用service
		boolean falg = service.registUser(user);
		//判断返回值
		ResultInfo info = new ResultInfo();
		if (falg) {
			//注册成功
			info.setFlag(true);
		} else {
			//注册失败
			info.setFlag(false);
			info.setErrorMsg("注册失败!");
		}

		/*//将info对象序列化json
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(info);
		//将数据写回页面
		res.setContentType("application/json;charset=utf-8");
		res.getWriter().write(json);*/
		writeValue(info,res);

	}

	/**
	 * 激活
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void active(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//获取验证码
		String code = req.getParameter("code");
		//判断
		if (code != null) {
			//调用service
//			UserService service = new UserServiceimpl();
			boolean flag = service.active(code);
			//判断
			String msg = null;
			if (flag) {
				//激活成功
				msg = "激活成功,请<a href='http://localhost/travel/login.html'>登录</a>";
			} else {
				msg = "激活失败,请联系管理员";
			}
			res.setContentType("text/html;charset=utf-8");
			res.getWriter().write(msg);
		}
	}

	/**
	 * 用于页面显示name
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOne(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		//从session中获取
		Object user = req.getSession().getAttribute("user");
		//将user写回页面
		/*ObjectMapper mapper = new ObjectMapper();
		res.setContentType("application/json;charset=utf-8");
		mapper.writeValue(res.getOutputStream(),user);*/
		writeValue(user,res);
	}

	/**
	 * 登录
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//获取页面中的验证码
		String check = req.getParameter("check");
		//获取session中的验证码
		HttpSession session = req.getSession();
		String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
		session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
		//判断
		if(check == null || !check.equalsIgnoreCase(checkcode_server)){
			ResultInfo info = new ResultInfo();
			info.setFlag(false);
			info.setErrorMsg("验证码错误!!!");
			/*//将info对象序列化json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(info);
			//将数据写回页面
			res.setContentType("application/json;charset=utf-8");
			res.getWriter().write(json);
*/
			writeValue(info,res);
			return;
		}


		//获取数据
		Map<String, String[]> map = req.getParameterMap();
		//将map封装为user对象
		User user = new User();
		try {
			BeanUtils.populate(user,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用service
		User u = service.login(user);
		//做判断
		ResultInfo info = new ResultInfo();
		if(u == null ){
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误");
		}
		if( u != null && !"Y".equals(u.getStatus())){
			info.setFlag(false);
			info.setErrorMsg("您的账户未激活");
		}
		if(u != null && "Y".equals(u.getStatus())){
			info.setFlag(true);
			req.getSession().setAttribute("user",u);
		}

		/*//将info转为json语句
		ObjectMapper mapper = new ObjectMapper();
		//设置响应编码
		res.setContentType("application/json;charset=utf-8");
		mapper.writeValue(res.getOutputStream(),info);*/
		writeValue(info,res);
	}

	/**
	 * 退出
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */

	public void exit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		//将session销毁
		req.getSession().invalidate();
		//跳转到登录页面
		res.sendRedirect(req.getContextPath()+"/login.html");

	}

}
