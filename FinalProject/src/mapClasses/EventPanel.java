package mapClasses;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class EventPanel extends JPanel
{
	TileMapWindow window;
	
	JLabel testEventName;
	JTextArea eventText;
	JButton returnMap;
	JPanel optionsPanel;
	
	public EventPanel(TileMapWindow window)
	{
		this.window = window;
		
		CreateComponents();
		
		setVisible(false);
	}
	
	private void CreateComponents()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		testEventName = new JLabel();
		
		returnMap = new JButton("Return to Map");
		returnMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				returnToMap();
			}
			
		});
		returnMap.setVisible(false);
		
		eventText = new JTextArea(10, 10);
		eventText.setEditable(false);
		
		optionsPanel = new JPanel();
		
		add(testEventName);
		add(eventText);
		add(optionsPanel);
		add(returnMap);
	}
	
	/**
	 * Show the event
	 */
	public void Display(MapEvent event)
	{
		//hide the return to map button
		returnMap.setVisible(false);
		
		//display the starting path
		displayPath(event.getStartPoint());
		
		//display this panel and hide others
		window.showEventPanel();
	}
	
	/**
	 * Display the path options, text, and name (choice text)
	 * @param path
	 */
	private void displayPath(EventPath path)
	{
		//reset options panel
		optionsPanel.removeAll();
		
		//set the text
		testEventName.setText(path.getName());
		eventText.setText(path.getText());
		
		//if the end, show return to map button
		if(path.isEnd())
		{
			returnMap.setVisible(true);
		}
		else
		{
			for(EventPath option : path.getOptions())
			{
				JButton optionButton = new JButton(option.getName());
				optionButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e)
					{
						displayPath(path.pickOption(option.getName()));
						
					}
					
				});
				optionsPanel.add(optionButton);
			}
		}
	}
	
	/**
	 * Hide the event panel
	 */
	public void Hide()
	{
		setVisible(false);
	}
	
	public void returnToMap()
	{
		window.showMapPanel();
	}
}
