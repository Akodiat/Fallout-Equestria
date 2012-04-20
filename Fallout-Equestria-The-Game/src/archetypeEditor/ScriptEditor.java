package archetypeEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import scripting.PlayerScript;
import scripting.BehaviourScript;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.thoughtworks.xstream.core.util.Fields;

import entityFramework.IComponent;

import anotations.Editable;
import anotations.Editable;

import java.awt.Font;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JScrollPane;

public class ScriptEditor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel componentsListModel;
	private HashMap<String, Class<BehaviourScript>> scriptMaps = new HashMap<>();
	
	private BehaviourScript script;
	
	public BehaviourScript getScript() {
		return this.script;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ScriptEditor dialog = new ScriptEditor(new PlayerScript());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ScriptEditor(Object script) throws ClassNotFoundException, IOException {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 229);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblScript = new JLabel("Script");
			lblScript.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblScript.setBounds(10, 11, 46, 14);
			contentPanel.add(lblScript);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 31, 414, 198);
			contentPanel.add(scrollPane);
			JPanel fieldPanel = new JPanel();
			scrollPane.setViewportView(fieldPanel);
			fieldPanel.setLayout(null);
			
			

			Field[] fields = script.getClass().getDeclaredFields();
			int height = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation annotation = field.getAnnotation(Editable.class);
				if(annotation != null) {
					PrimitiveTypePanel pPanel = new PrimitiveTypePanel(field, script);
					pPanel.setBounds(0, height, 500, 55);
					fieldPanel.add(pPanel);
					height += 50;
				}
			}
			this.invalidate();
			this.revalidate();
			this.repaint();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 229, 434, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						close(true);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}

	protected void close(boolean b) {
		this.setVisible(false);
	}

}
