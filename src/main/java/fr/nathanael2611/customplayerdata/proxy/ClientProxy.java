package fr.nathanael2611.customplayerdata.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInitialization(File configFile) {
        super.preInitialization(configFile);
    }

    @Override
    public void initialization() {
        super.initialization();
    }
}
