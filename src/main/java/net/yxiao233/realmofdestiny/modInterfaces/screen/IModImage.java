package net.yxiao233.realmofdestiny.modInterfaces.screen;

import net.yxiao233.realmofdestiny.modInterfaces.jei.ScreenElement;
import org.cyclops.cyclopscore.client.gui.image.IImage;

public interface IModImage extends IImage {
    <T extends ScreenElement> T getTexture();
    <T extends ScreenElement> T getPressTexture();
}
