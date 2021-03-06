package dev.edisoni.SceneElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import dev.edisoni.Assets;
import dev.edisoni.Editor;

import java.io.Serializable;


/**
 * Created by Edisoni on 09.06.14.
 */
public class SCGameObject extends Actor  {

    String objectName;
    String textureName;
    Sprite sprite;

    boolean selected = false;
    boolean dragged = false;
    boolean visible = true;

    float offsetX = 0.0f;
    float offsetY = 0.0f;


    Action onCreate;
    Action onUpdate;


    public SCGameObject(Texture textureRegion, float x, float y) {
        textureName = Assets.defaultSprite;
        changeName("GameObject");
        sprite = new Sprite(textureRegion);
        sprite.setPosition(x, y);
        setBounds(x, y, sprite.getWidth(), sprite.getHeight());
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = true;
                offsetX = x - getX();
                offsetY = y - getY();
                Editor.onSelectedObject(SCGameObject.this);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;
            }
        });
//        onCreate.act(1.0f);
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }
    public void setActions(Action actionOnStart,Action actionOnUpdate) {
        onCreate = actionOnStart;
        onUpdate = actionOnUpdate;
    }

    public void changeTexture(Texture texture) {
        sprite.setTexture(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
    }
    public void changeName(String name) {
        this.objectName = name;
        setName(name);
    }
    public String getNameObject() {
        return objectName;
    }
    public String getTexture() {
        return textureName;
    }

    public void setTextureName(String texture) {
        this.textureName = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
      //onUpdate.act(Gdx.graphics.getDeltaTime());
        if (dragged) {
            Vector2 pos = Editor.scene.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            float xx = pos.x + offsetX;
            float yy = pos.y + offsetY;
            sprite.setPosition(xx, yy);
            setPosition(xx, yy);
        }
        if (selected) {
            Editor.shape.drawRect(getX() - 5, getY() - 5, getWidth() + 10, getHeight() + 10, batch);
        }
        sprite.draw(batch);

    }
}
