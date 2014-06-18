/**
 * VelocityRoute.java
 * spark.template.velocity
 * Copyright (c) 2014, 北京微课创景教育科技有限公司版权所有.
*/

package spark.template.velocity;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import spark.ModelAndView;
import spark.TemplateViewRoute;

/**
 * Template View Route based on Apache Velocity.
 * 
 * Example:
 * 
 * <pre>
 * {@code
 *   get(new VelocityRoute("/hello") {
 *       public Object handle(final Request request, final Response response) {
 *           Map<String, Object> model = new HashMap<>();
 *           model.put("hello", "Velocity World");
 *           
 *           // The wm files are located under the resources directory
 *           return modelAndView(model, "hello.wm");
 *      }
 *   });
 * </pre>
 */
public abstract class VelocityRoute extends TemplateViewRoute {

	private final VelocityEngine velocityEngine;

	/**
	 * Constructor
	 * 
	 * @param path The route path which is used for matching. (e.g. /hello, users/:name)
	 */
	protected VelocityRoute(String path) {
		super(path);
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine = new VelocityEngine(properties);
	}

	/**
	 * Constructor
	 * 
	 * @param path The route path which is used for matching. (e.g. /hello, users/:name)
	 * @param acceptType the accept type
	 */
	protected VelocityRoute(String path, String acceptType) {
		super(path, acceptType);
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine = new VelocityEngine(properties);
	}

	/**
	 * Constructor
	 * 
	 * @param path The route path which is used for matching. (e.g. /hello, users/:name)
	 * @param velocityEngine The velocity engine, must not be null.
	 */
	protected VelocityRoute(String path, VelocityEngine velocityEngine) {
		super(path);
		if (velocityEngine == null) {
			throw new IllegalArgumentException("velocityEngine must not be null");
		}
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Constructor
	 * 
	 * @param path The route path which is used for matching. (e.g. /hello, users/:name)
	 * @param acceptType the accept type
	 * @param velocityEngine The velocity engine, must not be null.
	 */
	protected VelocityRoute(String path, String acceptType, VelocityEngine velocityEngine) {
		super(path, acceptType);
		if (velocityEngine == null) {
			throw new IllegalArgumentException("velocityEngine must not be null");
		}
		this.velocityEngine = velocityEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String render(ModelAndView modelAndView) {
		Template template = velocityEngine.getTemplate(modelAndView.getViewName());
		Object model = modelAndView.getModel();
		if (model instanceof Map) {
			Map<?, ?> modelMap = (Map<?, ?>) model;
			VelocityContext context = new VelocityContext(modelMap);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} else {
			throw new IllegalArgumentException("modelAndView must be of type java.util.Map");
		}
	}

}
