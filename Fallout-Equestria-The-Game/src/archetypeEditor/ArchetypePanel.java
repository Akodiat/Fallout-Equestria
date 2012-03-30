package archetypeEditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ArchetypePanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ArchetypePanel() {
		setLayout(null);
		
		JLabel lblname = new JLabel("*Name*");
		lblname.setBounds(10, 11, 67, 26);
		add(lblname);
		
		textField = new JTextField();
		textField.setBounds(76, 48, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblEntityId = new JLabel("Entity ID");
		lblEntityId.setBounds(20, 51, 46, 14);
		add(lblEntityId);
		
		JLabel lblNewLabel = new JLabel("Groups");
		lblNewLabel.setBounds(20, 104, 46, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select group to add");
		lblNewLabel_1.setBounds(188, 122, 94, 14);
		add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 120, 158, 116);
		add(scrollPane);
		
		JList list_2 = new JList();
		scrollPane.setViewportView(list_2);
		list_2.setModel(new AbstractListModel() {
			String[] values = new String[] {"oij", "k\u00E5ok\u00E5k", "", "k\u00E5", "\u00E5k", "\u00E5k", "\u00E5k", "\u00E5k\u00E5", "k\u00E5", "k\u00E5", "k\u00E5", "k\u00E5k\u00E5ok\u00E5ok", "", "k", "\u00E5k\u00E5", "k", "\u00E5k\u00E5", "k", "\u00E5ok'"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(292, 119, 118, 20);
		add(comboBox);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(198, 150, 89, 23);
		add(btnRemove);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(292, 150, 89, 23);
		add(btnAdd);

	}
}
