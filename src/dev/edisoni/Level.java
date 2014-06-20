package dev.edisoni;

import com.badlogic.gdx.scenes.scene2d.Group;
import dev.edisoni.SceneElements.SCGameObject;
import java.util.HashMap;

/**
 * Created by Gdxtor on 20.06.14.
 */
public class Level extends Group {

    private HashMap<String,SCGameObject> gameObjects;

    public Level() {
        gameObjects = new HashMap<String, SCGameObject>();
        SCGameObject scGameObject0 = new SCGameObject()
    }
    public void addObject(SCGameObject scGameObject) {
        gameObjects.put(scGameObject.getNameObject(),scGameObject);
    }
    public SCGameObject getGameObject(String name) {
        return gameObjects.get(name);
    }
    public void update() {

    }
}
