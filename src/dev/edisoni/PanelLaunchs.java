package dev.edisoni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.logging.FileHandler;

/**
 * Created by Edisoni on 11.06.14.
 */
public class PanelLaunchs  extends Table {
    public PanelLaunchs() {
        TextButton textButton = new TextButton("Assets Provider",Editor.skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.windowAssets.setVisible(true);

            }
        });
        add(textButton);
        Editor.windowLaunch.add(this).fill().expand();
    }
}
