package dev.edisoni;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.edisoni.UIElements.UISprite;

import java.util.ArrayList;

/**
 * Created by Edisoni on 09.06.14.
 */
public class Editor implements ApplicationListener {

    Skin skin;
    Stage stage;

    ArrayList<Actor> listGameObjects = new ArrayList<Actor>();
    ArrayList<Actor> listGUIObjects  = new ArrayList<Actor>();
    ArrayList<Actor> listBacgroundObjects = new ArrayList<Actor>();

    public static Actor selectedElement;

    public float CENTERX;
    public float CENTERY;


    @Override
    public void create() {
        CENTERX = (Gdx.graphics.getWidth() - 400)/2;
        CENTERY = (Gdx.graphics.getHeight()- 100)/2;

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiSkin/uiskin.json"));

        final TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("ball.png")));


        final Window windowTools = new Window("Tools", skin);
        windowTools.setWidth(400);
        windowTools.setHeight(Gdx.graphics.getHeight());
        windowTools.setPosition(Gdx.graphics.getWidth() - windowTools.getWidth(), 0);
        windowTools.setResizable(false);
        windowTools.setMovable(false);
        windowTools.bottom();
        stage.addActor(windowTools);



        final Window windowLaunch = new Window("Launch",skin);
        windowLaunch.setSize(Gdx.graphics.getWidth()- windowTools.getWidth(), 80);
        windowLaunch.setResizable(false);
        windowLaunch.setMovable(false);
        windowLaunch.setPosition(0,Gdx.graphics.getHeight()-windowLaunch.getHeight());
        stage.addActor(windowLaunch);

        final Window windowCode = new Window("Code",skin);
        windowCode.setMovable(true);
        windowCode.setResizable(true);
        windowCode.setSize(600,400);
        windowCode.setVisible(false);
        windowCode.setPosition(Gdx.graphics.getWidth()/2 - windowCode.getWidth()/2,Gdx.graphics.getHeight()/2- windowCode.getHeight()/2);
        stage.addActor(windowCode);



        // Buttons tools
        TextButton buttonAddSprite = new TextButton("Add Sprite", skin);
        windowTools.add(buttonAddSprite).size(380, 30).padTop(10.0f).top().expand();
        buttonAddSprite.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                UISprite uiSprite = new UISprite(textureRegion, CENTERX - textureRegion.getRegionWidth()/2,CENTERY - textureRegion.getRegionHeight()/2);
                Editor.this.addActor(uiSprite);
                return false;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        windowTools.row();
        // Buttons tools
        TextButton buttonCode = new TextButton("Add code", skin);
        windowTools.add(buttonCode).size(380, 30);
        buttonCode.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                windowCode.setVisible(true);
                return false;
            }
        });







        // Set starting params;
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
    public void addActor(Actor actor) {
        listGameObjects.add(actor);
        stage.addActor(actor);
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
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

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
