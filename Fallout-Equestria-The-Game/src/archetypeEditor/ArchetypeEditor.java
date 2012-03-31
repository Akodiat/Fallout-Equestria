package archetypeEditor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import components.TransformationComp;
import content.ContentManager;
import entityFramework.EntityArchetype;
import entityFramework.IComponent;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ArchetypeEditor {

	private JFrame frame;
	private ArchetypePanel archPanel;
	private ComponentPanel compPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArchetypeEditor window = new ArchetypeEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ArchetypeEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1104, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		this.compPanel = new ComponentPanel();
		splitPane.setRightComponent(this.compPanel);
		
		this.archPanel = new ArchetypePanel(this);
		splitPane.setLeftComponent(this.archPanel);
		
		splitPane.setDividerLocation(420);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		final JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<IComponent> list = new ArrayList<IComponent>();
				archPanel.setArchetype(new EntityArchetype(list));
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		mntmNewMenuItem.setSelectedIcon(new ImageIcon(ArchetypeEditor.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Fallout: Equestria entity archetypes", "archetype");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(mntmNewMenuItem);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						Display.create();
					} catch (LWJGLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	archPanel.setArchetype(ContentManager.loadArchetype(chooser.getSelectedFile().getName()));
			    	Display.destroy();
			    }
			}
		});
		mntmOpen.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
		mntmNewMenuItem_1.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Show documentation");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "There is one thing you need to understand.\nSwing and java isn't very nice to developers. \nTherefore, since things at the very least should be somewhat fair, we wont be nice to you. \n\ntl;dr: No help for you! Hah!");
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnNewMenu.add(mntmNewMenuItem_2);
	}
	public void openComponent(IComponent component){
		this.compPanel.setComponent(component);
		this.compPanel.revalidate();
	}
}
