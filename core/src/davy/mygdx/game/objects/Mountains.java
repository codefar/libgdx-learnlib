package davy.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import davy.mygdx.game.game.Assets;

/**
 * Created by wangyonghua on 2017/12/6.
 */

public class Mountains extends AbstractGameObject {

    private TextureRegion regMountainLeft;
    private TextureRegion regMountainRight;
    private int length;

    public Mountains(int length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(10, 2);
        regMountainLeft = Assets.instance.levelDecoration.mountainLeft;
        regMountainRight = Assets.instance.levelDecoration.mountainRight;
        // shift mountain and extend length
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // distant mountains (dark gray)
        drawMountain(spriteBatch, 0.5f, 0.5f, 0.5f);
        // distant mountains (gray)
        drawMountain(spriteBatch, 0.25f, 0.25f, 0.7f);
        // distant mountains (light gray)
        drawMountain(spriteBatch, 0.0f, 0.0f, 0.9f);
    }

    private void drawMountain(SpriteBatch batch, float offsetX, float offsetY, float tintColor) {
        TextureRegion reg = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x * offsetX;
        float yRel = dimension.y * offsetY;

        //山区跨越整个关卡
        int mountainLength = 0;
        mountainLength += MathUtils.ceil(length / (2 * dimension.x));
        mountainLength += MathUtils.ceil(0.5f + offsetX);
        for (int i = 0; i < mountainLength; i++) {
            // mountain left
            reg = regMountainLeft;
            batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y
                            + yRel, origin.x, origin.y, dimension.x, dimension.y,
                    scale.x, scale.y, rotation, reg.getRegionX(),
                    reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
            xRel += dimension.x;
            // mountain right
            reg = regMountainRight;
            batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y
                            + yRel, origin.x, origin.y, dimension.x, dimension.y,
                    scale.x, scale.y, rotation, reg.getRegionX(),
                    reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
            xRel += dimension.x;
        }
        // reset color to white
        batch.setColor(1, 1, 1, 1);
    }
}
