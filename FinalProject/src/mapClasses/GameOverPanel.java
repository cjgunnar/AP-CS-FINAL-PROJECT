package mapClasses;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		deathMessage = new JLabel("You Have Died. Shame.");
		deathMessage.setFont(new Font("Arial", Font.PLAIN, 45));
		deathMessage.setAlignmentX(CENTER_ALIGNMENT);
		
		deathReason = new JTextArea("Here's how.", 10, 10);
		deathReason.setEditable(false);
		deathReason.setFont(new Font("Arial", Font.PLAIN, 23));
		
		restartButton = new JButton("Restart");
		restartButton.setAlignmentX(CENTER_ALIGNMENT);
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				window.Restart();
			}
			
		});
		
		add(deathMessage);
		add(Box.createRigidArea(new Dimension(10, 50)));
		add(deathReason);
		add(Box.createRigidArea(new Dimension(10, 50)));
		add(restartButton);
		add(Box.createRigidArea(new Dimension(10, 10)));
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
