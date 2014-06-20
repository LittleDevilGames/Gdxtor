package dev.edisoni;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import dev.edisoni.SceneElements.SCGameObject;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Edisoni on 11.06.14.
 */
public class PanelLaunchs extends Table {

    TextField textZoom;
    TextField textSpeedCam;
    TextField textField;
    OrthographicCamera camera;

    public PanelLaunchs() {
        super(Editor.skin);
        camera = (OrthographicCamera) Editor.scene.getCamera();

        Table tableLeft = new Table(Editor.skin);
        Label labelSceneName = new Label("Scene name : ", Editor.skin);
        tableLeft.add(labelSceneName).left();

        TextButton loadButton = new TextButton("Load Scene", Editor.skin);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String className = textField.getText();
                try {
                    FileInputStream fis = new FileInputStream(className + ".gdxlevel");
                    ObjectInputStream oin = new ObjectInputStream(fis);
                    Level level = (Level) oin.readObject();
                    System.out.println("Level : " + level.level);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        tableLeft.add(loadButton).size(150.0f, 25.0f).padLeft(15.0f);

        tableLeft.row();

        textField = new TextField("", Editor.skin);
        tableLeft.add(textField).size(150.0f, 25.0f);

        final TextButton saveButton = new TextButton("Save Scene", Editor.skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String className = textField.getText();
                String sourceCode = "\n" +
                        "import com.badlogic.gdx.scenes.scene2d.Group;\n" +
                        "import dev.edisoni.SceneElements.SCGameObject;\n" +
                        "import java.util.HashMap;\n" +
                        "\n" +
                        "/**\n" +
                        " * Created by Gdxtor on 20.06.14.\n" +
                        " */\n" +
                        "public class Level extends Group {\n" +
                        "\n" +
                        "    private HashMap<String,SCGameObject> gameObjects;\n" +
                        "\n" +
                        "    public Level() {\n" +
                        "        gameObjects = new HashMap<String, SCGameObject>();";
                Array<Actor> gameObjects = Editor.scene.getActors();

                for (int i = 0; i < gameObjects.size; i++) {
                    SCGameObject gameObject = (SCGameObject) gameObjects.get(i);

                    sourceCode += "gameObjects.put("+scGameObject.getNameObject()+","scGameObject);";
                }
                try {
                    FileOutputStream fos = new FileOutputStream(className + ".gdxlevel");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(level);
                    oos.flush();
                    oos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        tableLeft.add(saveButton).size(150.0f, 25.0f).padLeft(15.0f);
        add(tableLeft);


        Table tableRight = new Table(Editor.skin);

        tableRight.add("Zoom  Camera : ").right().padRight(30.0f);
        tableRight.add("Speed Camera : ").right().padRight(15.0f);
        tableRight.row();
        textZoom = new TextField("1.0", Editor.skin);
        textZoom.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                String text = textZoom.getText();
                if (isNumeric(text)) {
                    camera.zoom = Float.parseFloat(text);
                }
            }
        });
        tableRight.add(textZoom).right().padRight(15.0f).expand();
        textSpeedCam = new TextField("5.0", Editor.skin);
        textSpeedCam.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                String text = textSpeedCam.getText();
                if (isNumeric(text)) {
                    Editor.SPEEDMOVE = Float.valueOf(text);
                }

            }
        });
        tableRight.add(textSpeedCam);
        add(tableRight).right().expand();

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    Editor.hud.setKeyboardFocus(null);
                }
                return true;
            }
        });

        Editor.windowLaunch.add(this).fill().expand();
    }

    public void updatePanel(float zoom) {
        textZoom.setText(String.valueOf(zoom));
    }

    private boolean isNumeric(String x) {
        Pattern p = Pattern.compile("^\\d+(?:\\.\\d+)?$");
        Matcher m = p.matcher(x);
        return m.matches();
    }
}
