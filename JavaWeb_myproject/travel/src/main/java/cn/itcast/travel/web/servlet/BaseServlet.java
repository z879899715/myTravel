package cn.itcast.travel.web.servlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 为了解决一个功能创建一个servlet,使用反射机制
 */
public class BaseServlet extends HttpServlet {

	/**
	 * 因为其子类的任一方法被调用,都会先调用sevice方法,而service方法中必定会调用其父类的service方法
	 * 所以在此service方法中,使用反射机制
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求路径
		String uri = req.getRequestURI();
		//获取方法名称,截取此路径(travel/user/add),得到方法名(add)
		String methodname = uri.substring(uri.lastIndexOf("/") + 1);
		//使用反射机制,获取方法对象,因为this代表谁调用此方法谁就是this
		try {
			Method method = this.getClass().getMethod(methodname,
					HttpServletRequest.class, HttpServletResponse.class);
			//执行方法
			method.invoke(this,req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象序列化为json串,并写回页面
	 * @param obj
	 * @param res
	 */
	public void writeValue(Object obj,HttpServletResponse res) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		res.setContentType("application/json;charset=utf-8");
		mapper.writeValue(res.getOutputStream(),obj);
	}

	/**
	 * 将对象序列化为json串
	 * @param obj
	 * @return
	 */
	public String writeValueAsString(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
}
