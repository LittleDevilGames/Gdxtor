package dev.edisoni;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import dev.edisoni.UIElements.UISprite;

import java.io.IOException;

/**
 * Created by evgenijpalenkov on 10.06.14.
 */
public class PanelButtons extends Table {
    public PanelButtons( Skin skin) {
        super(skin);
        TextButton buttonAddSprite = new TextButton("Add Sprite", skin);
        buttonAddSprite.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TextureRegion  textureRegion = Assets.getTexture(Assets.defaultSprite);
                UISprite uiSprite = new UISprite(textureRegion, Editor.CENTERX - textureRegion.getRegionWidth()/2,Editor.CENTERY - textureRegion.getRegionHeight()/2);
                Editor.addActorToScene(uiSprite);

                return false;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        TextButton buttonRemoveSprite = new TextButton("Remove Sprite", skin);
        buttonRemoveSprite.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Editor.removeActorFromScene();
                return false;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        // Buttons tools
        TextButton buttonCode = new TextButton("Edit code(not working)", skin);
        buttonCode.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    String pathFile = "out/production/Gdxtor/default.java";
                    Runtime.getRuntime().exec ("sublime/macos/sublime.app/Contents/SharedSupport/bin/subl " + pathFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        Window window = Editor.windowTools;
        add(buttonAddSprite).size(window.getWidth() - 50,25).padTop(15.0f);
        row();
        add(buttonRemoveSprite).size(window.getWidth() - 50, 25).padTop(5.0f);
        row();
        add(buttonCode).size(window.getWidth() - 50,25).padTop(5.0f);
        row();
        window.add(this).top().expand();
        window.row();
    }
}
