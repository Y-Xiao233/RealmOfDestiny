package net.yxiao233.realmofdestiny.config.machine;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;
import net.yxiao233.realmofdestiny.config.MachineConfig;

@ConfigFile.Child(MachineConfig.class)
public class PedestalConfig {
    @ConfigVal(comment = "Max Stored Power [FE] - Default: [10000 FE]")
    public static int maxStoredPower = 10000;
}
