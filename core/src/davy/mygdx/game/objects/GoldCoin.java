package davy.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import davy.mygdx.game.game.Assets;

/**
 * 金币
 * Created by wangyonghua on 2017/12/6.
 */

public class GoldCoin extends AbstractGameObject {

    private TextureRegion regGoldCoin;
    public boolean collected;

    public GoldCoin() {
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);

        regGoldCoin = Assets.instance.goldCoin.goldCoin;
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (collected) {
            return;
        }

        TextureRegion reg = null;
        reg = regGoldCoin;
        spriteBatch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y,
                dimension.x, dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);

    }

    public int getScore() {
        return 100;
    }
}
