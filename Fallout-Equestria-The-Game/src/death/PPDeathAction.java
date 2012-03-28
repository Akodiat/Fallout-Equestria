package death;

import components.RenderingComp;
import components.TransformationComp;
import content.ContentManager;

import entityFramework.IEntity;
import entityFramework.IEntityManager;
import graphics.Color;
import graphics.Texture2D;

public class PPDeathAction implements IDeathAction {
	@Override
	public void excecute(IEntity deadEntity, IEntityManager entityManager) {
		TransformationComp transformationComp = deadEntity.getComponent(TransformationComp.class).clone();
		Texture2D texture = ContentManager.loadTexture("HEJHEJDEAD.png");
		RenderingComp renderingComp = new RenderingComp(texture, Color.White, null, null, false);
		IEntity entity = entityManager.createEmptyEntity();
		entity.addComponent(transformationComp);
		entity.addComponent(renderingComp);
		entity.refresh();
	}

}
