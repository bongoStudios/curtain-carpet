package tk.bongostudios.curtaincarpet;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.*;

public class CurtainSettings {
    @Rule(desc = "Dispensers can feed animals", category = {EXPERIMENTAL, FEATURE})
    public static boolean dispensersFeedAnimals = false;
    
    @Rule(desc = "Dispensers can toggle things", category = {EXPERIMENTAL, FEATURE})
    public static boolean dispensersToggleThings = false;

    @Rule(desc = "Anvils that fall on cobblestone make sand", category = {EXPERIMENTAL, FEATURE})
    public static boolean renewableSand = false;
}
