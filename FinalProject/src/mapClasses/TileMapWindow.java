package mapClasses;


import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TileMapWindow extends JFrame
{
	static final int WIDTH = 700;
	static final int HEIGHT = 615;
	
	static final String GIT_HUB_REPO = "https://github.com/cjgunnar/AP-CS-FINAL-PROJECT";
	static final String author = "Caden";
	
	static final String title = "Game Window";
	static final String instructions = "<html><body>Click OK to start game.<br>"
			+ "Source code hosted on GitHub<br>"
			+ "Use WASD to move<br>"
			+ "Created by " + author + "</body></html>";
	
	private MapPanel mapPanel;
	private EventPanel eventPanel;
	private GameOverPanel gameOverPanel;
	
	public static TileMapWindow window;
	
	public void Restart()
	{
		this.dispose();
		Start();
	}
	
	public EventPanel getEventPanel()
	{
		return eventPanel;
	}
	
	public void showEventPanel()
	{
		mapPanel.setVisible(false);
		eventPanel.setVisible(true);
		gameOverPanel.setVisible(false);
	}
	
	public void showMapPanel()
	{
		mapPanel.setVisible(true);
		eventPanel.setVisible(false);
		gameOverPanel.setVisible(false);
	}
	
	public void showGameOverPanel(String deathReasonText)
	{
		mapPanel.setVisible(false);
		eventPanel.setVisible(false);
		gameOverPanel.setVisible(true);
		
		gameOverPanel.Display(deathReasonText);
	}
	
	public TileMapWindow()
	{
		if(window != null) return;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		//setResizable(false);
		setTitle(title);
		
		mapPanel = new MapPanel(this);
		eventPanel = new EventPanel(this);
		gameOverPanel = new GameOverPanel(this);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(mapPanel);
		contentPane.add(eventPanel);
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
				JPanel contentPane = new JPanel();
				contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
				JButton linkButton = new JButton(GIT_HUB_REPO);
				linkButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						if(Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
						{
							try
							{
								Desktop.getDesktop().browse(new URI(GIT_HUB_REPO));
							} catch (IOException e)
							{
								linkButton.setText("Error: unsupported browser");
								linkButton.setEnabled(false);
							} catch (URISyntaxException e)
							{
								linkButton.setText("Error: broken URL");
								linkButton.setEnabled(false);
							}
						}
						else
						{
							linkButton.setText("Error: unsupported device");
							linkButton.setEnabled(false);
						}
					}
				});
				
				contentPane.add(new JLabel(instructions));
				contentPane.add(linkButton);
				
				if(JOptionPane.showConfirmDialog(null, contentPane, title, 
						JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
				{
					return;
				}

				window = new TileMapWindow();
				window.setVisible(true);
			}
		});
	}
}
