package tk.bongostudios.curtaincarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class CurtainExtension implements CarpetExtension {
    public static void noop() { }
    static {
        CarpetServer.manageExtension(new CurtainExtension());
    }

    @Override
    public void onGameStarted() {
        // let's /carpet handle our few simple settings
        CarpetServer.settingsManager.parseSettingsClass(CurtainSettings.class);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        // reloading of /carpet settings is handled by carpet
        // reloading of own settings is handled as an extension, since we claim own settings manager
    }

    @Override
    public void onTick(MinecraftServer server) {
        // no need to add this.
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) {
        //
    }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player) {
        //
    }
}
