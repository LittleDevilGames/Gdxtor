package dev.edisoni;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import dev.edisoni.UIElements.UISprite;
import org.lwjgl.opengl.GL11;

/**
 * Created by Edisoni on 09.06.14.
 */
public class Editor implements ApplicationListener {

    static Skin skin;

    public static Stage hud;
    public static Stage scene;
    public static Shape shape;

    public static Actor selectedElement;
    public static float CENTERX;
    public static float CENTERY;

    public static boolean MOVESCENE = false;

    static Window windowTools;
    static Window windowLaunch;
    static Window windowStruct;

    static PanelParams panelParams;
    static PanelButtons panelButtons;
    static PanelProjectTree panelProjectTree;

    @Override
    public void create() {
        Assets.load();

        CENTERX = (Gdx.graphics.getWidth() - 400) / 2;
        CENTERY = (Gdx.graphics.getHeight() - 100) / 2;

        hud = new Stage();
        scene = new Stage();
        shape = new Shape(scene.getCamera());
        scene.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    MOVESCENE = true;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    MOVESCENE = false;
                }
                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                OrthographicCamera camera = (OrthographicCamera)scene.getCamera();
                camera.zoom += amount/10.0f;
                return true;
            }
        });
        scene.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                if (MOVESCENE) {
                    ((OrthographicCamera) scene.getCamera()).translate(getDeltaX(), getDeltaY());
                }
            }
        });


        skin = new Skin(Gdx.files.internal("uiSkin/uiskin.json"));

        windowTools = new Window("Tools", skin);
        windowTools.setWidth(250);
        windowTools.setHeight(Gdx.graphics.getHeight());
        windowTools.setPosition(Gdx.graphics.getWidth() - windowTools.getWidth(), 0);
        windowTools.setResizable(false);
        windowTools.setMovable(false);
        windowTools.bottom();
        hud.addActor(windowTools);


        windowStruct = new Window("Project Structure", skin);
        windowStruct.setSize(220, Gdx.graphics.getHeight());
        windowStruct.setResizable(false);
        windowStruct.setMovable(false);
        windowStruct.setPosition(0, 0);
        hud.addActor(windowStruct);


        windowLaunch = new Window("Launch", skin);
        windowLaunch.setSize(Gdx.graphics.getWidth() - windowTools.getWidth() - windowStruct.getWidth(), 80);
        windowLaunch.setResizable(false);
        windowLaunch.setMovable(false);
        windowLaunch.setPosition(windowStruct.getWidth(), Gdx.graphics.getHeight() - windowLaunch.getHeight());
        hud.addActor(windowLaunch);


        panelButtons = new PanelButtons(skin);
        panelParams = new PanelParams(skin);
        panelProjectTree = new PanelProjectTree(skin);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(hud);
        inputMultiplexer.addProcessor(scene);

        // Set starting params;
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static void addActorToScene(Actor actor) {
        panelProjectTree.addGameObject(actor);
        actor.setZIndex(1);
        scene.addActor(actor);
    }

    public static void removeActorFromScene(Actor actor) {
        scene.getRoot().removeActor(actor);
        panelParams.hideParams();
    }

    public static void removeActorFromScene() {
        if (selectedElement != null) {
            scene.getRoot().removeActor(selectedElement);
            panelParams.hideParams();
            panelProjectTree.removeGameObject(selectedElement);
        }
    }

    public static void onSelected(Actor actor) {
        if (actor instanceof UISprite) {
            if (selectedElement != null) {
                UISprite element = (UISprite) selectedElement;
                element.unselect();
            }
            selectedElement = actor;
            ((UISprite) selectedElement).select();
        }
        panelParams.showParams(actor);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.drawOnBack();
        scene.act(Gdx.graphics.getDeltaTime());
        scene.draw();
        shape.drawOnFront();
        hud.act(Gdx.graphics.getDeltaTime());
        hud.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        hud.dispose();
        scene.dispose();
    }
}
