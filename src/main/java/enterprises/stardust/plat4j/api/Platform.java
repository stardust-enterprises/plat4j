package enterprises.stardust.plat4j.api;

import enterprises.stardust.plat4j.api.cpu.CPU;
import enterprises.stardust.plat4j.api.os.OS;
import enterprises.stardust.plat4j.impl.PlatformImpl;

/**
 * @author xtrm
 * @since 0.0.1
 */
public interface Platform {
    OS os();
    CPU cpu();

    static Platform current() {
        return PlatformImpl.current();
    }
}
