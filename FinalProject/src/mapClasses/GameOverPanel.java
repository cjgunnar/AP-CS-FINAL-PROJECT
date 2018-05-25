package mapClasses;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GameOverPanel extends JPanel
{
	JLabel deathMessage;
	JTextArea deathReason;
	JButton restartButton;
	
	TileMapWindow window;
	
	public GameOverPanel(TileMapWindow window)
	{
		this.window = window;
		
		CreateComponents();
		setVisible(false);
	}
	
	private void CreateComponents()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		deathMessage = new JLabel("You Have Died. Shame.");
		
		deathReason = new JTextArea("Here's how.", 10, 10);
		deathReason.setEditable(false);
		
		restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				window.Restart();
			}
			
		});
		
		add(deathMessage);
		add(deathReason);
		add(restartButton);
	}
	
	public void Display(String deathReasonText)
	{
		deathReason.setText(deathReasonText);
		setVisible(true);
	}
	
	public void Hide()
	{
		setVisible(false);
	}
}
