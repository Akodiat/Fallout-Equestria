package archetypeEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import entityFramework.IEntityArchetype;

import javax.swing.DefaultComboBoxModel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;


import misc.EntityGroups;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import ability.Abilities;
import anotations.Editable;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ArchetypePanel extends JPanel {
	private JTextField entityIDTextField;
	private JList groupList;
	private JList componentList;
	private DefaultListModel groupListModel;
	private DefaultListModel componentsListModel;
	private HashMap<String, IComponent> compMap;
	
	private ArchetypeEditor archEd;
	
	private Map<String, IComponent> componentMap = new HashMap<>();
	private Map<String, Class> componentClassMap = new HashMap<>();
	

	@SuppressWarnings("unchecked")
	public void setArchetype(IEntityArchetype entityArch){
		this.entityIDTextField.setText(entityArch.getLabel());

		componentsListModel.clear();
		componentMap.clear();
		compMap.clear();
		List<IComponent> componentList = entityArch.getComponents().asList();
		for (IComponent component : componentList) {			
			String name = component.getClass().getSimpleName();
			componentsListModel.addElement(name);
			componentMap.put(name, component);
			compMap.put(component.getClass().getSimpleName(), component);
		}
		
		ImmutableList<String> groupList = entityArch.getGroups().asList();
		for (String string : groupList) {
			groupListModel.addElement(string);
			System.out.println(string);
		}
 
	} 
	
	public IEntityArchetype getArchetype() {
		ImmutableSet<IComponent> compSet = ImmutableSet.copyOf(this.componentMap.values());
		
		List<String> groupNames = new ArrayList<>();
		for (int i = 0; i < this.groupListModel.size(); i++) {
			groupNames.add(this.groupListModel.get(i).toString());
		}
		ImmutableSet<String> groups = ImmutableSet.copyOf(groupNames);
		
		String entityLabel = this.entityIDTextField.getText();
		
		return new EntityArchetype(compSet, groups, entityLabel);
	}
	
	/**
	 * Create the panel.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ArchetypePanel(final ArchetypeEditor archEd) throws ClassNotFoundException, IOException {
		setLayout(null);
		
		this.archEd = archEd;
		
		this.compMap = new HashMap<String, IComponent>();

		JScrollPane scrollPane = unimportantGUI();

		this.groupListModel = new DefaultListModel();
		this.componentsListModel = new DefaultListModel();

		groupList = new JList();
		scrollPane.setViewportView(groupList);
		groupList.setModel(this.groupListModel);

		final JComboBox groupComboBox = new JComboBox();
		groupComboBox.setModel(new DefaultComboBoxModel(EntityGroups.values()));
		groupComboBox.setBounds(292, 119, 118, 20);
		add(groupComboBox);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				groupListModel.removeElement(groupList.getSelectedValue());
			}
		});
		btnRemove.setBounds(222, 150, 89, 23);
		add(btnRemove);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!groupListModel.contains(groupComboBox.getSelectedItem())){
					groupListModel.addElement(groupComboBox.getSelectedItem());
					groupList.revalidate();
				}
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

		componentList = new JList();
		componentList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(componentList.getSelectedValue() != null) {
					IComponent component = componentMap.get(componentList.getSelectedValue());
					archEd.openComponent(component);
				}
			}
		});
		scrollPane_1.setViewportView(componentList);
		componentList.setModel(this.componentsListModel);

		JButton button = new JButton("Remove");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object selected = componentList.getSelectedValue();
				componentsListModel.removeElement(selected);
				removeComponent(selected.toString());
			}
		});
		button.setBounds(222, 296, 89, 23);
		add(button);

		
		List<String> classNames = new ArrayList<>();
		Class[] clazzes = ReflectionHelper.getClassesThatContainAnnotation(Editable.class, "components");
		for (Class clazz : clazzes) {
			classNames.add(clazz.getSimpleName());
			this.componentClassMap.put(clazz.getSimpleName(), clazz);
		}

		final JComboBox componentComboBox = new JComboBox();
		componentComboBox.setModel(new DefaultComboBoxModel(classNames.toArray(new String[classNames.size()])));
		componentComboBox.setBounds(292, 265, 118, 20);
		add(componentComboBox);

		JLabel lblSelectComponentTo = new JLabel("Select component to add");
		lblSelectComponentTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectComponentTo.setBounds(152, 268, 130, 14);
		add(lblSelectComponentTo);
		
		JButton button_1 = new JButton("Add");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!componentsListModel.contains(componentComboBox.getSelectedItem())){
					createComponent(componentComboBox.getSelectedItem().toString());
					componentsListModel.addElement(componentComboBox.getSelectedItem());
					componentList.revalidate();
				}
			}
		});
		button_1.setBounds(321, 296, 89, 23);
		add(button_1);
	}
	
	protected void removeComponent(String string) {
		this.componentMap.remove(string);		
	}

	protected void createComponent(String componentName) {
		Class componentClass = this.componentClassMap.get(componentName);
		IComponent component = ReflectionHelper.createNewInstance(componentClass);
		this.componentMap.put(componentName, component);
	}

	private JScrollPane unimportantGUI() {
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
		return scrollPane;
	}
}
