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

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * 测试路由
 * <p>
 * @author   hubin
 * @Date	 2014-5-9 	 
 */
public class LogoutRoute extends Route {

	protected LogoutRoute(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		boolean rlt = SSOHelper.logout(request.raw(), response.raw());
		System.out.println("---- logout rlt ----" + rlt);
		return "logout...";
	}

}
