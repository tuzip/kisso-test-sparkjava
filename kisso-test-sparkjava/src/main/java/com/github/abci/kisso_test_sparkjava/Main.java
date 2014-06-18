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

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试 SSO
 * <p>
 * @author   hubin
 * @Date	 2014-5-19
 */
public class Main {
	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * hosts
	 * --------------------------------
	 * 127.0.0.1 sso.test.com
	 */
	public static void main(String[] args) {
		setPort(8080);
		logger.info("kissoDemo init 8080");

		get(new LoginRoute("/login.html"));
		post(new LoginRoute("/login.html"));

		get(new ClientRoute("/client.html"));
		get(new LogoutRoute("/logout.html"));
	}
}
