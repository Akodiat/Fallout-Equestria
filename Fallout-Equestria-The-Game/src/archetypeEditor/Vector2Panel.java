package archetypeEditor;

import java.awt.font.NumericShaper;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entityFramework.IComponent;

import math.Vector2;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class Vector2Panel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private Vector2 vector2;
	private Field field;
	private IComponent component;

	/**
	 * Create the panel.
	 */
	public Vector2Panel(Field field, IComponent component) {
		setLayout(null);
		this.setInitialValue();
		this.field = field;
		this.component = component;
		
		JLabel lblNewLabel = new JLabel(field.getName());
		lblNewLabel.setBounds(10, 11, 150, 14);
		add(lblNewLabel);
		
		JLabel lblVector = new JLabel("Vector2");
		lblVector.setBounds(10, 36, 150, 14);
		add(lblVector);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseX((JTextField)arg0.getSource());
			}
		});
		textField.setBounds(170, 33, 50, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblXvalue = new JLabel("X-value");
		lblXvalue.setBounds(170, 11, 50, 14);
		add(lblXvalue);
		
		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseY((JTextField)arg0.getSource());			
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(225, 33, 50, 20);
		add(textField_1);
		
		JLabel lblYvalue = new JLabel("Y-value");
		lblYvalue.setBounds(225, 11, 50, 14);
		add(lblYvalue);

	}
	
	private void setInitialValue() {
		try {
			Object vector = field.get(component);
			
			this.vector2 = (Vector2) field.get(component);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}

	private void parseX(JTextField field) {
		try {
		float x = Float.parseFloat(field.getText());

		this.vector2 = new Vector2(x, this.vector2.Y);
		this.setComponentValue();
		} catch(Exception e) {
			field.setText(this.vector2.X + "");
		}
	}

	private void parseY(JTextField field) {
		try {
			float x = Float.parseFloat(field.getText());
			this.vector2 = new Vector2(x, this.vector2.Y);
			this.setComponentValue();
		} catch(Exception e) {
			field.setText(this.vector2.X + "");
		}
	}
	
	private void setComponentValue() {
		try {
			this.field.set(this.component, this.vector2);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
