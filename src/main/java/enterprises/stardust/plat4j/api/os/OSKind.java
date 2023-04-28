package enterprises.stardust.plat4j.api.os;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

/**
 * @author xtrm
 */
@ApiStatus.Internal
public interface OSKind {
    String title();

    String nativeFormat();

    Collection<OSFamily> families();

    default String mapLibraryName(String libraryName) {
        return String.format(nativeFormat(), libraryName);
    }
}
