package net.yxiao233.realmofdestiny.screen.button;

import net.minecraft.network.chat.Component;
import net.yxiao233.realmofdestiny.modInterfaces.screen.IModImage;
import org.cyclops.cyclopscore.client.gui.component.button.ButtonImage;
import org.cyclops.cyclopscore.client.gui.image.IImage;

public class ImageButton extends ButtonImage {
    public IImage image;
    public IModImage modImage;
    public final int finalY;
    public final int finalX;

    public ImageButton(int x, int y, Component narrationMessage, OnPress pressCallback, IImage image) {
        super(x, y, narrationMessage, pressCallback, image);
        if(image instanceof IModImage){
            this.modImage = (IModImage) image;
        }else{
            this.image = image;
        }
        this.finalY = y;
        this.finalX = x;
    }
}
