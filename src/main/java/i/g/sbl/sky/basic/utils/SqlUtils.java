package i.g.sbl.sky.basic.utils;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

public class SqlUtils {

    public static StringExpression likeConcat(String field) {
        return Expressions.asString("%").concat(field).concat("%");
    }
}
