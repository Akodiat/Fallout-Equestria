package scripting;

import components.TransformationComp;

import math.MathHelper;
import math.Vector2;
import utils.GameTime;
import utils.Rectangle;

public class RandomMoving extends Behavior {

	public static final int DEFAULT_SPEED = 10;
	public static final float DEFAULT_DISTANCE = 700;

	private Vector2 targetPos;
	private Vector2 previousPos;
	private int lerpSpeed;
	private float distance;
	private float lerpAmount;
	private Rectangle area;

	public RandomMoving(Rectangle boundsToMoveWithin) {
		this.area = boundsToMoveWithin;
	}

	/**
	 * Copyconstructor
	 * @param other
	 */
	public RandomMoving(RandomMoving other) {
		this.area = other.area;
	}

	@Override
	public Object clone() {
		return new RandomMoving(this);
	}

	@Override
	public void start() {
		this.lerpSpeed = DEFAULT_SPEED;
		this.distance= DEFAULT_DISTANCE;
		this.lerpAmount = 0;

		TransformationComp transComp = this.Entity.getComponent(TransformationComp.class);
		transComp.setPosition(this.area.getCenter());
		this.previousPos = transComp.getPosition();
		this.targetPos = transComp.getPosition();
	}

	public void update(GameTime time) {

		TransformationComp transComp = this.Entity.getComponent(TransformationComp.class);

		if(lerpAmount>1){
			this.previousPos = targetPos;
			spawnNewTargetPos();
			lerpAmount = 0;
		}



		float amountToLerp = (float)(time.getElapsedTime().getTotalSeconds()/lerpSpeed);
		lerpAmount += amountToLerp;
		System.out.println(previousPos + "WDASDASD" + targetPos);
		Vector2 newPos = Vector2.smoothLerp(previousPos, targetPos, lerpAmount);

		transComp.setPosition(newPos);
	}

	private void spawnNewTargetPos() {
		Vector2 newTarget;
		while(true){
			float angle = MathHelper.Tau*(float)Math.random();

			Vector2 direction = new Vector2((float)Math.cos(angle), (float)Math.sin(angle));
			newTarget = Vector2.add(this.targetPos, Vector2.mul(this.distance, direction));

			if (this.area.intersects(newTarget)){
				break;
			}
		}
		this.targetPos = newTarget;
	}
}
