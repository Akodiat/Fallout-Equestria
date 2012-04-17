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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CirclePanel extends JPanel {
	private JTextField radiusField;
	private JTextField xField;
	private JTextField yField;
	private mFloat x = new mFloat();
	private mFloat y = new mFloat();;
	private mFloat radius= new mFloat();
	
	private class mFloat {
		public float f = 0;
	}
	
	private Object component;
	private Field field;

	/**
	 * Create the panel.
	 */
	public CirclePanel(Field field, Object component) {
		setLayout(null);

		JLabel lblCircle = new JLabel(field.getName());
		lblCircle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCircle.setBounds(10, 6, 99, 23);
		add(lblCircle);

		JLabel lblRadius = new JLabel("Radius:");
		lblRadius.setBounds(119, 12, 59, 14);
		add(lblRadius);

		radiusField = new JTextField();
		radiusField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				textChanged(radius, (JTextField)arg0.getSource());
			}
		});
		radiusField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChanged(radius, (JTextField)e.getSource());
			}
		});
		radiusField.setBounds(188, 9, 47, 20);
		add(radiusField);
		radiusField.setColumns(10);

		JLabel lblX = new JLabel("X:");
		lblX.setBounds(245, 12, 17, 14);
		add(lblX);

		xField = new JTextField();
		xField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(x, (JTextField)e.getSource());
			}
		});
		xField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					textChanged(x, (JTextField)e.getSource());
			}
		});
		xField.setBounds(272, 9, 47, 20);
		add(xField);
		xField.setColumns(10);

		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(329, 12, 10, 14);
		add(lblY);

		yField = new JTextField();
		yField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(y, (JTextField)e.getSource());
			}
		});
		yField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					textChanged(y, (JTextField)e.getSource());
			}
		});
		yField.setBounds(349, 9, 47, 20);
		add(yField);
		yField.setColumns(10);

	}
	
	private void textChanged(mFloat toChange, JTextField textField){
		try{
			float newValue = Float.parseFloat(textField.getText());
			textField.setText("" + newValue);
			toChange.f = newValue;
			setCompValue();
		}catch(Exception ex){
			textField.setText("" + toChange.f);
		}
	}

	private void setCompValue(){
		Object newValue = new Circle(new Vector2(x.f, y.f), radius.f);
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
