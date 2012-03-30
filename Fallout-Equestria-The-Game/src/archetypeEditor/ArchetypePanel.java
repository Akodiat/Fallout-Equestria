package archetypeEditor;

import java.util.List;

import javax.swing.DefaultListModel;
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

import entityFramework.EntityArchetype;
import entityFramework.IComponent;
import javax.swing.DefaultComboBoxModel;
import misc.EntityGroups;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArchetypePanel extends JPanel {
	private JTextField entityIDTextField;
	private JList groupsList;
	private DefaultListModel groupListModel;
	
	@SuppressWarnings("unchecked")
	public void setArchetype(EntityArchetype arch){
		this.entityIDTextField.setText(arch.getLabel());
		
		List<IComponent> componentList = arch.getComponents().asList();
		final String[] compStrings = new String[componentList.size()];
		for (IComponent component : componentList) {
			compStrings[componentList.indexOf(component)] = component.toString();
		}
		groupsList.setModel(this.groupListModel);
	}
	/**
	 * Create the panel.
	 */
	public ArchetypePanel() {
		setLayout(null);
		
		JLabel entityNameLabel = new JLabel("Archetype");
		entityNameLabel.setBounds(10, 11, 67, 26);
		add(entityNameLabel);
		
		entityIDTextField = new JTextField();
		entityIDTextField.setBounds(76, 48, 86, 20);
		add(entityIDTextField);
		entityIDTextField.setColumns(10);
		
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
		
		groupListModel = new DefaultListModel();
		
		groupsList = new JList();
		scrollPane.setViewportView(groupsList);
		groupsList.setModel(this.groupListModel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(EntityGroups.values()));
		comboBox.setBounds(292, 119, 118, 20);
		add(comboBox);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(222, 150, 89, 23);
		add(btnRemove);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println();
				groupListModel.addElement(comboBox.getSelectedItem());
				groupsList.revalidate();
			}
		});
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
