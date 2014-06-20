package dev.edisoni;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.edisoni.SceneElements.SCGameObject;

/**
 * Created by Edisoni on 10.06.14.
 */
public class PanelParams extends Table {
    Actor selectedElement;

    TextField objectName;
    TextButton textureButton;

    public PanelParams( Skin skin) {
        super(skin);
        setTouchable(Touchable.enabled);
        Label labelParam = new Label("Param ",skin);
        Label labelValue = new Label("Value ",skin);
        add(labelParam);
        add(labelValue);
        row();

        add("Name : ").padRight(10.0f);
        objectName = new TextField("name", skin);
        objectName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (selectedElement!=null) {
                    SCGameObject go = (SCGameObject)selectedElement;
                    go.changeName(objectName.getText());
                    Editor.panelProjectTree.refreshTree();
                }
            }
        });
        add(objectName);
        row();
        add("Texture:").padRight(10.0f);
        textureButton = new TextButton("default.png",Editor.skin);
        textureButton.setWidth(objectName.getWidth());
        textureButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.windowAssets.setVisible(true);
                Editor.windowAssets.toFront();
            }
        });

        add(textureButton).left();
        row();
        add("Visible : ").padRight(10.0f);
        SelectBox visibles = new SelectBox(skin);
        visibles.setItems(new String[] {"true","false"});
        add(visibles).size(textureButton.getWidth(),textureButton.getHeight());


        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode== Input.Keys.ENTER) {
                    Editor.hud.setKeyboardFocus(null);
                }
                return true;
            }
        });
        setVisible(false);
        Editor.windowTools.add(this);
    }

    public void showParams(Actor actor) {
        if (actor instanceof SCGameObject) {
            selectedElement = actor;
            objectName.setText(((SCGameObject) actor).getNameObject());
            textureButton.setText(((SCGameObject)actor).getTexture());
            textureButton.setWidth(objectName.getWidth());
            setVisible(true);
        }
    }
    public void hideParams() {
        setVisible(false);
    }

    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {

    }
}
