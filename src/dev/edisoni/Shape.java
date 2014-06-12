package dev.edisoni;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Edisoni on 11.06.14.
 */
public class Shape {
    ShapeRenderer shapeRenderer;
    Camera camera;
    float width = 3.0f;
    Texture textureSelection;
    public Shape(Camera camera) {
        shapeRenderer = new ShapeRenderer();
        this.camera = camera;
        textureSelection = Assets.getTexture(Assets.selectionSprite);
    }

    public void drawOnBack() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        setWidth(3.0f);
        for (int x = 0; x <= 1000; x += 50) {
            drawLine(x,0,x,1000);
        }
        for (int y = 0; y <= 1000; y += 50) {
            drawLine(0,y,1000,y);
        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    public void drawLine(float x1, float y1, float x2, float y2) {
        shapeRenderer.rectLine(new Vector2(x1,y1),new Vector2(x2,y2),width);
    }
    public void drawRect(float x,float y,float w,float h,Color color) {
        shapeRenderer.setColor(color);
        float offset = width/2;
        drawLine(x + offset,y,x+w-offset,y);
        drawLine(x+offset,y+h,x+w-offset,y+h);
        drawLine(x,y+offset,x,y+h-offset);
        drawLine(x+w,y+offset,x+w,y+h-offset);
        shapeRenderer.arc(x+w-offset,y+h-offset,width,0,90,5); // Top right
        shapeRenderer.arc(x+offset,y+offset,width,180,90,5); // Left bottom
        shapeRenderer.arc(x+offset,y+h-offset,width,90,90,5);
        shapeRenderer.arc(x+w-offset,y+offset,width,270,90,5);
    }
    public void drawRect(float x,float y,float w,float h,Batch batch) {
        batch.draw(textureSelection,x,y,w,h);
    }
    public void drawRect(float x,float y,float w,float h) {

    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void drawOnFront() {
        shapeRenderer.end();
    }
}
