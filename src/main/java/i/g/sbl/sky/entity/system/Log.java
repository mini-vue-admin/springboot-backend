package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import i.g.sbl.sky.basic.cons.system.Level;
import i.g.sbl.sky.basic.cons.system.Type;
import i.g.sbl.sky.basic.cons.system.ResultStatus;

/**
* 系统审计日志表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_log")
public class Log extends BaseEntity {

    /**
     * 操作描述
     */
    private String msg;

    /**
     * 危险级别(正常:1, 警告:2, 危险:3)
     */
    private Level level;

    /**
     * 操作类型(认证操作:1, 系统操作:2)
     */
    private Type type;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 操作结果状态(失败:0, 成功:1)
     */
    private ResultStatus resultStatus;

    /**
     * 失败原因
     */
    private String failedReason;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * 操作参数
     */
    private String parameters;

    /**
     * 操作结果
     */
    private String result;

    public void copyNonNulls(Log log) {
        Mapper.INSTANCE.map(log, this);
    }

    @org.mapstruct.Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(Log source, @MappingTarget Log target);
    }
}
