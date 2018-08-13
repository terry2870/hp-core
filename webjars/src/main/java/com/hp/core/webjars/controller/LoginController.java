package com.hp.core.webjars.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.core.common.beans.Response;
import com.hp.core.webjars.constants.BaseConstant;
import com.hp.core.webjars.enums.IdentityEnum;
import com.hp.core.webjars.interceptor.UrlInterceptor;
import com.hp.core.webjars.model.request.SysUserRequestBO;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;
import com.hp.core.webjars.service.ISysMenuService;
import com.hp.core.webjars.service.ISysUserService;
import com.hp.core.webjars.utils.SessionUtil;

/**
 * 登录controller
 * 
 * @author hp 2014-03-11
 */
@Controller
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private ISysUserService sysUserService;
	@Resource
	private ISysMenuService sysMenuService;
	@Resource
	private UrlInterceptor urlInterceptor;

	/**
	 * 打开登录页面
	 * @return
	 */
	@RequestMapping({"/", "/login"})
	public String login(HttpServletRequest request) {
		log.info("login start ");
		return "login";
	}
	
	/**
	 * 登出
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("logout start ");
		request.getSession().invalidate();
		response.sendRedirect("/login");
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("hhh", "asdasdasd");
		return "index";
	}
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public Response<Object> login(SysUserRequestBO request, HttpSession session) throws Exception {
		log.info("doLogin start with request={}", request);
		// 检查验证码
		if (StringUtils.isEmpty(request.getCheckCode())) {
			log.warn("doLogin error. checkCode is null. with request={}", request);
			return new Response<>(201, "验证码为空");
		}
		String sessionCheckCode = (String) session.getAttribute(BaseConstant.CHECK_CODE);
		if (!sessionCheckCode.equals(request.getCheckCode())) {
			log.warn("doLogin error. checkCode is error. with request={}", request);
			return new Response<>(201, "验证码错误");
		}

		// 调用登录
		SysUserResponseBO user = sysUserService.login(request);

		SessionUtil.getOperater().setUser(user);
		SessionUtil.getOperater().setSuperUser(IdentityEnum.checkIsSuperUser(user.getIdentity()));
		SessionUtil.getOperater().setManager(IdentityEnum.checkIsManager(user.getIdentity()));
		SessionUtil.getOperater().setNormalUser(IdentityEnum.checkIsNormalUser(user.getIdentity()));

		// 查询用户所能看到的菜单
		List<SysMenuResponseBO> menuList = sysMenuService.queryAllSysMenu();

		// 保存session
		session.setAttribute(BaseConstant.USER_SESSION, user);
		session.setAttribute(BaseConstant.USER_MENU, menuList);

		
		
		
		log.info("doLogin success with request={}", request);
		return new Response<>();
	}

	/**
	 * 获取图形验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/refeshCheckCode")
	public void refeshCheckCode(HttpServletRequest request, HttpServletResponse response) {
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(new Color(227, 234, 244));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(new Color(168, 188, 215));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(49, 65, 98));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}

		// 将认证码存入SESSION
		request.getSession().setAttribute(BaseConstant.CHECK_CODE, sRand);

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// 图象生效
		g.dispose();
		// 输出图象到页面
		try (
				OutputStream out = response.getOutputStream();
			) {
			ImageIO.write(image, "JPEG", out);
		} catch (Exception e) {
			log.error("error to create checkCode!", e);
		}
	}

}
