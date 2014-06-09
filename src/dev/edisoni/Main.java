package dev.edisoni;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

    private static final String title = "LibGDX";
    private static final int widht = 1440;
    private static final int height = 900;

    public static void main(String[] args) {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.width = widht;
        configuration.height = height;
        configuration.title = title;
        configuration.resizable = false;
        new LwjglApplication(new Editor(),configuration);
    }
}
