package davy.mygdx.game.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by wangyonghua on 2017/12/4.
 */

public class WorldController {
    private static final String TAG = WorldController.class.getSimpleName();

    public Sprite[] testSprites;
    public int selectedSprites;

    public WorldController() {
        init();
    }

    private void init() {
        initTestSprites();
    }

    public void update(float deltaTime) {
        updateTestObjects(deltaTime);
    }

    private void initTestSprites() {
        testSprites = new Sprite[5];

        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);
        Texture texture = new Texture(pixmap);

        //使用文理数组创建精灵
        for (int i = 0; i < testSprites.length; i++) {
            Sprite sprite = new Sprite(texture);
            sprite.setSize(1, 1);
            //设置精灵的原点设置为中心
            sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);

            //随机坐标
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            sprite.setPosition(randomX, randomY);

            testSprites[i] = sprite;
        }

        selectedSprites = 0;
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

    private void updateTestObjects(float deltaTime) {
        float rotation = testSprites[selectedSprites].getRotation();
        //以90度没每秒旋转精灵
        rotation += 90 * deltaTime;
        rotation %= 360;
        testSprites[selectedSprites].setRotation(rotation);
    }
}
