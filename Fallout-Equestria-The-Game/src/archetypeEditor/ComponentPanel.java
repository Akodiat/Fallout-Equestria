package archetypeEditor;

import javax.swing.JPanel;

import entityFramework.IComponent;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import math.Vector2;

import com.thoughtworks.xstream.core.util.Fields;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ComponentPanel extends JPanel {
	private IComponent activeComponent;
	private JPanel scrollPanel;
	private JLabel componentName;
	private int height;
	
	
	/**
	 * Create the panel.
	 */
	public ComponentPanel() {
		setBackground(UIManager.getColor("Panel.background"));
		setLayout(null);
		
		componentName = new JLabel("NO ACTIVE COMPONENT");
		componentName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		componentName.setBounds(10, 11, 543, 19);
		add(componentName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 776, 446);
		add(scrollPane);
		
		scrollPanel = new JPanel();
		scrollPane.setViewportView(scrollPanel);
		scrollPanel.setLayout(null);
		this.forceInvalidate(this);
	}
	
	public void setComponent(IComponent component) {
		if(component == null) {
			this.clear();
		}
		
		try {
			this.setComponentName(component);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.generatePanels();
	}

	private void generatePanels() {
		// TODO Auto-generated method stub
		
	}

	private void setComponentName(IComponent component) throws Exception {
		this.componentName.setText(component.getClass().getName());
		Field[] fields = component.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			if(!Modifier.isFinal(field.getModifiers())) {		
				createFieldPanel(component, field);
			}
		}

		this.forceInvalidate(this.scrollPanel);
		
	}

	private void createSpearator() {
		JSeparator separator = new JSeparator();
		height += 5;
		separator.setBounds(0,height, 500, 1);
		this.scrollPanel.add(separator);
		
		height += 5;
	}

	private void createFieldPanel(IComponent component, Field field) {
		System.out.println(field.getName());	
		Class type = field.getType();
		
		if(type.isPrimitive() || type.equals(String.class)){
			createPrimitivePanel(component, field);
		} else if(type.equals(Vector2.class)) {
			Vector2Panel vPanel = new Vector2Panel(field, component);
			vPanel.setBounds(0, height, 500, 55);
			height += 50;
			this.scrollPanel.add(vPanel);
		}

		createSpearator();
	}

	private void createPrimitivePanel(IComponent component, Field field) {
		PrimitiveTypePanel primitivePanel = new PrimitiveTypePanel(field, component);
		primitivePanel.setBounds(0, height, 500, 45);
		height += 50;
		
		
		this.scrollPanel.add(primitivePanel);
	}

	private void clear() {
		this.scrollPanel.removeAll();
		this.forceInvalidate(this.scrollPanel);
	}
	
	//Swing sucks.
	private void forceInvalidate(Component component) {
		this.scrollPanel.invalidate();
		this.scrollPanel.revalidate();
		this.scrollPanel.validate();
		this.scrollPanel.repaint();
	}

}
