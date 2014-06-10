package dev.edisoni.UIElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import dev.edisoni.Assets;
import dev.edisoni.Editor;

/**
 * Created by Edisoni on 09.06.14.
 */
public class UISprite extends Actor {
    Sprite sprite;
    boolean selected = false;
    boolean dragged  = false;
    boolean visible  = true;
    String textureName;

    public UISprite(TextureRegion textureRegion, float x,float y) {

        textureName = Assets.defaultSprite;
        setName("GameObject");

        sprite = new Sprite(textureRegion);
        sprite.setPosition(x, y);
        setBounds(x, y, sprite.getWidth(), sprite.getHeight());
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragged = true;
                Editor.onSelected(UISprite.this);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragged = false;
            }
        });

    }
    public void select() { selected = true;}
    public void unselect() {selected = false;}
    public void changeSprite(Texture texture) {
        sprite.setTexture(texture);
        sprite.setSize(texture.getWidth(),texture.getHeight());
        setBounds(getX(), getY(), texture.getWidth(), getHeight());
    }

    public String getTexture() {
        return textureName;
    }
    public void setTexture(String texture) {
        this.textureName = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (dragged) {
            float xx = Gdx.input.getX();
            float yy = Gdx.graphics.getHeight() - Gdx.input.getY();
            xx -= sprite.getWidth()/2;
            yy -= sprite.getHeight()/2;
            sprite.setPosition(xx,yy);
            setPosition(xx,yy);
        }
        if (selected) {
            sprite.setColor(1.0f,0.5f,0.5f,1.0f);
        } else {
            sprite.setColor(1.0f,1.0f,1.0f,1.0f);
        }
        sprite.draw(batch);
    }
}
