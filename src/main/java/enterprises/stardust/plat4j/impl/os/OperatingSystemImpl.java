package enterprises.stardust.plat4j.impl.os;

import enterprises.stardust.plat4j.api.os.OS;
import enterprises.stardust.plat4j.api.os.OSKind;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xtrm
 * @since 0.0.1
 */
@Accessors(fluent = true)
public @Data class OperatingSystemImpl implements OS {
    private final String title;
    private final String version;
    private final OSKind kind;

    public static OperatingSystemImpl fetchCurrent() {
        String name = System.getProperty("os.name");
        String version = System.getProperty("os.version");


        return new OperatingSystemImpl(name, version, null);
    }
}
