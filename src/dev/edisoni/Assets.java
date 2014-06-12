package dev.edisoni;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Edisoni on 10.06.14.
 */
public class Assets {

    private static AssetManager assetManager;
    private static ArrayList<Action> onLoaded = new ArrayList<Action>();

    public static String defaultSprite = "default.png";
    public static String selectionSprite = "uiSkin/sl.png";


    public static void load() {
        assetManager = new AssetManager();

        addLoadTexture(defaultSprite);
        addLoadTexture(selectionSprite);
    }

    public static void update() {
        if (!assetManager.update()) {

        } else {
            for(Iterator<Action> it = onLoaded.iterator() ; it.hasNext(); ) {
                Action action = it.next();
                action.act(1.0f);
                it.remove();
            }
        }
    }
    public static void addLoadTexture(String name) {
        if (!assetManager.isLoaded(name)) {
            assetManager.load(name, Texture.class);
        }
    }
    public static int getCount() {
        return assetManager.getLoadedAssets();
    }
    public static boolean isLoaded(String name) {
        return assetManager.isLoaded(name);
    }
    public static void addAction(Action action) {
        onLoaded.add(action);
    }
    public static Texture getTexture(int i) {
        String name =  assetManager.getAssetNames().get(i);
        return getTexture(name);
    }
    public static Texture getTexture(String name) {
        if (assetManager.isLoaded(name)) {
            return assetManager.get(name, Texture.class);
        } else {
            return assetManager.get(defaultSprite,Texture.class);
        }
    }
}
