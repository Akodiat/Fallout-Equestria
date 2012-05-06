package archetypeEditor;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import org.newdawn.slick.openal.Audio;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AudioPlayerFrame extends JFrame {

	private JPanel contentPane;
	@SuppressWarnings("unused")
	private Audio sound;
	/**
	 * Launch the application.
	 */
	/*public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AudioPlayerFrame frame = new AudioPlayerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AudioPlayerFrame(final Audio sound) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.sound = sound;
		
		
		
		JButton btnNewButton = new JButton("PLAY AUDIO");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.playAsSoundEffect(1.0f, 0.5f, false);
			}
		});
		contentPane.add(btnNewButton, BorderLayout.CENTER);
	}

}
