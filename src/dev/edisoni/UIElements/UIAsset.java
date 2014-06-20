package dev.edisoni.UIElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.edisoni.Assets;
import dev.edisoni.Editor;
import dev.edisoni.Shape;

/**
 * Created by Edisoni on 11.06.14.
**/

public class UIAsset extends Actor {

    Sprite sprite;
    Label label;
    String nameAsset;
    boolean selected;
    static BitmapFont font;

    public UIAsset(Texture texture, final String nameAsset) {
        this.nameAsset = nameAsset;
        sprite = new Sprite(texture);
        sprite.setPosition(getX(),getY());
        sprite.setSize(200,200);
        label = new Label(nameAsset, Editor.skin);
        setBounds(getX(),getY(),200,240);
        font = Assets.getFont(Assets.assetsWindowFont);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selected = true;
                Editor.onSelectTexture(nameAsset);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                selected = false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (selected) {
            Editor.shape.drawRect(getX()-5,getY()-5,getWidth()+10,getHeight()+10,batch);
        }
        sprite.setPosition(getX(),getY());
        setBounds(getX(),getY(),200,200);
        sprite.draw(batch);

        font.draw(batch,nameAsset,getX()+10,getY()-8);
    }

}
