package enterprises.stardust.plat4j.api.cpu;

import com.sun.jna.ELFAnalyserWrapper;
import enterprises.stardust.interstellair.api.Burst;
import enterprises.stardust.plat4j.api.os.OS;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Comparator;

/**
 * @author xtrm
 */
public interface CPU {
    CPUArch arch();
    short cores();
    short threads();
    short bitness();

    /**
     * @author lambdagg
     * @author xtrm
     * @since 0.0.1
     */
    @Getter
    @Accessors(fluent = true)
    enum Arch implements CPUArch {
        /**
         * The standard (or at least most common) 64-bits architecture (x86_64, amd64, x64).
         */
        X86_64("x86_64", "x86_64", "amd64", "x64"),

        /**
         * The standard (or at least most common) 32-bits x86 architecture (i3, i4, i5, i6 & x86).
         */
        X86("x86", "i386", "i486", "i586", "i686", "x86", "amd32"),

        /**
         * The non-aarch ARM architecture, 32-bits.
         */
        ARM("arm", "armv7l", "armv7", "armel", "armle", "armv", "arm"),

        /**
         * The aarch ARM architecture, 32-bits (aarch32, arm32).
         */
        AARCH("aarch32", "aarch32", "arm32"),

        /**
         * The aarch ARM architecture, 64-bits (aarch64, arm64).
         */
        AARCH_64("aarch64", "aarch64", "arm64"),

        /**
         * The MIPS architecture, 32-bits (mipsle, mipsel, mips).
         * <a href="https://github.com/golang/go/issues/18622#issuecomment-272060013">...</a>
         */
        MIPS("mips", "mipsle", "mipsel", "mips"),

        /**
         * The MIPS architecture, 64-bits (mips64).
         */
        MIPS_64("mips64", "mips64", "mips64el", "mips"),

        /**
         * The PowerPC architecture, 32-bits (ppcel, pccle, powerpc, ppc).
         */
        PPC("powerpc", "ppcel", "ppcle", "powerpc", "ppc"),

        /**
         * The PowerPC architecture, 64-bits (ppc64, powerpc64, ppc64el, ppc64le).
         */
        PPC_64("ppc64", "ppc64", "powerpc64", "ppc64el", "ppc64le"),

        /**
         * The S390X architecture, 64-bits (s390).
         */
        S390X("s390x", "s390"),

        /**
         * The SPARCv9 architecture, 64-bits (sparcv9, sparc).
         */
        SPARCV9("sparcv9", "sparcv9", "sparc"),

        /**
         * The unknown architecture type, if we ever need it.
         */
        UNKNOWN("unknown");

        public static final String CURRENT_RAW;
        public static final @NotNull Arch CURRENT;

        static {
            // CURRENT_RAW
            String currentRaw = null;
            {
                String[] envs = new String[]{"PROCESSOR_ARCHITECTURE", "PROCESSOR_ARCHITEW6432"};
                for (String env : envs) {
                    if (currentRaw == null) {
                        try {
                            currentRaw = System.getenv(env);
                        } catch (Throwable ignored) {
                        }
                    }
                }

                if (currentRaw == null) {
                    currentRaw = System.getProperty("os.arch");
                }

                if (currentRaw == null) {
                    try {
                        currentRaw = new BufferedReader(
                            new InputStreamReader(
                                new ProcessBuilder("uname", "-m").start().getInputStream()
                            )
                        ).readLine();
                    } catch (Throwable ignored) {
                    }
                }

                if (currentRaw != null) {
                    currentRaw = currentRaw.toLowerCase();

                    if (currentRaw.equals("zarch_64")) {
                        currentRaw = "s390x";
                    } else if (currentRaw.equals("ppc64") && System.getProperty("sun.cpu.endian").equalsIgnoreCase("little")) {
                        // https://bugs.openjdk.java.net/browse/JDK-8073139
                        currentRaw = "ppc64le";
                    } else if (currentRaw.equals("arm") && OS.current().kind() == OS.Kind.LINUX) {
                        boolean isSoftFloat = false;
                        try {
                            File javaBinary = new File("/proc/self/exe");
                            isSoftFloat = javaBinary.exists() &&
                                ELFAnalyserWrapper.isArmHardFloat(javaBinary.getCanonicalPath());
                        } catch (Throwable ignored) {
                        }

                        if (isSoftFloat) {
                            currentRaw = "armel";
                        }
                    }
                }
            }
            CURRENT_RAW = currentRaw;

            // CURRENT
            Arch current = Arch.UNKNOWN;
            if (currentRaw != null) {
                final int j = Burst.ofArray(Arch.values())
                    .max(Comparator.comparingInt((a) -> a.identifiers.length))
                    .get().identifiers.length;

                for (int i = 0; i < j; i++) {
                    for (Arch a : Arch.values()) {
                        if (a.identifiers.length > i) {
                            String id = a.identifiers[i];
                            if (CURRENT_RAW.contains(id)) {
                                // Bitness check?
                                current = a;
                            }
                        }
                    }
                }
            }

            CURRENT = current;
        }

        private final String title;
        private final String[] identifiers;

        Arch(String title, String... identifiers) {
            this.title = title;
            this.identifiers = identifiers;
        }
    }
}
