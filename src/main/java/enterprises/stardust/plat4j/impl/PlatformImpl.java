package enterprises.stardust.plat4j.impl;

import enterprises.stardust.plat4j.api.Platform;
import enterprises.stardust.plat4j.api.cpu.CPU;
import enterprises.stardust.plat4j.impl.os.OperatingSystemImpl;
import enterprises.stardust.plat4j.impl.cpu.CPUImpl;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xtrm
 */
@Accessors(fluent = true)
public @Data class PlatformImpl implements Platform {
    private final OperatingSystemImpl os;
    private final CPU cpu;

    public static PlatformImpl current() {
        OperatingSystemImpl os = OperatingSystemImpl.fetchCurrent();
        CPUImpl processor = CPUImpl.fetchCurrent(os);
        return new PlatformImpl(os, processor);
    }
}
