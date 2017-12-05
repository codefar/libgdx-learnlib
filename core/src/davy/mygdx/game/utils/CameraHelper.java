package davy.mygdx.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import davy.mygdx.game.objects.AbstractGameObject;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MIN_ZOOM_OUT = 10.f;

	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;
	
	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}
	
	public void update(double deltaTime) {
		if(!hasTarget())
			return;
		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
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
	
	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}
	
	public AbstractGameObject getTarget() {
		return target;
	}

	public boolean hasTarget(AbstractGameObject target) {
		return this.target != null && this.target.equals(target);
	}
	
	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}