package dev.edisoni.SceneElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import dev.edisoni.Assets;
import dev.edisoni.Editor;
import dev.edisoni.Shape;


/**
 * Created by Edisoni on 09.06.14.
 */
public class SCGameObject extends Actor {
    Sprite sprite;
    boolean selected = false;
    boolean dragged = false;
    boolean visible = true;
    String textureName;

    public SCGameObject(Texture textureRegion, float x, float y) {
        textureName = Assets.defaultSprite;
        setName("GameObject");
        sprite = new Sprite(textureRegion);
        sprite.setPosition(x, y);
        setBounds(x, y, sprite.getWidth(), sprite.getHeight());
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = true;
                Editor.onSelected(SCGameObject.this);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;
            }
        });

    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    public void changeTexture(Texture texture) {
        sprite.setTexture(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    public String getTexture() {
        return textureName;
    }

    public void setTextureName(String texture) {
        this.textureName = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (dragged) {
            Vector2 pos =  Editor.scene.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            float xx = pos.x;
            float yy = pos.y;
            xx -= sprite.getWidth() / 2;
            yy -= sprite.getHeight() / 2;
            sprite.setPosition(xx, yy);
            setPosition(xx, yy);
        }
        if (selected) {
            Editor.shape.drawRect(getX()-5,getY()-5,getWidth()+10,getHeight()+10,batch);
        }
        sprite.draw(batch);

    }
}
