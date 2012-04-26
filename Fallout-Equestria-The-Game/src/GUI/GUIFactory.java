package GUI;

import misc.EventArgs;
import misc.IEventListener;
import components.BehaviourComp;
import components.GUIComp;

import scripting.GUIBehaviour;
import utils.Rectangle;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;

public class GUIFactory {
	
	private IEntityManager manager;
	
	public GUIFactory(IEntityManager manager) {
		this.manager = manager;
	}
	
	public IEntity createGUI(Rectangle position, IEntityArchetype guiArchtype) {
		
		IEntity entity = manager.createEntity(guiArchtype);
		GUIComp comp = entity.getComponent(GUIComp.class);
		
		comp.setPosition(position);
	
		return entity;
	}
	
	public IEntity createGUI(Rectangle position, IEntityArchetype guiArchtype, String text) {
		IEntity entity = manager.createEntity(guiArchtype);
		GUIComp comp = entity.getComponent(GUIComp.class);
		
		comp.setPosition(position);
		comp.setText(text);
		
		return entity;
	}
	
	
	public IEntity createGUI(Rectangle position, IEntityArchetype guiArchtype, String text, IEventListener<MouseEventArgs> mouseClicked) {
		IEntity entity = manager.createEntity(guiArchtype);
		GUIComp comp = entity.getComponent(GUIComp.class);
		
		comp.setPosition(position);
		comp.setText(text);
		
		GUIBehaviour behaviour = (GUIBehaviour)entity.getComponent(BehaviourComp.class).getBehavior();
		behaviour.addMouseClicked(mouseClicked);
		
		return entity;
	}
	
	public IEntity createGUIWithClick(Rectangle position, IEntityArchetype guiArchtype, IEventListener<MouseEventArgs> clickListener) {
		IEntity entity = this.createGUI(position, guiArchtype);
		GUIBehaviour behaviour = (GUIBehaviour)entity.getComponent(BehaviourComp.class).getBehavior();
		behaviour.addMouseClicked(clickListener);
		
		return entity;	
	}
	
}
