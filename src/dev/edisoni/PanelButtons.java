package dev.edisoni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import dev.edisoni.SceneElements.SCGameObject;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Edisoni on 10.06.14.
 */
public class PanelButtons extends Table {


    public PanelButtons( Skin skin) {
        super(skin);
        setTouchable(Touchable.enabled);
        TextButton buttonAddObject = new TextButton("Add GameObject", skin);
        buttonAddObject.getLabel().setAlignment(Align.left);

        buttonAddObject.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Texture texture = Assets.getTexture(Assets.defaultSprite);
                SCGameObject SCGameObject = new SCGameObject(texture, Editor.CENTERX - texture.getWidth() / 2, Editor.CENTERY - texture.getHeight() / 2);
                Editor.addActorToScene(SCGameObject);

                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        TextButton buttonRemoveObject = new TextButton("Remove GameObject", skin);
        buttonRemoveObject.getLabel().setAlignment(Align.left);
        buttonRemoveObject.addListener(new InputListener() {
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
        buttonCode.getLabel().setAlignment(Align.left);
        buttonCode.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               /* try {
                    String pathFile = "out/production/Gdxtor/default.java";
                    Runtime.getRuntime().exec ("sublime/macos/sublime.app/Contents/SharedSupport/bin/subl " + pathFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Assets.addLoadTexture("bg_game.png");
                Assets.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {
                        System.out.println("Loaded new image");
                        return false;
                    }
                });
                return false;
            }
        });
        Window window = Editor.windowTools;
        add(buttonAddObject).size(window.getWidth() - 50,25).padTop(15.0f);
        row();
        add(buttonRemoveObject).size(window.getWidth() - 50, 25).padTop(5.0f);
        row();
        add(buttonCode).size(window.getWidth() - 50,25).padTop(5.0f);
        row();
        window.add(this).top().expand();
        window.row();

    }
}
