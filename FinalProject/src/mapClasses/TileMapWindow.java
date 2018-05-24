package mapClasses;


import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TileMapWindow extends JFrame
{
	static final int WIDTH = 700;
	static final int HEIGHT = 615;
	
	static final String GIT_HUB_REPO = "[none]";
	static final String author = "Caden";
	
	static final String title = "Game Window";
	static final String instructions = "Click OK to start game.\n"
			+ "Source code for this project is available at " + GIT_HUB_REPO 
			+ "\nCreated by " + author;
	
	public MapPanel mapPanel;
	public GameOverPanel gameOverPanel;
	
	public void Restart()
	{
		this.dispose();
		Start();
	}
	
	public TileMapWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		//setResizable(false);
		setTitle(title);
		
		mapPanel = new MapPanel();
		gameOverPanel = new GameOverPanel(this);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(mapPanel);
		contentPane.add(gameOverPanel);
		
		add(contentPane);
	}
	
	public static void main(String[] args)
	{
		Start();
	}
	
	public static void Start()
	{
		//for thread safety
		EventQueue.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				if(JOptionPane.showConfirmDialog(null, instructions, title, 
						JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
				{
					return;
				}

				JFrame app = new TileMapWindow();
				app.setVisible(true);
			}
		});
	}
}
