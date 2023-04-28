package enterprises.stardust.plat4j.api.os;

import enterprises.stardust.plat4j.api.Platform;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author xtrm
 * @since 0.0.1
 */
public interface OS {
    @NotNull String title();
    String version();
    @NotNull OSKind kind();

    @NotNull static OS current() {
        return Platform.current().os();
    }

    /**
     * @author lambdagg
     * @author xtrm
     * @since 0.0.1
     */
    @Getter
    @Accessors(fluent = true)
    enum Kind implements OSKind {
        UNIX("Unix"),
        LINUX("Linux"),
        SOLARIS("Solaris"),
        AIX("ATX"),
        HAIKU("Haiku"),

        BSD("BSD"),
        FREE_BSD("FreeBSD"),
        OPEN_BSD("OpenBSD"),
        NET_BSD("NetBSD"),
        DRAGONFLY_BSD("DragonFlyBSD"),

        NT("NT", NativeFormat.NT_LIKE),
        WINDOWS("Windows", NativeFormat.NT_LIKE),

        MACOS("macOS", NativeFormat.MAC_OS),

        UNKNOWN("unknown", NativeFormat.UNKNOWN);

        private final @NotNull String title;
        private final @NotNull NativeFormat enumNativeFormat;
        private final Collection<OSFamily> families;

        Kind(String title, OSFamily... families) {
            this(title, NativeFormat.UNIX_LIKE, families);
        }

        Kind(String title, NativeFormat nativeFormat, OSFamily... families) {
            this.title = title;
            this.enumNativeFormat = nativeFormat;
            this.families = Arrays.asList(families);
        }

        public String nativeFormat() {
            return this.enumNativeFormat.format;
        }

        @ApiStatus.Internal
        public enum NativeFormat {
            NT_LIKE("%s.dll"),
            UNIX_LIKE("lib%s.so"),
            MAC_OS("lib%s.dylib"),
            UNKNOWN("%s");

            public final String format;

            NativeFormat (String format) {
                this.format = format;
            }
        }
    }
}
