package davy.mygdx.game.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;

import davy.mygdx.game.utils.CameraHelper;
import davy.mygdx.game.utils.Constants;

/**
 * Created by wangyonghua on 2017/12/4.
 */

public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getSimpleName();

    public CameraHelper cameraHelper;

    //当前关卡
    public Level level;
    //生命数
    public int lives;
    //分数
    public int score;

    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }

    private void initLevel() {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        // Camera controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveCamera(-camMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.RIGHT))
            moveCamera(camMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.UP))
            moveCamera(0, camMoveSpeed);
        if(Gdx.input.isKeyPressed(Keys.DOWN))
            moveCamera(0, -camMoveSpeed);
        if(Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);

        // Camera controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if(Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if(Gdx.input.isKeyPressed(Keys.PERIOD))
            cameraHelper.addZoom(-camZoomSpeed);
        if(Gdx.input.isKeyPressed(Keys.SLASH))
            cameraHelper.setZoom(1);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }


    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();

        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);

        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        return false;
    }
}
