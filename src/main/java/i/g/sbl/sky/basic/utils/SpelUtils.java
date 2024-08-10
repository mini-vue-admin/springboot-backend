package i.g.sbl.sky.basic.utils;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelUtils {

    public static String parseSPEL(String el, Object root) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(el);
        StandardEvaluationContext context = new StandardEvaluationContext(root);
        Object value = exp.getValue(context);

        return JsonUtils.toJson(value);
    }
}
