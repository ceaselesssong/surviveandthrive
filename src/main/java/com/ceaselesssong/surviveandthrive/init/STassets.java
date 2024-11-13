package com.ceaselesssong.surviveandthrive.init;

import com.ceaselesssong.surviveandthrive.SurviveAndThrive;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;

import org.apache.logging.log4j.Logger;

public class STassets {

    public static void init() {
        ClientAssetsGenerator generator = new ClientAssetsGenerator();
        generator.register();
    }

    public static class ClientAssetsGenerator extends DynClientResourcesGenerator {

        protected ClientAssetsGenerator() {
            //here you pass the dynamic texture pack instance
            super(new DynamicTexturePack(SurviveAndThrive.res("generated_pack"), Pack.Position.TOP, false, false));
        }

        // generate here your assets
        @Override
        public void regenerateDynamicAssets(ResourceManager manager) {
        }

        @Override
        public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {
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
