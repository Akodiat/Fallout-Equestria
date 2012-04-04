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

public class ColorPanel extends JPanel {
	private JTextField rField;
	private JTextField gField;
	private JTextField bField;
	private JTextField aField;

	private float r;
	private float g;
	private float b;
	private float a;

	private IComponent component;
	private Field field;

	/**
	 * Create the panel.
	 */
	public ColorPanel(Field field, IComponent component) {
		setLayout(null);

		r = 0; g = 0; b = 0; a = 0;

		JLabel lblColor = new JLabel(field.getName());
		lblColor.setFont(new Font("Arial Black", Font.BOLD, 16));
		lblColor.setBounds(10, 4, 63, 23);
		add(lblColor);

		JLabel lblR = new JLabel("R:");
		lblR.setBounds(83, 11, 16, 14);
		add(lblR);

		rField = new JTextField();
		rField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
					rField.setText("" + newValue);
					r = newValue;
					setCompValue();
				}catch(Exception ex){
					rField.setText("" + r);
				}
			}
		});
		rField.setBounds(93, 8, 47, 20);
		add(rField);
		rField.setColumns(10);

		JLabel lblG = new JLabel("G:");
		lblG.setBounds(150, 11, 16, 14);
		add(lblG);

		gField = new JTextField();
		gField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
					gField.setText("" + newValue);
					g = newValue;
					setCompValue();
				}catch(Exception ex){
					gField.setText("" + g);
				}
			}
		});
		gField.setBounds(160, 8, 47, 20);
		add(gField);
		gField.setColumns(10);

		JLabel lblB = new JLabel("B:");
		lblB.setBounds(217, 11, 10, 14);
		add(lblB);

		bField = new JTextField();
		bField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
					bField.setText("" + newValue);
					b = newValue;
					setCompValue();
				}catch(Exception ex){
					bField.setText("" + b);
				}
			}
		});
		bField.setBounds(227, 8, 47, 20);
		add(bField);
		bField.setColumns(10);

		JLabel lblA = new JLabel("A:");
		lblA.setBounds(284, 11, 16, 14);
		add(lblA);

		aField = new JTextField();
		aField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					float newValue = Float.parseFloat(((JTextField)(e.getSource())).getText());
					aField.setText("" + newValue);
					a = newValue;
					setCompValue();
				}catch(Exception ex){
					aField.setText("" + a);
				}
			}
		});
		aField.setBounds(294, 8, 47, 20);
		add(aField);
		aField.setColumns(10);

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
