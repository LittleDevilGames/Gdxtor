package dev.edisoni;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import dev.edisoni.SceneElements.SCGameObject;

/**
 * Created by Edisoni on 09.06.14.
 */
public class Editor implements ApplicationListener {

    public static Skin skin;

    public static Stage hud;
    public static Stage scene;
    public static Shape shape;

    public static Actor selectedElement;
    public static float CENTERX;
    public static float CENTERY;

    public static float SPEEDMOVE = 5.0f;

    static Window windowTools;
    static Window windowLaunch;
    static Window windowStruct;
    static Window windowAssets;

    static PanelParams panelParams;
    static PanelButtons panelButtons;
    static PanelLaunchs panelLaunchs;
    static PanelProjectTree panelProjectTree;
    static PanelAssets panelAssets;

    @Override
    public void create() {
        Assets.load();
        Assets.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                onCreate();
                return false;
            }
        });
    }

    public static void onCreate() {
        CENTERX = (Gdx.graphics.getWidth() - 400) / 2;
        CENTERY = (Gdx.graphics.getHeight() - 100) / 2;

        hud = new Stage();
        scene = new Stage();
        shape = new Shape(scene.getCamera());
        scene.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x > 220 && x < 250 && y > 0 && y < 80) {
                    hud.setKeyboardFocus(null);
                }
                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
                camera.zoom += amount / 10.0f;
                panelLaunchs.updatePanel(camera.zoom);
                return true;
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


        windowAssets = new Window("Assets Provider", skin);
        windowAssets.setSize(1000, 800);
        windowAssets.setResizable(false);
        windowAssets.setMovable(true);
        windowAssets.setPosition(Gdx.graphics.getWidth() / 2 - windowAssets.getWidth() / 2, Gdx.graphics.getHeight() / 2 - windowAssets.getHeight() / 2);
        windowAssets.setVisible(false);
        hud.addActor(windowAssets);

        panelButtons = new PanelButtons(skin);
        panelParams = new PanelParams(skin);
        panelProjectTree = new PanelProjectTree(skin);
        panelLaunchs = new PanelLaunchs();
        panelAssets = new PanelAssets();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(hud);
        inputMultiplexer.addProcessor(scene);


        hud.getRoot().setName("Root");
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

    public static void onSelectedObject(Actor actor) {
        if (actor instanceof SCGameObject) {
            if (selectedElement != null) {
                SCGameObject element = (SCGameObject) selectedElement;
                element.unselect();
            }
            selectedElement = actor;
            ((SCGameObject) selectedElement).select();
        }
        panelParams.showParams(actor);
    }

    public static void onSelectTexture(String texture) {
        SCGameObject scGameObject = (SCGameObject) selectedElement;
        scGameObject.changeTexture(Assets.getTexture(texture));
        scGameObject.setTextureName(texture);
        panelParams.showParams(scGameObject);
        windowAssets.setVisible(false);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (shape != null) {
            shape.drawOnBack();
            this.controls();
            scene.act(Gdx.graphics.getDeltaTime());
            scene.draw();
            shape.drawOnFront();
            hud.act(Gdx.graphics.getDeltaTime());
            hud.draw();
        }

        Assets.update();
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

    public void controls() {
        Actor actor = hud.getKeyboardFocus();
        System.out.println("Actor : " + actor);
        if (actor==null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
                camera.translate(0, SPEEDMOVE);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
                camera.translate(0, -SPEEDMOVE);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
                camera.translate(SPEEDMOVE, 0.0f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
                camera.translate(-SPEEDMOVE, 0.0f);
            }
        }

    }
}
