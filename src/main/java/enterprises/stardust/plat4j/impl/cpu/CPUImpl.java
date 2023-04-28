package enterprises.stardust.plat4j.impl.cpu;

import enterprises.stardust.plat4j.api.cpu.CPUArch;
import enterprises.stardust.plat4j.api.cpu.CPU;
import enterprises.stardust.plat4j.impl.os.OperatingSystemImpl;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xtrm
 * @since 0.0.1
 */
@Accessors(fluent = true)
public @Data class CPUImpl implements CPU {
    private final CPUArch arch;
    private final short cores;
    private final short threads;
    private final short bitness;

    public static CPUImpl fetchCurrent(OperatingSystemImpl os) {
//        switch (os.type()) {
//            case
//        }

        return new CPUImpl(
//                ArchitectureImpl.current(),
            null,
            (short) Runtime.getRuntime().availableProcessors(),
            (short) Runtime.getRuntime().availableProcessors(),
            (short) 64
        );
    }
}
