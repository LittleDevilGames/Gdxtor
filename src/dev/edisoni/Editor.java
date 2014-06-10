package dev.edisoni;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.edisoni.UIElements.UISprite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Edisoni on 09.06.14.
 */
public class Editor implements ApplicationListener {

    static Skin skin;
    static Stage stage;



    public static Actor selectedElement;
    public static float CENTERX;
    public static float CENTERY;

    static Window windowTools;
    static Window windowLaunch;
    static Window windowStruct;

    static PanelParams panelParams;
    static PanelButtons panelButtons;
    static PanelProjectTree panelProjectTree;

    @Override
    public void create() {
        Assets.load();

        CENTERX = (Gdx.graphics.getWidth() - 400)/2;
        CENTERY = (Gdx.graphics.getHeight()- 100)/2;

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiSkin/uiskin.json"));

        windowTools = new Window("Tools", skin);
        windowTools.setWidth(250);
        windowTools.setHeight(Gdx.graphics.getHeight());
        windowTools.setPosition(Gdx.graphics.getWidth() - windowTools.getWidth(), 0);
        windowTools.setResizable(false);
        windowTools.setMovable(false);
        windowTools.bottom();
        stage.addActor(windowTools);


        windowStruct = new Window("Project Structure",skin);
        windowStruct.setSize(220,Gdx.graphics.getHeight());
        windowStruct.setResizable(false);
        windowStruct.setMovable(false);
        windowStruct.setPosition(0,0);
        stage.addActor(windowStruct);


        windowLaunch = new Window("Launch",skin);
        windowLaunch.setSize(Gdx.graphics.getWidth()- windowTools.getWidth() - windowStruct.getWidth(), 80);
        windowLaunch.setResizable(false);
        windowLaunch.setMovable(false);
        windowLaunch.setPosition(windowStruct.getWidth(),Gdx.graphics.getHeight()-windowLaunch.getHeight());
        stage.addActor(windowLaunch);


        panelButtons = new PanelButtons(skin);
        panelParams = new PanelParams(skin);
        panelProjectTree = new PanelProjectTree(skin);

        // Set starting params;
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
    public static void addActorToScene(Actor actor) {
        panelProjectTree.addGameObject(actor);
        stage.addActor(actor);
    }
    public static void removeActorFromScene(Actor actor) {
        stage.getRoot().removeActor(actor);
        panelParams.hideParams();
    }
    public static void removeActorFromScene() {
        if (selectedElement!=null) {
            stage.getRoot().removeActor(selectedElement);
            panelParams.hideParams();
            panelProjectTree.removeGameObject(selectedElement);
        }
    }
    public static void onSelected(Actor actor) {
        if (actor instanceof UISprite) {
            if (selectedElement!=null) {
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        panelParams.drawDebug(stage);
        }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
