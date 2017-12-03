package davy.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MIN_ZOOM_OUT = 10.f;

	private Vector2 position;
	private float zoom;
	private Sprite target;
	
	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}
	
	public void update(double deltaTime) {
		if(!hasTarget())
			return;
		position.x = target.getX() + target.getOriginX();
		position.y = target.getY() + target.getOriginY();
	}

	public boolean hasTarget() {
		return target != null;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void addZoom(float amount) {
		setZoom(zoom + amount);
	}
	
	public void setZoom(float amount) {
		zoom = MathUtils.clamp(amount, MAX_ZOOM_IN, MIN_ZOOM_OUT);
	}
	
	public float getZoom() {
		return zoom;
	}
	
	public void setTarget(Sprite target) {
		this.target = target;
	}
	
	public Sprite getTarget() {
		return target;
	}

	public boolean hasTarget(Sprite target) {
		return this.target != null && this.target.equals(target);
	}
	
	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}