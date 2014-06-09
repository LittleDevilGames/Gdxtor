package dev.edisoni.UIElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import dev.edisoni.Editor;

/**
 * Created by Edisoni on 09.06.14.
 */
public class UISprite extends Actor {
    Sprite sprite;
    boolean selected = false;
    boolean dragged  = false;
    String textureName;
    public UISprite(TextureRegion textureRegion, float x,float y) {
        textureName = textureRegion.getTexture().getTextureData().getType().name();
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
