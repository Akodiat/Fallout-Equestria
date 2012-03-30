package archetypeEditor;

import javax.swing.JPanel;

import entityFramework.IComponent;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;

public class ComponentPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ComponentPanel(IComponent component) {
		setBackground(UIManager.getColor("Panel.background"));
		setLayout(null);
		
		JLabel componentNameLabel = new JLabel(component.getClass().getSimpleName());
		componentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		componentNameLabel.setBounds(10, 11, 543, 19);
		add(componentNameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 776, 446);
		add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

	}

}
