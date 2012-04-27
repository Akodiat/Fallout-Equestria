package GUI;

import misc.EventArgs;
import misc.IEventListener;
import components.BehaviourComp;
import components.GUIComp;
import content.ContentManager;

import scripting.ButtonBehavior;
import scripting.GUIBehaviour;
import utils.Rectangle;
import entityFramework.IEntity;
import entityFramework.IEntityArchetype;
import entityFramework.IEntityManager;


public abstract class IGUIFactory {
	protected GUIRenderingContext context;
	
	public abstract void initialize(ContentManager manager);
	
	public abstract Button createButton(Rectangle bounds, String text);
	public abstract Label createLabel(Rectangle bound, String text);
}