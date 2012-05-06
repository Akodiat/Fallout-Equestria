package archetypeEditor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import content.ContentManager;
import content.EntityArchetypeLoader;
import entityFramework.EntityArchetype;
import entityFramework.IComponent;
import entityFramework.IEntityArchetype;
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
import org.lwjgl.opengl.Display;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArchetypeEditor {

	private JFrame frame;
	private ArchetypePanel archPanel;
	private ComponentPanel compPanel;
	private ContentManager manager;
	private EntityArchetypeLoader loader;
	
	
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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ArchetypeEditor() throws ClassNotFoundException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, IOException {
		

		manager = new ContentManager("resources");
		loader = new EntityArchetypeLoader(manager, "archetypes");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1104, 579);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		this.compPanel = new ComponentPanel(manager);
	
		splitPane.setRightComponent(this.compPanel);
		
		this.archPanel = new ArchetypePanel(this, manager);
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
						FileInputStream in = new FileInputStream(chooser.getSelectedFile());
				    	archPanel.setArchetype(loader.loadContent(in));
				    	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						Display.destroy();
					}
			    }
			}
		});
		mntmOpen.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setIcon(new ImageIcon(ArchetypeEditor.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(saveMenuItem);
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
			    int returnVal = chooser.showOpenDialog(mntmNewMenuItem);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	if(chooser.getSelectedFile() != null) {
			    		File file = chooser.getSelectedFile();
			    		try {
							FileOutputStream ostream = new FileOutputStream(file);
							IEntityArchetype arch = archPanel.getArchetype();
							loader.save(ostream, arch);
							ostream.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    	}
			    }
			}
		});
		
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
