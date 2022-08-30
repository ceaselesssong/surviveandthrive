package galena.oreganized.api;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureDeconstructor {

    public static int getBrightessColorFromBlock(Block block, BlockPos blockPos) {
        InputStream inputStream;
        BufferedImage image;
        Block baseProperties = block;
        if (ForgeRegistries.BLOCKS.getKey(block).getPath().contains("copper")) baseProperties = Blocks.COPPER_BLOCK;
        try {
            String id = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getTexture(baseProperties.defaultBlockState(), Minecraft.getInstance().level, blockPos).getName().getNamespace();
            inputStream = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(id, "textures/block/" + ForgeRegistries.BLOCKS.getKey(baseProperties).getPath() + ".png")).get().open();
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        int brightestColor = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixelRGB = image.getRGB(x, y);
                int pixelRGB$toDecimal = (((pixelRGB >> 16) & 0xFF) << 16) + (((pixelRGB >> 8) & 0xFF) << 8) + ((pixelRGB) & 0xFF);
                if (pixelRGB$toDecimal > brightestColor) {
                    brightestColor = pixelRGB$toDecimal;
                }
            }
        }
        return modifyColorBrightness(brightestColor, -0.068F);
    }

    public static int modifyColorBrightness(int color, float brightness) {
        int R = (color >> 16) & 0xFF;
        int G = (color >> 8) & 0xFF;
        int B = color & 0xFF;
        float[] HSB = Color.RGBtoHSB(R, G, B, new float[4]);
        return Color.HSBtoRGB(HSB[0], HSB[1], HSB[2] + brightness);
    }
}
