package archetypeEditor;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.List;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class EnumListPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("rawtypes")
	public EnumListPanel() {
		setLayout(null);
		
		JLabel lblList = new JLabel("List:");
		lblList.setBounds(10, 11, 28, 14);
		add(lblList);
		
		JLabel lblThisCanBe = new JLabel("This Can be any name!");
		lblThisCanBe.setBounds(40, 11, 110, 14);
		add(lblThisCanBe);
		
		JLabel lblType = new JLabel("enumType");
		lblType.setBounds(233, 11, 71, 14);
		add(lblType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(298, 8, 123, 20);
		add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 31, 140, 79);
		add(scrollPane);
		
		List list = new List();
		scrollPane.setViewportView(list);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(233, 86, 89, 23);
		add(btnRemove);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(332, 86, 89, 23);
		add(btnNewButton);

	}
}
