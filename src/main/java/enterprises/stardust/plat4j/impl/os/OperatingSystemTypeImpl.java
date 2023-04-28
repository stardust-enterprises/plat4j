package enterprises.stardust.plat4j.impl.os;

import enterprises.stardust.plat4j.api.os.OSFamily;
import enterprises.stardust.plat4j.api.os.OSKind;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @author xtrm
 */
@Accessors(fluent = true)
public @Data class OperatingSystemTypeImpl implements OSKind {
    private final String title;
    private final String nativeFormat;
    private final Collection<OSFamily> families;
}
