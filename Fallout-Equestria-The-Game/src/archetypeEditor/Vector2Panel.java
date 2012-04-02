package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entityFramework.IComponent;

import math.Vector2;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Field;

@SuppressWarnings("serial")
public class Vector2Panel extends JPanel {
	private JTextField xTextField;
	private JTextField yTextField;
	private Vector2 vector2;
	private Field field;
	private IComponent component;

	/**
	 * Create the panel.
	 */
	public Vector2Panel(Field field, IComponent component) {
		setLayout(null);
		this.field = field;
		this.component = component;
		
		JLabel lblNewLabel = new JLabel(field.getName());
		lblNewLabel.setBounds(10, 11, 150, 14);
		add(lblNewLabel);
		
		JLabel lblVector = new JLabel("Vector2");
		lblVector.setBounds(10, 36, 150, 14);
		add(lblVector);
		
		xTextField = new JTextField();
		xTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseX((JTextField)arg0.getSource());
			}
		});
		
		xTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				parseX((JTextField)arg0.getSource());			
			}
		});
		
		xTextField.setBounds(170, 33, 50, 20);
		add(xTextField);
		xTextField.setColumns(10);
		
		JLabel lblXvalue = new JLabel("X-value");
		lblXvalue.setBounds(170, 11, 50, 14);
		add(lblXvalue);
		
		yTextField = new JTextField();
		yTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseY((JTextField)arg0.getSource());			
			}
		});
		
		yTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				parseY((JTextField)arg0.getSource());			
			}
		});
		
		yTextField.setColumns(10);
		yTextField.setBounds(225, 33, 50, 20);
		add(yTextField);
		
		JLabel lblYvalue = new JLabel("Y-value");
		lblYvalue.setBounds(225, 11, 50, 14);
		add(lblYvalue);
		

		this.setInitialValue();

	}
	
	private void setInitialValue() {
		try {
			this.vector2 = (Vector2) field.get(component);
			
			this.xTextField.setText(this.vector2.X + "");
			this.yTextField.setText(this.vector2.Y + "");
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}

	private void parseX(JTextField field) {
		try {
		float x = Float.parseFloat(field.getText());
		this.vector2 = new Vector2(x, this.vector2.Y);
		field.setText(x+"");
		
		this.setComponentValue();
		} catch(Exception e) {
			field.setText(this.vector2.X + "");
		}
	}

	private void parseY(JTextField field) {
		try {
			float y = Float.parseFloat(field.getText());
			this.vector2 = new Vector2(this.vector2.X, y);
			field.setText(y+"");
			this.setComponentValue();
		} catch(Exception e) {
			field.setText(this.vector2.Y + "");
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
