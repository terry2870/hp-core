/**
 * 
 */
package com.hp.core.jsptags.ognl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * @author huangping 2017年8月5日 上午12:03:47
 */
public class OgnlCache {
	
	static Logger log = LoggerFactory.getLogger(OgnlCache.class);

	private static final Map<String, Object> expressionCache = new ConcurrentHashMap<>();

	private OgnlCache() {
		// Prevent Instantiation of Static Class
	}

	public static Object getValue(String expression, Object root) {
		OgnlContext context = new OgnlContext(new HashMap<>());
		Object ognl = null;
		try {
			ognl = parseExpression(expression);
			context.setRoot(root);
			return Ognl.getValue(ognl, context, context.getRoot());
		} catch (OgnlException e) {
			log.error("Error evaluating expression={}", expression, e);
			throw new RuntimeException("Error evaluating expression '" + expression + "'. Cause: " + e.getMessage(), e);
		}
	}

	private static Object parseExpression(String expression) throws OgnlException {
		Object node = expressionCache.get(expression);
		if (node == null) {
			node = Ognl.parseExpression(expression);
			expressionCache.put(expression, node);
		}
		return node;
	}


}
