package davy.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by wangyonghua on 2017/12/5.
 */

public abstract class AbstractGameObject {

    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    public Vector2 velocity;//速度
    public Vector2 terminalVelocity;//最大速度
    public Vector2 friction;//摩擦力

    public Vector2 acceleration;//加速度
    public Rectangle bounds;//碰撞边缘

    public AbstractGameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    public void update(float deltaTime) {
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        //移动到最新位置
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    protected void updateMotionX(float deltaTime) {
        if (velocity.x != 0) {
            //应用摩擦力
            if (velocity.x > 0) {
                velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
            } else {
                velocity.x = Math.max(velocity.x + friction.x * deltaTime, 0);
            }
        }

        //应用加速度
        velocity.x += acceleration.x * deltaTime;
        //确保速度没超过最大值
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
    }

    protected void updateMotionY(float deltaTime) {
        if (velocity.y != 0) {
            //应用摩擦力
            if (velocity.y > 0) {
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y = Math.max(velocity.y + friction.y * deltaTime, 0);
            }
        }

        //应用加速度
        velocity.y += acceleration.y * deltaTime;
        //确保速度没超过最大值
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
    }

    public abstract void render(SpriteBatch spriteBatch);
}
