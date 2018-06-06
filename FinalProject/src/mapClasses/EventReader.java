package mapClasses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import mapClasses.MapEvent.Prerequisite;

public class EventReader
{
	final static String EVENT = "event";
	final static String REQUIREMENTS = "requirements";
	
	final static String PATH = "path";
	final static String ID = "id";
	final static String OPTION = "option";
	
	final static String OUT = "out";
	final static String RETURN_TO_MAP = "returnToMap";
	final static String GAME_OVER = "gameOver";
	
	final static String TILE = "tile";
	final static String CONTAINS = "contains";
	final static String STRUCTURE = "structure";
	
	final static String PLAYER = "player";
	final static String GOLD = "gold";
	
	final static String ACTION = "action";
	final static String TYPE = "type";
	final static String REMOVE_GOLD = "removeGold";
	final static String ADD_GOLD = "addGold";
	
	final static String FILL_THIRST = "fillWater";
	final static String REMOVE_THIRST = "removeWater";
	final static String ADD_THIRST = "addWater";
	
	//the iterator
	private static XMLEventReader eventReader;

	//the current event the iterator is on
	//placed here so helper methods have access
	private static XMLEvent event;
	
	public static boolean validateXMLSchema(String xsdPath, String xmlPath)
	{
	      try
	      {
	         SchemaFactory factory =
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	            Schema schema = factory.newSchema(new File(xsdPath));
	            Validator validator = schema.newValidator();
	            validator.validate(new StreamSource(new File(xmlPath)));
	      } catch (IOException e)
	      {
	         System.out.println("Exception: "+e.getMessage());
	         return false;
	      } catch(SAXException e1)
	      {
	         System.out.println("SAX Exception: "+e1.getMessage());
	         return false;
	      }
			
	      return true;
	}
	
	/**
	 * From XML file, returns a MapEvent object that can be played
	 * @param eventFile name of the file containing the event
	 * @return complete MapEvent
	 */
	public static MapEvent readEvent(String eventFile)
	{
//		if(!validateXMLSchema("Events/EventSchema.xsd", eventFile))
//		{
//			System.out.println("ERROR: malformed event file: " + eventFile);
//		}
		
		//create a room to act as the placeholder to use setters on
		MapEvent mapEvent = new MapEvent();
		
		try
		{
			//create the inputfactory to create the event reader
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			
			//create the input stream to create the event reader
			InputStream in = EventReader.class.getClass().getResourceAsStream(eventFile);
			
			if(in == null)
			{
				System.out.println("ERROR: could not find resource");
				return null;
			}
			
			//finally create the event reader
			eventReader = inputFactory.createXMLEventReader(in);
	
			//begin reading the XML File
			while(eventReader.hasNext())
			{	
				//set the event of the current iteration
				event = eventReader.nextEvent();
	
				//returns true if this is the start of a new element ex: <path>
				if(event.isStartElement())
				{
					StartElement startElement = event.asStartElement();
					
					event = eventReader.nextEvent();
					
					String elementName = startElement.getName().getLocalPart(); //get element name
					
					//if(elementName.equals(EVENT))
						//System.out.println("OPENING EVENT");
					
					if(elementName.equals(REQUIREMENTS))
					{
						//get all the attributes in the element
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
	
						//while the iterator has more attributes to go over
						while(attributes.hasNext())
						{
							//create variable for the individual attribute
							Attribute attribute = attributes.next();
	
							String attributeName = attribute.getName().getLocalPart();
							
							Prerequisite prereq = readPrerequisite(attributeName, attribute.getValue());
							
							if(prereq != null)
								mapEvent.addPrerequisite(prereq);
							
							else
								System.out.println("EVENT: WARNING: unrecognized attribute on event: " + attribute.getName().getLocalPart());
							
						}
					}
					
					//opening <path> element
					else if(elementName.equals(PATH))
					{		
						//System.out.println("NEW STARTING PATH");
						
						//get all the attributes in the element
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
	
						//then read the inside of the path
						EventPath path = readPath();
						//System.out.println("Read name " + path.getName());
						
						//while the iterator has more attributes to go over
						while(attributes.hasNext())
						{
							//create variable for the individual attribute
							Attribute attribute = attributes.next();
							
							Prerequisite prereq = readPrerequisite(attribute.getName().getLocalPart(), attribute.getValue());
							if(attribute.getName().getLocalPart().equals(OPTION))
							{
								//System.out.println("Setting name " + attribute.getValue());
								path.setName(attribute.getValue());
							}
							else if(prereq != null)
								mapEvent.addPrerequisite(prereq);
							else
								System.out.println("EVENT: WARNING: unrecognized attribute on event: " + attribute.getName().getLocalPart());
							
						}
						
						mapEvent.setStartPoint(path);
					}
	
				}
	
				//if this is an ending element ex: </room>
				else if (event.isEndElement())
				{
					//create object of type end element
					EndElement endElement = event.asEndElement();
	
					//get the name of the ending element as we did before with the start element
					String endElementName = endElement.getName().getLocalPart().toString();
	
					//closing </evebt> element at very end of XML document
					if(endElementName.equals(EVENT))
					{
						//return the event
						return mapEvent;
					}
				}
	
			}
	
		}
		//If file not found, display error message to console
//		catch(FileNotFoundException e)
//		{
//			System.out.println("EVENT READER ERROR: Did not find file: " + eventFile);
//			return null;
//		}
		catch(XMLStreamException e)
		{
			
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	
		System.out.println("ERROR: reached end of document without closing event element");
		return mapEvent;
	}
	
	private static EventPath readPath()
	{
		//create a path to act as the placeholder to use setters on
		EventPath path = new EventPath();
		path.setLosePath(false);
		try
		{	
			//begin reading the XML File
			while(eventReader.hasNext())
			{
				//set the event to the next iteration
				event = eventReader.nextEvent();
				
				//returns true if this is the start of a new element ex: <path>
				if(event.isStartElement())
				{
					StartElement startElement = event.asStartElement();

					String elementName = startElement.getName().getLocalPart();
					
					//System.out.println("Current start element " + elementName);
					
					event = eventReader.nextEvent();
					
					//the data inside of the event
					String eventData = null;
					try
					{
						if(!event.isEndElement() && !event.asCharacters().isIgnorableWhiteSpace())
							eventData = event.asCharacters().getData();
					}
					catch (ClassCastException e)
					{
						System.out.println("EVENT READER ERROR: Could not cast event to character data");
						//e.printStackTrace(System.out);
						System.out.println(e.toString());
					}

					//opening <path> element
					if(elementName.equals(PATH))
					{
						//System.out.println("NEW PATH INSIDE A PATH");
						//this code will set attributes (the data in the start of the element ex: <path id="1">)

						EventPath newPath = readPath();
						
						//get all the attributes in the element
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						
						//while the iterator has more attributes to go over
						while(attributes.hasNext())
						{
							//create variable for the individual attribute
							Attribute attribute = attributes.next();
							
							Prerequisite prereq = readPrerequisite(attribute.getName().getLocalPart(), attribute.getValue());
							//check for the attributes we are looking for and set them
							if(attribute.getName().getLocalPart().toString().equals(OPTION))
							{
								newPath.setName(attribute.getValue());
								//System.out.println("nested path name : " + attribute.getValue());
							}
							else if(prereq != null)
								newPath.addPrerequisite(prereq);
						}
						
						path.addOption(newPath);
					}
					else if(elementName.equals(OUT))
					{
						path.setText(eventData);
					}
					else if(elementName.equals(ACTION))
					{
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						
						//while the iterator has more attributes to go over
						while(attributes.hasNext())
						{
							//create variable for the individual attribute
							Attribute attribute = attributes.next();
							
							if(attribute.getName().getLocalPart().equals(TYPE))
							{
								Action action = readAction(attribute.getValue(), eventData);
								path.addAction(action);
								//System.out.println("Added action " + attribute.getValue() + " to path");
							}
							else
								System.out.println("READER: PATH: ACTION: WARNING: unkown attribute: " + attribute.getName().getLocalPart());
						}
					}
					else if(elementName.equals(GAME_OVER))
					{
						path.setLosePath(true);
					}
					else if(elementName.equals(RETURN_TO_MAP))
					{
						path.setEnd(true);
					}
					else
						System.out.println("UNKNOWN ELEMENT IN PATH: " + elementName);
				}

				//if this is an ending element ex: </path>
				else if (event.isEndElement())
				{
					//create object of type end element
					EndElement endElement = event.asEndElement();

					//get the name of the ending element as we did before with the start element
					String endElementName = endElement.getName().getLocalPart().toString();

					if(endElementName.equals(PATH))
					{
						//System.out.println("New path: " + path);
						return path;
					}
				}
			}

		}

		catch(XMLStreamException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}

		System.out.println("ERROR: Reached end of path");
		
		return path;
	}
	
	private static Action readAction(String type, String eventData)
	{
		//action involving player
		if(type.length() > PLAYER.length() && type.substring(0, PLAYER.length()).equals(PLAYER))
		{
			String playerReq = type.substring(PLAYER.length() + 1);
			if(playerReq.equals(REMOVE_GOLD))
			{
				return new Action()
				{
					@Override
					public void performAction(Player player, Tile tile)
					{
						int gold = player.getGold();
						int amount = 0;
						try
						{
							amount = Integer.parseInt(eventData);
						} catch(NumberFormatException e)
						{
							System.out.println("ACTION READER ERROR: non-int value for player.removeGold: " + eventData);
						}
						player.setGold(gold - amount);
					}
				};
			}
			else if(playerReq.equals(ADD_GOLD))
			{
				return new Action()
				{
					@Override
					public void performAction(Player player, Tile tile)
					{
						int gold = player.getGold();
						int amount = 0;
						try
						{
							amount = Integer.parseInt(eventData);
						} catch(NumberFormatException e)
						{
							System.out.println("ACTION READER ERROR: non-int value for player.addGold: " + eventData);
						}
						player.setGold(gold + amount);
					}
				};
			}
			else if(playerReq.equals(FILL_THIRST))
			{
				return new Action()
				{
					@Override
					public void performAction(Player player, Tile tile)
					{
						player.setThirst(100);
					}
				};
			}
			else if(playerReq.equals(ADD_THIRST))
			{
				return new Action()
				{
					@Override
					public void performAction(Player player, Tile tile)
					{
						int water = player.getThirst();
						int amount = 0;
						try
						{
							amount = Integer.parseInt(eventData);
						} catch(NumberFormatException e)
						{
							System.out.println("ACTION READER ERROR: non-int value for player.addWater: " + eventData);
						}
						player.setThirst(water + amount);
					}
				};
			}
			else if(playerReq.equals(REMOVE_THIRST))
			{
				return new Action()
				{
					@Override
					public void performAction(Player player, Tile tile)
					{
						int water = player.getThirst();
						int amount = 0;
						try
						{
							amount = Integer.parseInt(eventData);
						} catch(NumberFormatException e)
						{
							System.out.println("ACTION READER ERROR: non-int value for player.removeWater: " + eventData);
						}
						player.setThirst(water + amount);
					}
				};
			}
		}
		
		System.out.println("ACTION READER: Returned null for " + type + " " + eventData);
		
		//no match
		return null;
	}
	
	private static Prerequisite readPrerequisite(String attributeName, String attributeValue)
	{
		if(attributeName.length() > TILE.length() && attributeName.substring(0, TILE.length()).equals(TILE))
		{
			//is a requirement involving tile
			String tileReq = attributeName.substring(TILE.length() + 1);
			//System.out.println(tileReq);
			//add a prerequisite that the tile must contain a person with name of the attirbute value
			if(tileReq.equals(CONTAINS))
			{
				//System.out.println("Tile must contain: " + attribute.getValue());
				return new Prerequisite()
				{
					@Override
					public boolean checkPrerequisite(Player player, Tile tile)
					{
						for(Person p : tile.getPeople())
						{
							if(p.getName().equalsIgnoreCase(attributeValue))
							{
								return true;
							}
						}
						return false;
					}
					
				};
			}
			else if(tileReq.equals(STRUCTURE))
			{
				//System.out.println("Adding new structure prereq: " + attributeName + " " + attributeValue);
				return new Prerequisite()
				{
					@Override
					public boolean checkPrerequisite(Player player, Tile tile)
					{
						return (tile.structure.toString().equalsIgnoreCase(attributeValue));
					}

				};
			}
		}
		
		//add a prerequiste that the player
		else if(attributeName.length() > PLAYER.length() && attributeName.substring(0, PLAYER.length()).equals(PLAYER))
		{
			String playerReq = attributeName.substring(PLAYER.length() + 1);
			
			if(playerReq.equals(GOLD))
			{
				// > or < can be put in front, such as player.gold=">5"
				if(attributeValue.contains("<"))
				{
					return new Prerequisite()
					{
						@Override
						public boolean checkPrerequisite(Player player, Tile tile)
						{
							try
							{
								return player.getGold() < Integer.parseInt(attributeValue.substring(1));
							}
							catch(NumberFormatException e)
							{
								System.out.println("EVENT READER ERROR: < player.gold attribute non-int: " + attributeValue.substring(1));
								return false;
							}
						}
					};
				}
				else if(attributeValue.contains(">"))
				{
					return new Prerequisite()
					{
						@Override
						public boolean checkPrerequisite(Player player, Tile tile)
						{
							try
							{
								return player.getGold() > Integer.parseInt(attributeValue.substring(1));
							}
							catch(NumberFormatException e)
							{
								System.out.println("EVENT READER ERROR: > player.gold attribute non-int: " + attributeValue.substring(1));
								return false;
							}
						}
					};
				}
				else //exact value
				{
					return new Prerequisite()
					{
						@Override
						public boolean checkPrerequisite(Player player, Tile tile)
						{
							try
							{
								return player.getGold() == Integer.parseInt(attributeValue);
							}
							catch(NumberFormatException e)
							{
								System.out.println("EVENT READER ERROR: player.gold attribute non-int: " + attributeValue);
								return false;
							}
						}
					};
				}
			}
		}
//		else
//			System.out.println("PREREQ READER: WARNING: unrecognized attribute on event: " + attributeName);
		
		return null;
	}
}
