package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.cons.system.Gender;
import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.entity.system.Role;
import i.g.sbl.sky.entity.system.User;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DetailedUser extends User {
    public static final DetailedUser ANONYMOUSE = new DetailedUser();
    static {
        ANONYMOUSE.setUsername("anonymouse");
        ANONYMOUSE.setNickname("anonymouse");
        ANONYMOUSE.setStatus(Status.enabled);
        ANONYMOUSE.setGender(Gender.unknown);
    }

    private List<Role> role;

    public DetailedUser() {
        this.role = new ArrayList<Role>();
    }

    public DetailedUser(User user) {
        Mapper.INSTANCE.map(user, this);
    }

    @org.mapstruct.Mapper
    public interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        void map(User user, @MappingTarget DetailedUser detailedUser);
    }
}
