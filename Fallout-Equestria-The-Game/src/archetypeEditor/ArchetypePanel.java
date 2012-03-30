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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
		btnRemove.setBounds(222, 150, 89, 23);
		add(btnRemove);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(321, 150, 89, 23);
		add(btnAdd);
		
		JLabel lblComponents = new JLabel("Components");
		lblComponents.setBounds(20, 312, 77, 14);
		add(lblComponents);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 247, 430, 26);
		add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 328, 390, 238);
		add(scrollPane_1);
		
		JList list = new JList();
		scrollPane_1.setViewportView(list);
		
		JButton button = new JButton("Remove");
		button.setBounds(222, 296, 89, 23);
		add(button);
		
		JButton button_1 = new JButton("Add");
		button_1.setBounds(321, 296, 89, 23);
		add(button_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(292, 265, 118, 20);
		add(comboBox_1);
		
		JLabel lblSelectComponentTo = new JLabel("Select component to add");
		lblSelectComponentTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectComponentTo.setBounds(152, 268, 130, 14);
		add(lblSelectComponentTo);

	}
}
