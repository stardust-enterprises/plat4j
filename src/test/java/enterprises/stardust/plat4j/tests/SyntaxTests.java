package enterprises.stardust.plat4j.tests;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class SyntaxTests {
    @Test
    public void test() {
        switch (Platform.current().os()) {
        }
    }
}
