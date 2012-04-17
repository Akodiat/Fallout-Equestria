package archetypeEditor;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.awt.Font;

public class ColorPanel extends JPanel {

	private Object containingObject;
	private Field field;
	private JLabel colorLabel;
	
	
	/**
	 * Create the panel.
	 */
	public ColorPanel(Field field,Object containingObject) {
		setLayout(null);

		this.containingObject = containingObject;
		this.field = field;
		
		JLabel label = new JLabel(field.getName());
		label.setBounds(10, 11, 106, 14);
		add(label);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setBounds(10, 36, 59, 14);
		add(lblColor);
		
		colorLabel = new JLabel("Sample!");
		colorLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		colorLabel.setForeground(Color.WHITE);
		colorLabel.setBounds(100, 11, 188, 42);
		colorLabel.setBackground(Color.WHITE);
		add(colorLabel);
		
		JButton btnSelectColor = new JButton("Select Color");
		btnSelectColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pickNewColor();
			}
		});
		btnSelectColor.setBounds(295, 27, 106, 23);
		add(btnSelectColor);
	}


	protected void pickNewColor() {
		Color color = JColorChooser.showDialog(this, "Select a color", this.colorLabel.getBackground());
		this.colorLabel.setForeground(color);
		this.colorLabel.setBackground(color);
		this.colorLabel.repaint();
		this.setTheColorValue();
		
	}

	private void setTheColorValue() {
		try {
			Color awtColor = this.colorLabel.getBackground();
			graphics.Color color = new graphics.Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), 255);
			
			this.field.set(containingObject, color);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
