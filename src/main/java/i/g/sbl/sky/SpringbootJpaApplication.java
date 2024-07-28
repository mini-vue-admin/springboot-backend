package i.g.sbl.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class SpringbootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaApplication.class, args);
        log.info("""
                                                                                            \s
                 .M""\"bgd `7MM               `7MM""\"Mq.                  *MM                \s
                ,MI    "Y   MM                 MM   `MM.                  MM                \s
                `MMb.       MM  ,MP'`7M'   `MF'MM   ,M9 `7Mb,od8 ,pW"Wq.  MM,dMMb.   .gP"Ya \s
                  `YMMNq.   MM ;Y     VA   ,V  MMmmdM9    MM' "'6W'   `Wb MM    `Mb ,M'   Yb\s
                .     `MM   MM;Mm      VA ,V   MM         MM    8M     M8 MM     M8 8M""\"""\"\s
                Mb     dM   MM `Mb.     VVV    MM         MM    YA.   ,A9 MM.   ,M9 YM.    ,\s
                P"Ybmmd"  .JMML. YA.    ,V   .JMML.     .JMML.   `Ybmd9'  P^YbmdP'   `Mbmmd'\s
                                       ,V                                                   \s
                                    OOb"                                                    \s
                """);
    }

}
