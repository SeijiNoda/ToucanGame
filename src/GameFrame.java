import javax.swing.JFrame;

public class GameFrame extends JFrame {
	// Generated serial version ID
	private static final long serialVersionUID = 3868855034012153296L;

	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Toucan");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
