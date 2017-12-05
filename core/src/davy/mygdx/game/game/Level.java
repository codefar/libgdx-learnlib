package davy.mygdx.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import davy.mygdx.game.objects.AbstractGameObject;
import davy.mygdx.game.objects.Clouds;
import davy.mygdx.game.objects.Mountains;
import davy.mygdx.game.objects.Rock;
import davy.mygdx.game.objects.WaterOverlay;

/**
 * 关卡 包含一个用于表示所有的游戏对象的emnum数据类型，在关卡图片中，每个游戏对象都是用一个独一无二的
 * RGBA颜色作为游戏省份的ID，因为我们并不使用alaph通道， 因此游戏世界的所有的对象像素都是不透明的
 * Created by wangyonghua on 2017/12/6.
 */

public class Level {
    public static final String TAG = Level.class.getName();

    public enum BLOCK_TYPE {
        GOAL(255, 0, 0),                    // red
        EMPTY(0, 0, 0),                        // black
        ROCK(0, 255, 0),                    // green
        PLAYER_SPAWNPOINT(255, 255, 255),    // white
        ITEM_FEATHER(255, 0, 255),            // purple
        ITEM_GOLD_COIN(255, 255, 0);        // yellow

        private int color;

        private BLOCK_TYPE(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor(int color) {
            return color == this.color;
        }

        public int getColor() {
            return color;
        }
    }

    // 游戏对象
    public Array<Rock> rocks;

    // 装饰器对象
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level(String filename) {
        init(filename);
    }

    private void init(String filename) {
        // 游戏石头对象
        rocks = new Array<Rock>();

        // 加载关卡图片
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // 从图片的左上角逐行扫描至右下角
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); ++pixelY) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); ++pixelX) {
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                // 计算底部导读
                float baseHeight = pixmap.getHeight() - pixelY;
                // g获取当前颜色值
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                // 空白区域
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
                    // do nothing
                }
                // 石头
                else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor
                                + offsetHeight);
                        rocks.add((Rock) obj);
                    } else {
                        rocks.get(rocks.size - 1).increaseLength(1);
                    }
                }
                // unknown object/pixel color
                else {
                    int r = 0xff & (currentPixel >>> 24); // red channel
                    int g = 0xff & (currentPixel >>> 16); // green channel
                    int b = 0xff & (currentPixel >>> 8);  // blue channel
                    int a = 0xff & currentPixel;          // alpha channel
                    Gdx.app.debug(TAG, "unknown object at x<" + pixelX + "> y<"
                            + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }
        // decoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, -3.75f);

        // 释放内存
        pixmap.dispose();
        Gdx.app.debug(TAG, "level " + filename + " loaded");
    }

    public void update(float deltaTime) {
        clouds.update(deltaTime);
    }

    public void render(SpriteBatch batch) {
        // 渲染山丘
        mountains.render(batch);

        // 渲染石头
        for (int i = 0; i < rocks.size; ++i) {
            rocks.get(i).render(batch);
        }

        // 渲染水
        waterOverlay.render(batch);

        // 渲染云
        clouds.render(batch);
    }
}
