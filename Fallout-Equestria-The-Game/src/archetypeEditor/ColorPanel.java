package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.lang.reflect.Field;

import javax.swing.JTextField;
import entityFramework.IComponent;
import graphics.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ColorPanel extends JPanel {
	private JTextField rField;
	private JTextField gField;
	private JTextField bField;
	private JTextField aField;

	private Float r;
	private Float g;
	private Float b;
	private Float a;

	private Object component;
	private Field field;

	/**
	 * Create the panel.
	 */
	public ColorPanel(Field field, Object component) {
		setLayout(null);

		r = 0f; g = 0f; b = 0f; a = 0f;

		JLabel lblColor = new JLabel(field.getName());
		lblColor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblColor.setBounds(10, 4, 106, 23);
		add(lblColor);

		JLabel lblR = new JLabel("R:");
		lblR.setBounds(126, 10, 16, 14);
		add(lblR);

		rField = new JTextField();
		rField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(r, (JTextField)e.getSource());
			}
		});
		rField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChanged(r, (JTextField)e.getSource());
			}
		});
		rField.setBounds(136, 7, 47, 20);
		add(rField);
		rField.setColumns(10);

		JLabel lblG = new JLabel("G:");
		lblG.setBounds(193, 10, 16, 14);
		add(lblG);

		gField = new JTextField();
		gField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(g, (JTextField)e.getSource());
			}
		});
		gField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChanged(g, (JTextField)e.getSource());
			}
		});
		gField.setBounds(203, 7, 47, 20);
		add(gField);
		gField.setColumns(10);

		JLabel lblB = new JLabel("B:");
		lblB.setBounds(260, 10, 10, 14);
		add(lblB);

		bField = new JTextField();
		bField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(b, (JTextField)e.getSource());
			}
		});
		bField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChanged(b, (JTextField)e.getSource());
			}
		});
		bField.setBounds(270, 7, 47, 20);
		add(bField);
		bField.setColumns(10);

		JLabel lblA = new JLabel("A:");
		lblA.setBounds(327, 10, 16, 14);
		add(lblA);

		aField = new JTextField();
		aField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textChanged(a, (JTextField)e.getSource());
			}
		});
		aField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChanged(a, (JTextField)e.getSource());
			}
		});
		aField.setBounds(337, 7, 47, 20);
		add(aField);
		aField.setColumns(10);

	}
	
	private void textChanged(Float toChange, JTextField textField){
		try{
			float newValue = Float.parseFloat(textField.getText());
			textField.setText("" + newValue);
			toChange = newValue;
			setCompValue();
		}catch(Exception ex){
			textField.setText("" + toChange);
		}
	}

	private void setCompValue(){
		Object newValue = new Color(r, g, b, a);
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
