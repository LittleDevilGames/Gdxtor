package dev.edisoni;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.edisoni.SceneElements.SCGameObject;

import java.util.ArrayList;

/**
 * Created by Edisoni on 10.06.14.
 */
public class PanelProjectTree  extends Table {

    ArrayList<Actor> listGameObjects = new ArrayList<Actor>();

    Tree.Node backgrounds;
    Tree.Node gameObjects;
    Tree.Node HUD;

    Skin skin;
    public PanelProjectTree(Skin skin) {
        this.skin = skin;
        setTouchable(Touchable.enabled);
        Tree tree = new Tree(skin);
        Tree.Node root = new Tree.Node(new Label("Root",skin));
        backgrounds = new Tree.Node(new Label("Backgrounds",skin));
        root.add(backgrounds);
        gameObjects = new Tree.Node(new Label("GameObjects",skin));
        root.add(gameObjects);
        HUD = new Tree.Node(new Label("HUD",skin));
        root.add(HUD);
        tree.add(root);


        Window window = Editor.windowStruct;

        add(tree).size(window.getWidth() - 50, 100).expand();
        Editor.windowStruct.add(this).top().expand();
    }
    public void addGameObject(Actor actor) {
        if (actor instanceof SCGameObject) {
            Tree.Node node = new Tree.Node(new Label(actor.getName(),skin));
            gameObjects.add(node);
            listGameObjects.add(actor);
        }
    }
    public void removeGameObject(Actor actor) {
        if (actor instanceof SCGameObject) {
            int index =  listGameObjects.indexOf(actor);
            gameObjects.getChildren().get(index).getActor().remove();
            gameObjects.getChildren().removeIndex(index);
            listGameObjects.remove(index);
        }
    }
    public void addHUD(Actor actor) {

    }
    public void addBackground(Actor actor) {

    }
    public void refreshTree() {
        for(int i = 0 ; i < listGameObjects.size(); i++) {
            ((Label)gameObjects.getChildren().get(i).getActor()).setText(listGameObjects.get(i).getName());
        }
    }
}
