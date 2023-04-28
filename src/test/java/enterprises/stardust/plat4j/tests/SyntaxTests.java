package enterprises.stardust.plat4j.tests;

import enterprises.stardust.plat4j.api.Platform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class SyntaxTests {
    @Test
    public void canFindPlatform() {
        Assertions.assertDoesNotThrow(() -> {
            Platform ignored = Platform.current();
        });
    }
}
