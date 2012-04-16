package archetypeEditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;

import scripting.Script;

import anotations.Editable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class ScriptPanel extends JPanel {

	Field scriptField;
	Object containgingObject;
	HashMap<String, Class<? extends Script>> scriptTypes = new HashMap<>();
	
	int height = 300;
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Create the panel.
	 */
	public ScriptPanel(Field scriptField, Object valueContaingScript) {
		
		this.containgingObject = valueContaingScript;
		this.scriptField = scriptField;
		setLayout(null);
		
		JLabel ScriptType = new JLabel("Script Type");
		ScriptType.setBounds(262, 6, 54, 14);
		add(ScriptType);
		
		JLabel fieldNamelabel = new JLabel(scriptField.getName());
		fieldNamelabel.setBounds(6, 6, 77, 14);
		add(fieldNamelabel);
		
		JLabel scriptLabel = new JLabel("Script");
		scriptLabel.setBounds(6, 26, 77, 14);
		add(scriptLabel);
		
		
		List<String> scriptTypeNames = fillScriptTypes();
		scriptTypeNames.add(0, "");
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(262, 23, 180, 20);
		add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(scriptTypeNames.toArray(new String[scriptTypeNames.size()])));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<String> box = (JComboBox<String>)arg0.getSource();
				changedScript(box.getSelectedItem().toString());
			}
		});
		this.setBackground(Color.LIGHT_GRAY);
		fillFieldPanel();
	}


	protected void changedScript(String string) {
		Class<? extends Script> scriptClass = this.scriptTypes.get(string);
		if(scriptClass == null)
			return;
		
		Script script = (Script) this.tryGetScript();
		if(script == null || !script.equals(scriptClass)) {
			script = createNewScript(scriptClass);
			setScriptValue(script);
			fillFieldPanel();
		}
		
		
		this.invalidate();
		this.revalidate();
		this.validate();
		this.repaint();
	}


	private void setScriptValue(Script script) {
		try {
			this.scriptField.set(this.containgingObject, script);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			
		}
	}


	private Script createNewScript(Class<? extends Script> scriptClass) {
		Script script = ReflectionHelper.createNewInstance(scriptClass);
		return script;
	}


	private List<String> fillScriptTypes(){
		
		List<String> classNames = new ArrayList<>();
		Class[] clazzes;
		
		try {
			clazzes = ReflectionHelper.getClassesThatContainAnnotation(Editable.class, "scripting");
			for (Class clazz : clazzes) {
				classNames.add(clazz.getSimpleName());
				this.scriptTypes.put(clazz.getSimpleName(), clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return classNames;
	}


	private void fillFieldPanel() {
		Object script = tryGetScript();
		if(script != null) {	

			Component[] components = this.getComponents();
			for (int i = 4; i < components.length; i++) {
				this.remove(components[i]);
			}
			
			Field[] fields = script.getClass().getDeclaredFields();
			height = 65;
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annotation = field.getAnnotation(Editable.class);
				if(annotation != null) {
					PrimitiveTypePanel pPanel = new PrimitiveTypePanel(field, script);
					pPanel.setBounds(0, height, 500, 55);
					add(pPanel);
					height += 55;
				}
			}
			
		}
	}


	private Object tryGetScript() {
		try {
			Object obj = this.scriptField.get(this.containgingObject);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error();
		}
	}
}
