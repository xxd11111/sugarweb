package com.sugarweb.framework.orm;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

/**
 * expression包装工具类
 *
 * @author xxd
 * @version 1.0
 */
public class ExpressionWrapper {

	private final BooleanExpression expression;

	public ExpressionWrapper(BooleanExpression expression) {
		this.expression = expression;
	}

	public static ExpressionWrapper of() {
		return new ExpressionWrapper(Expressions.TRUE);
	}

	public ExpressionWrapper and(boolean flag, BooleanExpression booleanExpression) {
		if (flag) {
			expression.and(booleanExpression);
		}
		return this;
	}

	public ExpressionWrapper and(BooleanExpression booleanExpression) {
		return and(true, booleanExpression);
	}

	public ExpressionWrapper or(boolean flag, BooleanExpression booleanExpression) {
		if (flag) {
			expression.or(booleanExpression);
		}
		return this;
	}

	public BooleanExpression build() {
		return expression;
	}

}