package mapClasses;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class GameOverPanel extends JPanel
{
	JLabel deathMessage;
	JTextArea deathReason;
	
	public GameOverPanel()
	{
		CreateComponents();
		setVisible(false);
	}
	
	private void CreateComponents()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		deathMessage = new JLabel("You Have Died. Shame.");
		deathReason = new JTextArea("Here's how.", 10, 10);
		deathReason.setEditable(false);
		
		add(deathMessage);
		add(deathReason);
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
