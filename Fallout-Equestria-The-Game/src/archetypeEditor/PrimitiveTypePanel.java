package archetypeEditor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entityFramework.IComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class PrimitiveTypePanel extends JPanel {
	private JTextField textField;
	private Parser parser;
	private IComponent component;
	private Field field;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PrimitiveTypePanel(Field fieldType, IComponent component) {
		setLayout(null);
		this.field = fieldType;
		parser = new Parser(fieldType.getType());
		this.component = component;
		
		JLabel lblNewLabel = new JLabel(fieldType.getName());
		lblNewLabel.setBounds(10, 11, 120, 14);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textChanged(((JTextField)arg0.getSource()).getText());
			}
		});
		
		textField.setBounds(141, 26, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setBounds(141, 11, 46, 14);
		add(lblValue);
		
		JLabel lblHej = new JLabel(fieldType.getType().getSimpleName());
		lblHej.setBounds(10, 29, 91, 14);
		add(lblHej);
	}


	protected void textChanged(String text) {
		try {
			Object value = this.parser.parse(text);
			field.set(this.component, value);
		} catch(Exception e) {
			this.textField.setText("Invalid Input");
		}
	}

	
	private class Parser<T> {
		private Class<T> type;
		
		public Parser(Class<T> type) {
			this.type = type;
		}
		
		public Object parse(String s) {	
			if(type.equals(Integer.TYPE)) {
				Integer i = Integer.parseInt(s);
				return i;
			} else if(type.equals(Boolean.TYPE)) {
				Boolean bool = Boolean.parseBoolean(s);
				return bool;	
			} else if(type.equals(Float.TYPE)) {
				Float f = Float.parseFloat(s);
				return f;
			} else if(type.equals(String.class)) {
				return s;
			}
			
			throw new RuntimeException("Something went wrong.");			
		}
	}
}
