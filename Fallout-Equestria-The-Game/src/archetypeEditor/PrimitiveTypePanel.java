package archetypeEditor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entityFramework.IComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Field;

public class PrimitiveTypePanel extends JPanel {
	private JTextField textField;
	private Parser parser;
	private IComponent component;
	private Field field;
	
	private String textValue;
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
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				textChanged(((JTextField)arg0.getSource()).getText());			
			}
		});
		
		textField.setBounds(171, 26, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setBounds(171, 11, 46, 14);
		add(lblValue);
		
		JLabel lblHej = new JLabel(fieldType.getType().getSimpleName());
		lblHej.setBounds(10, 29, 91, 14);
		add(lblHej);
		this.setup();
	}


	private void setup() {
		try {
			Object value = field.get(this.component);
			this.textField.setText(value.toString());
			this.textValue = value.toString();
					
		} catch(Exception e) {
			throw new RuntimeException();
		}
	}


	protected void textChanged(String text) {
		try {
			Object value = this.parser.parse(text);
			field.set(this.component, value);
			this.textValue = value.toString();
			this.textField.setText(this.textValue);
		} catch(Exception e) {
			this.textField.setText(textValue);
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
