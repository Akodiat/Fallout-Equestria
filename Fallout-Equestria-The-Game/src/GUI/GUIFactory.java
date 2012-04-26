package GUI;

import misc.EventArgs;
import misc.IEventListener;
import components.BehaviourComp;
import components.GUIComp;

import scripting.ButtonBehavior;
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

	public IEntity createButton(Rectangle bounds, IEntityArchetype buttonArchetype,
			String text, IEventListener<ButtonEventArgs> buttonClickedListener) {
		
		IEntity entity = manager.createEntity(buttonArchetype);
		GUIComp comp = entity.getComponent(GUIComp.class);
		
		comp.setPosition(bounds);
		comp.setText(text);
		
		ButtonBehavior behaviour = (ButtonBehavior)entity.getComponent(BehaviourComp.class).getBehavior();
		behaviour.addButtonClicked(buttonClickedListener);
				
		return entity;
	}
	
	public IEntity createLabel(Rectangle bounds ,IEntityArchetype labelArchetype, String text) {
		IEntity entity = manager.createEntity(labelArchetype);
		GUIComp comp = entity.getComponent(GUIComp.class);
		
		comp.setPosition(bounds);
		comp.setText(text);
				
		return entity;
	}
}
