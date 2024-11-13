package com.ceaselesssong.surviveandthrive.init;

import com.ceaselesssong.surviveandthrive.SurviveAndThrive;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;

public class STdata {
    public static void init() {
        STdata.ServerDataGenerator generator = new ServerDataGenerator();
        generator.register();
    }


    public static class ServerDataGenerator extends DynServerResourcesGenerator {
        protected ServerDataGenerator() {
            //here you pass the dynamic texture pack instance
            super(new DynamicDataPack(SurviveAndThrive.res("generated_pack"), Pack.Position.TOP, true, true));        }

        // generate here your assets
        @Override
        public void regenerateDynamicAssets(ResourceManager manager) {
        }

        @Override
        public Logger getLogger() {
            return (Logger) SurviveAndThrive.LOGGER;
        }

        @Override
        public boolean dependsOnLoadedPacks() {
            return true;
        }
    }
}
