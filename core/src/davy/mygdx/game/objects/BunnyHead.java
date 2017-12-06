package davy.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import davy.mygdx.game.game.Assets;
import davy.mygdx.game.utils.Constants;

/**
 * 玩家
 * Created by wangyonghua on 2017/12/6.
 */

public class BunnyHead extends AbstractGameObject {

    public static final String TAG = BunnyHead.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

    public enum VIEW_DIRECTION {
        LEFT, RIGHT
    }

    //跳跃状态 站在石头（地面）上，降落状态，跳跃上升状态，跳跃后降落
    public enum JUMP_STATE {
        GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
    }

    private TextureRegion regHead;

    public VIEW_DIRECTION viewDirection;
    public float timeJumping;
    public JUMP_STATE jumpState;
    public boolean hasFeatherPowerup;
    public float timeLeftFeatherPowerup;

    public BunnyHead() {
        init();
    }

    private void init() {
        dimension.set(1, 1);

        regHead = Assets.instance.bunny.head;
        // 原点设置为对象中心
        origin.set(dimension.x/2, dimension.y/2);
        // 边界检测大小
        bounds.set(0, 0, dimension.x, dimension.y);

        // 物理属性
        terminalVelocity.set(3.0f, 4.0f);
        friction.set(12.0f, 0);
        acceleration.set(0.0f, -25.0f);

        // 初始方向
        viewDirection = VIEW_DIRECTION.RIGHT;
        // 跳跃状态
        jumpState = JUMP_STATE.FALLING;
        timeJumping = 0;
        // 飞行状态
        hasFeatherPowerup = false;
        timeLeftFeatherPowerup = 0;
    }

    public void setJumping(boolean jumpKeyPressed) {
        switch (jumpState) {
            case GROUNDED:
                if (jumpKeyPressed) {
                    timeJumping = 0;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
            case JUMP_RISING:
                if (!jumpKeyPressed) {
                    jumpState = JUMP_STATE.FALLING;
                }
                break;
            case FALLING://diao luo
            case JUMP_FALLING:
                if (jumpKeyPressed && hasFeatherPowerup) {
                    timeJumping = JUMP_TIME_OFFSET_FLYING;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
        }
    }

    public void setHasFeatherPowerup(boolean hasFeatherPowerup) {
        this.hasFeatherPowerup = hasFeatherPowerup;
        if (hasFeatherPowerup) {
            timeLeftFeatherPowerup = Constants.ITEM_FEATHER_POWERUP_DURATION;
        }
    }

    public boolean hasFeatherPowerup() {
        return hasFeatherPowerup;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
        }

        if (timeLeftFeatherPowerup > 0) {
            timeLeftFeatherPowerup -= deltaTime;
            if (timeLeftFeatherPowerup < 0) {
                //guanbi
                timeLeftFeatherPowerup = 0;
                setHasFeatherPowerup(false);
            }
        }
    }

    @Override
    protected void updateMotionY(float deltaTime) {
        switch (jumpState) {
            case GROUNDED:
                jumpState = JUMP_STATE.FALLING;
                break;
            case JUMP_RISING:
                // Keep track of jump time
                timeJumping += deltaTime;
                // Jump time left?
                if (timeJumping <= JUMP_TIME_MAX) {
                    // Still jumping
                    velocity.y = terminalVelocity.y;
                }
                break;
            case FALLING:
                break;
            case JUMP_FALLING:
                // Add delta times to track jump time
                timeJumping += deltaTime;
                // Jump to minimal height if jump key was pressed too short
                if (timeJumping > 0 && timeJumping >= JUMP_TIME_MIN) {
                    // Still falling
                    velocity.y = -terminalVelocity.y;
                }
        }

        if (jumpState != JUMP_STATE.GROUNDED) {
            super.updateMotionY(deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        TextureRegion reg = null;

        if (hasFeatherPowerup) {
            spriteBatch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
        }

        reg = regHead;
        spriteBatch.draw(reg.getTexture(), position.x, position.y, origin.x,
                origin.y, dimension.x , dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT,
                false);

        spriteBatch.setColor(1, 1, 1 ,1);
    }
}
