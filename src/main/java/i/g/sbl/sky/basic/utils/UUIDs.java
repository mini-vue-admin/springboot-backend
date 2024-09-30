package i.g.sbl.sky.basic.utils;

import com.fasterxml.uuid.Generators;

public class UUIDs {

    public static String MIN = "00000000-0000-0000-0000-000000000000";
    public static String MAX = "ffffffff-ffff-ffff-ffff-ffffffffffff";

    public static String v4() {
        return Generators.randomBasedGenerator().generate().toString();
    }

    public static String v7() {
        return Generators.timeBasedEpochRandomGenerator().generate().toString();
    }
}
