package death;

import graphics.Color;
import graphics.Texture2D;

import components.RenderingComp;
import components.TransformationComp;

import content.ContentManager;
import entityFramework.IEntity;
import entityFramework.IEntityManager;

public class CorpseDeathAction implements IDeathAction {
	
	private final Texture2D corpseTexture;
	
	public CorpseDeathAction(Texture2D corpseTexture) {
		this.corpseTexture = corpseTexture;
	}
	
	@Override
	public void excecute(IEntity deadEntity, IEntityManager entityManager) {
		TransformationComp transformationComp = deadEntity.getComponent(TransformationComp.class).clone();
		RenderingComp renderingComp = new RenderingComp(corpseTexture, Color.White, null, null, false);
		
		IEntity entity = entityManager.createEmptyEntity();
		entity.addComponent(transformationComp);
		entity.addComponent(renderingComp);
		entity.refresh();
	}

}
