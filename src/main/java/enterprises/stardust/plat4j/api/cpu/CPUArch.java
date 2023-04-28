package enterprises.stardust.plat4j.api.cpu;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

/**
 * @author xtrm
 * @since 0.0.1
 */
@ApiStatus.Internal
public interface CPUArch {
    String title();
    Collection<String> identifiers();
}
