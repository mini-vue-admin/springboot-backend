package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.cons.system.Gender;
import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.entity.system.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class DetailedUser extends User {
    /**
     * 匿名用户
     */
    public static final DetailedUser ANONYMOUS = new DetailedUser();

    static {
        ANONYMOUS.setUsername("anonymous");
        ANONYMOUS.setNickname("anonymous");
        ANONYMOUS.setStatus(Status.enabled);
        ANONYMOUS.setGender(Gender.unknown);
    }

    private List<String> roles;

    public DetailedUser() {
        this.roles = new ArrayList<>();
    }

    public DetailedUser(User user) {
        this();
        Mapper.INSTANCE.map(user, this);
    }

    @org.mapstruct.Mapper
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        @Mapping(target = "roles", ignore = true)
        void map(User user, @MappingTarget DetailedUser detailedUser);
    }
}
