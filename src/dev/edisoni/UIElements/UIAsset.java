package dev.edisoni.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import dev.edisoni.Editor;

/**
 * Created by evgenijpalenkov on 11.06.14.
 */
public class UIAsset extends Actor {

    Sprite sprite;
    Label label;
    String nameAsset;
    public UIAsset(Texture texture,String nameAsset) {
        this.nameAsset = nameAsset;
        sprite = new Sprite(texture);
        sprite.setPosition(getX(),getY());
        label = new Label(nameAsset, Editor.skin);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        super.draw(batch,parentAlpha);
    }

}
