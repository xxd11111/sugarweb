package com.sugarcoat.orm;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

/**
 * expression包装工具类
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/6
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