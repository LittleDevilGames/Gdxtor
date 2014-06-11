package dev.edisoni;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.edisoni.SceneElements.SCSprite;

/**
 * Created by evgenijpalenkov on 10.06.14.
 */
public class PanelParams extends Table {
    Actor selectedElement;

    TextField objectName;
    TextField textureName;

    public PanelParams( Skin skin) {
        super(skin);
        setTouchable(Touchable.enabled);
        Label labelParam = new Label("Param ",skin);
        Label labelValue = new Label("Value ",skin);
        add(labelParam);
        add(labelValue);
        row();

        add("Name : ");
        objectName = new TextField("name", skin);
        objectName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (selectedElement!=null) {
                    selectedElement.setName(objectName.getText());
                    Editor.panelProjectTree.refreshTree();
                }
            }
        });
        add(objectName);
        row();
        add("Texture : ");
        textureName = new TextField("texture",skin);
        textureName.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (selectedElement != null) {
                    String text = textureName.getText();
                    if (text.indexOf(".png") != -1) {
                        if (Gdx.files.internal(text).exists()) {
                            SCSprite SCSprite = (SCSprite)selectedElement;
                            SCSprite.changeSprite(new Texture(text));
                            SCSprite.setName(objectName.getText());
                            SCSprite.setTexture(textureName.getText());
                        } else {
                            //TODO: Console message about error;
                        }
                    }
                }
            }
        });
        add(textureName);
        row();
        add("Visible : ");
        SelectBox visibles = new SelectBox(skin);
        visibles.setItems(new String[] {"true","false"});
        add(visibles).size(textureName.getWidth(),textureName.getHeight());

        setVisible(false);
        Editor.windowTools.add(this);
    }

    public void showParams(Actor actor) {
        if (actor instanceof SCSprite) {
            selectedElement = actor;
            objectName.setText(actor.getName());
            textureName.setText(((SCSprite)actor).getTexture());
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
