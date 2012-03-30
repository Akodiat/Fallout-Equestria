package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrimitiveTypePanel extends JPanel {
	private JTextField textField;


	public Object Value;
	private Parser parser;
	
	/**
	 * Create the panel.
	 */
	public PrimitiveTypePanel(Class fieldType, String fieldName) {
		setLayout(null);
		parser = new Parser(fieldType);
		
		JLabel lblNewLabel = new JLabel(fieldName);
		lblNewLabel.setBounds(10, 11, 91, 14);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textChanged(((JTextField)arg0.getSource()).getText());
			}

		});
	
		
		textField.setBounds(112, 26, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setBounds(112, 11, 46, 14);
		add(lblValue);
		
		JLabel lblHej = new JLabel(fieldType.getSimpleName());
		lblHej.setBounds(10, 29, 91, 14);
		add(lblHej);
	}


	protected void textChanged(String text) {
		try {
		this.Value = parser.parse(text);
		} catch(Exception e) {
			this.textField.setText("Invalid Input");
			throw e;
		}
		
		System.out.println(Value);
	}
	
	public Object getValue() {
		return this.Value;
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
