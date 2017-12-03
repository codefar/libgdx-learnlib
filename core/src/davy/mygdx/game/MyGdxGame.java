package davy.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import davy.mygdx.game.game.WorldController;
import davy.mygdx.game.game.WorldRenderer;

public class MyGdxGame extends ApplicationAdapter {

	private static final String TAG = MyGdxGame.class.getSimpleName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

    private boolean paused;

	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
        paused = false;
	}

	@Override
	public void render () {
        if (!paused) {
            worldController.update(Gdx.graphics.getDeltaTime());
        }
		Gdx.gl.glClearColor(100/255.0f, 149/255.0f, 237255.0f, 255/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
	}

    @Override
    public void pause() {
        super.pause();
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        paused = false;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        worldRenderer.resize(width, height);
    }

    @Override
	public void dispose () {
        worldRenderer.dispose();
	}
}
