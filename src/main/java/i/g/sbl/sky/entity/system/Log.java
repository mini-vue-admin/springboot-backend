package i.g.sbl.sky.entity.system;

import i.g.sbl.sky.basic.cons.system.LogLevel;
import i.g.sbl.sky.basic.cons.system.LogResultStatus;
import i.g.sbl.sky.basic.cons.system.LogType;
import i.g.sbl.sky.config.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 系统审计日志表
 */
@Data
@Entity
@Table(name = "sys_log")
public class Log extends BaseEntity {

    /**
     * 操作描述
     */
    private String msg;

    /**
     * 危险级别(1普通 2警告 2危险)
     */
    private LogLevel level;

    /**
     * 操作类型(1认证操作 2系统操作)
     */
    private LogType type;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 操作结果状态(0失败 1成功)
     */
    private LogResultStatus resultStatus;

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
