/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abci.kisso_test_sparkjava;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityRoute;

import com.github.tuzip.sso.LoginHelper;
import com.github.tuzip.sso.SSOToken;
import com.github.tuzip.sso.client.SSOHelper;
import com.github.tuzip.sso.common.CookieHelper;
import com.github.tuzip.sso.common.util.RandomUtil;
import com.github.tuzip.sso.waf.request.WafRequestWrapper;

/**
 * 登录路由
 * <p>
 * @author   hubin
 * @Date	 2014-5-9 	 
 */
public class LoginRoute extends VelocityRoute {

	protected LoginRoute(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String htmltpl = "login.html";
		SSOToken token = (SSOToken) SSOHelper.getToken(request.raw());
		if (token == null) {
			/**
			 * 正常登录
			 * 需要过滤sql及脚本注入
			 */
			WafRequestWrapper wr = new WafRequestWrapper(request.raw());
			String name = wr.getParameter("userid");
			if (name != null && !"".equals(name)) {
				/**
				 * 设置登录 Cookie
				 */
				SSOToken st = new SSOToken(request.raw());
				st.setUserId(name);
				LoginHelper.authSSOCookie(request.raw(), response.raw(), st);

				/**
				 * 设置 js 可获取 Cookie
				 */
				StringBuffer str = new StringBuffer();
				str.append("kisso-");
				str.append(System.currentTimeMillis());
				str.append("-");
				str.append(RandomUtil.getCharacterAndNumber(6));
				str.append("v");
				CookieHelper.addCookie(response.raw(), "uid", str.toString());
				//CookieHelper.addCookie(response.raw(), null, "/", "vkouser", str.toString(), -1, true, false);
				htmltpl = "index.html";
			}
		} else {
			htmltpl = "index.html";
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("hello", "Velocity World");

		// The wm files are located under the resources directory
		//hello.wm
		return modelAndView(model, htmltpl);
	}
}
