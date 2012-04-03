package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.JTextField;

import math.Vector2;

import utils.Circle;

import entityFramework.IComponent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CirclePanel extends JPanel {
	private JTextField radiusField;
	private JTextField xField;
	private JTextField yField;
	private float x;
	private float y;
	private float radius;
	private IComponent component;
	private Field field;

	/**
	 * Create the panel.
	 */
	public CirclePanel(IComponent component, Field field) {
		setLayout(null);
		
		JLabel lblCircle = new JLabel("Circle:");
		lblCircle.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblCircle.setBounds(10, 4, 63, 23);
		add(lblCircle);
		
		JLabel lblRadius = new JLabel("Radius:");
		lblRadius.setBounds(83, 11, 41, 14);
		add(lblRadius);
		
		radiusField = new JTextField();
		radiusField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
				radiusField.setText("" + newValue);
				radius = newValue;
				setCompValue();
			}
		});
		radiusField.setBounds(134, 8, 47, 20);
		add(radiusField);
		radiusField.setColumns(10);
		
		JLabel lblX = new JLabel("X:");
		lblX.setBounds(191, 11, 10, 14);
		add(lblX);
		
		xField = new JTextField();
		xField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
				xField.setText("" + newValue);
				x = newValue;
				setCompValue();
			}
		});
		xField.setBounds(211, 8, 47, 20);
		add(xField);
		xField.setColumns(10);
		
		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(268, 11, 10, 14);
		add(lblY);
		
		yField = new JTextField();
		yField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
				yField.setText("" + newValue);
				y = newValue;
				setCompValue();
			}
		});
		yField.setBounds(288, 8, 47, 20);
		add(yField);
		yField.setColumns(10);

	}
	
	private void setCompValue(){
		Object newValue = new Circle(new Vector2(x, y), radius);
		try {
			this.field.set(component, newValue);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR WHEN SETTING COMPONENT ASSET FIELD (Illegal argument)");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR WHEN SETTING COMPONENT ASSET FIELD (Illegal access)");
		}
	}

}
