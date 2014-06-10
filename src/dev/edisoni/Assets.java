package dev.edisoni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by evgenijpalenkov on 10.06.14.
 */
public class Assets {
    public static TextureRegion region;
    public static String defaultSprite = "default.png";
    public static void load() {
        region = new TextureRegion(new Texture(defaultSprite));
    }
    public static TextureRegion getTexture(String name) {
        return region;
    }
}
