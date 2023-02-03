package ga.tianyuge.test;

import com.fasterxml.uuid.Generators;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2023/01/05 23:05
 */
public class UUIDTest {

    @Test
    public void uuid1Test() {
        UUID generate = Generators.timeBasedGenerator().generate();
        System.out.println(generate.timestamp());
        System.out.println(System.currentTimeMillis());
    }
}
