package dev.edisoni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sun.tools.javac.util.Name;
import dev.edisoni.UIElements.UIAsset;

/**
 * Created by Edisoni on 12.06.14.
 */
public class PanelAssets extends Table {

    public PanelAssets() {
        Window window = Editor.windowAssets;

        TextField textURL = new TextField(System.getProperty("user.dir") + "/production/Gdxtor", Editor.skin);
        TextButton buttonRefresh = new TextButton("Reload", Editor.skin);

        buttonRefresh.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileHandle handle = new FileHandle(System.getProperty("user.dir") + "/production/Gdxtor");
                FileHandle[] newHandles = handle.list();
                for (FileHandle f : newHandles) {
                    if (!f.isDirectory()) {
                        String name = f.name();
                        if (name.indexOf(".png") != -1 || name.indexOf(".jpg") != -1) {
                            Assets.addLoadTexture(f.name());
                        }
                    }
                }
                Assets.addAction(new Action() {
                    @Override
                    public boolean act(float delta) {

                        // TODO : Auto calculate columns in assets provider

                        int column = 0;
                        int columnTotal = 3;
                        for (int i = 0; i < Assets.getCount(); i++) {

                            if (column < columnTotal) {
                                add(new UIAsset(Assets.getTexture(i), "LOLKA")).size(200, 200).pad(15.0f);
                                column++;
                            } else {
                                column = 0;
                                row();
                            }


                        }
                        return false;
                    }
                });
            }
        });

        Table tableTop = new Table(Editor.skin);
        tableTop.add(textURL).size(window.getWidth() - 170, 30);
        tableTop.add(buttonRefresh).size(150, 30).padLeft(10.0f);

        window.add(tableTop).size(window.getWidth(), 50);
        window.row();

        ScrollPane scrollPane = new ScrollPane(this, Editor.skin);
        window.add(scrollPane).size(window.getWidth(), window.getHeight() - 100);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

}
