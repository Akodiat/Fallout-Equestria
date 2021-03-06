package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class StringListPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public StringListPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("List:");
		lblNewLabel.setBounds(10, 11, 25, 14);
		add(lblNewLabel);
		
		JLabel lblNameofvariable = new JLabel("nameOfVariable");
		lblNameofvariable.setBounds(45, 11, 104, 14);
		add(lblNameofvariable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 139, 100);
		add(scrollPane);
		
		JList list = new JList();
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		scrollPane.setViewportView(list);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(214, 67, 89, 23);
		add(btnRemove);
		
		JButton btnAd = new JButton("Add");
		btnAd.setBounds(323, 67, 89, 23);
		add(btnAd);
		
		JLabel label = new JLabel("nameOfVariable");
		label.setBounds(214, 11, 104, 14);
		add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(323, 8, 117, 20);
		add(comboBox);

	}
}
