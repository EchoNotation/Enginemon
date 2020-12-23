package events;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import util.Variables;

/**
 * The class that contains all of the EventStreams used throughout the game.
 * @author Thomas
 *
 */
public class EventTable {
	private static HashMap<Integer, HashMap<Integer, HashMap<Integer, EventStream>>> streams = new HashMap<Integer, HashMap<Integer, HashMap<Integer, EventStream>>>();
	private static int currentGlobalID = 0;
	
	/**
	 * Returns the EventStream designated by the passed ID from the current map.
	 * @param eventID The global ID of the EventStream.
	 * @return The appropriate EventStream.
	 */
	@Deprecated
	public static EventStream getEventStream(int eventID) {
		if(eventID == 0) {
			Event[] seq = new Event[3];
			seq[0] = new PrintEvent(-1, "This is a test of the...");
			seq[1] = new DelayEvent(-1, 60);
			seq[2] = new PrintEvent(-1, "Sequential Event System (TM)");
			Event[] par = new Event[1];
			par[0] = new PrintEvent(1, 60, -1, "Parallel Event System (TM)");
			return new EventStream(0, 0, seq, par);
		}
		else if(eventID == 1) {
			Event[] seq = new Event[2];
			String[] textData = new String[3];
			textData[0] = "This is a test of the text display system...";
			textData[1] = "...And this is the follow-up message!";
			textData[2] = "Capiche?";
			String[] optionData = new String[3];
			optionData[0] = "Sure";
			optionData[1] = "Never!";
			optionData[2] = "What?";
			seq[0] = new TextEvent(-1, textData, optionData);
			int[] streamIDs = new int[3];
			streamIDs[0] = 2;
			streamIDs[1] = 3;
			streamIDs[2] = 1;
			seq[1] = new SelectionBranchEvent(0, streamIDs);
			return new EventStream(1, 1, seq, new Event[0]);
		}
		else if(eventID == 2) {
			String[] textData2 = new String[1];
			textData2[0] = "Sweet.";
			Event[] seq2 = new Event[1];
			seq2[0] = new TextEvent(-1, textData2, new String[0]);
			return new EventStream(2, 2, seq2, new Event[0]);
		}
		else if(eventID == 3) {
			String[] textData3 = new String[1];
			textData3[0] = "Very well Mr. Bond, but now I will have to kill you.";
			Event[] seq3 = new Event[1];
			seq3[0] = new TextEvent(-1, textData3, new String[0]);
			return new EventStream(3, 3, seq3, new Event[0]);
		}
		return new EventStream(-1, -1, new Event[0], new Event[0]);
	}
	
	/**
	 * Will eventually return the selected EventStream from any particular map.
	 * @param regionID The ID of the region from which to choose the map.
	 * @param mapID The ID of the map from that selected region.
	 * @param eventID The local ID of the EventStream within that particular map to return.
	 * @return The appropriate EventStream.
	 */
	public static EventStream getEventStream(int regionID, int mapID, int eventID) {
		//TODO Actual implementation.
		return streams.get(regionID).get(mapID).get(eventID);
	}
	
	/**
	 * Will eventually load all the events coresponding to the specified map.
	 * @param regionID The ID of the region from which to draw the map.
	 * @param mapID The ID of the map within that region to load the events from.
	 */
	public static void loadEvents(int regionID, int mapID) {
		BufferedReader br;
		HashMap<Integer, EventStream> temp = new HashMap<Integer, EventStream>();
		
		if(!streams.containsKey(regionID)) {
			streams.put(regionID, new HashMap<Integer, HashMap<Integer, EventStream>>());
		}

		try {
			if(Variables.runningAsJar) {
				String filename = "events/EVENTS" + regionID + "-" + mapID + ".txt";
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
				br = new BufferedReader(new InputStreamReader(is));
			}
			else {
				String filename = "resources/events/EVENTS" + regionID + "-" + mapID + ".txt";
				FileReader fr;
				fr = new FileReader(filename);
				br = new BufferedReader(fr);
			}
			
			int numberOfStreams = Integer.parseInt(br.readLine()); //Read the total number of EventStreams for this map.
			
			for(int i = 0; i < numberOfStreams; i++) {
				int localID = Integer.parseInt(br.readLine()); //Read the local ID of this EventStream.
				int eventsInThisStream = Integer.parseInt(br.readLine()); //Read the total number of events in this stream.
				ArrayList<Event> seq = new ArrayList<Event>();
				ArrayList<Event> par = new ArrayList<Event>();
				
				for(int j = 0; j < eventsInThisStream; j++) {
					//Get the data that is needed for every different type of event.
					String eventName = br.readLine();
					boolean isParallel = Boolean.parseBoolean(br.readLine());
					int idToWaitFor = -1;
					int framesToDelay = -1;
					
					if(isParallel) {
						idToWaitFor = Integer.parseInt(br.readLine());
						framesToDelay = Integer.parseInt(br.readLine());
					}
					
					int inputIndex = Integer.parseInt(br.readLine());
					
					switch(eventName) {
					case "DelayEvent":
						//This event should only ever be sequential.
						int framesToWait = Integer.parseInt(br.readLine());
						seq.add(new DelayEvent(inputIndex, framesToWait));
						break;
					case "PrintEvent":
						String message = br.readLine();
						if(isParallel) {
							par.add(new PrintEvent(idToWaitFor, framesToDelay, inputIndex, message));
						}
						else {
							seq.add(new PrintEvent(inputIndex, message));
						}
						break;
					case "TextEvent":
						int lengthOfTextData = Integer.parseInt(br.readLine());
						String[] textData = new String[lengthOfTextData];
						for(int k = 0; k < lengthOfTextData; k++) {
							textData[k] = br.readLine();
						}
						int lengthOfOptionsData = Integer.parseInt(br.readLine());
						String[] optionsData = new String[lengthOfOptionsData];
						for(int k = 0; k < lengthOfOptionsData; k++) {
							optionsData[k] = br.readLine();
						}
						
						if(isParallel) {
							par.add(new TextEvent(idToWaitFor, framesToDelay, inputIndex, textData, optionsData));
						}
						else {
							seq.add(new TextEvent(inputIndex, textData, optionsData));
						}
						break;
					case "WarpEvent":
						//Warp events should never be parallel.
						int destRegionID = Integer.parseInt(br.readLine());
						int destMapID = Integer.parseInt(br.readLine());
						int destX = Integer.parseInt(br.readLine());
						int destY = Integer.parseInt(br.readLine());
						seq.add(new WarpEvent(inputIndex, destRegionID, destMapID, destX, destY));
						break;
					case "SelectionBranchEvent":
						//Branch events should never be parallel.
						int numberOfStreamIndices = Integer.parseInt(br.readLine());
						int[] streamIndices = new int[numberOfStreamIndices];
						for(int k = 0; k < numberOfStreamIndices; k++) {
							streamIndices[k] = Integer.parseInt(br.readLine());
						}
						seq.add(new SelectionBranchEvent(inputIndex, streamIndices));
						break;
					case "RandomBranchEvent":
						int numberOfStreamIndices2 = Integer.parseInt(br.readLine());
						int[] streamIndices2 = new int[numberOfStreamIndices2];
						int[] probabilities = new int[numberOfStreamIndices2];
						for(int k = 0; k < numberOfStreamIndices2; k++) {
							String[] randomBranchTokens = br.readLine().trim().split("\\s+");
							streamIndices2[k] = Integer.parseInt(randomBranchTokens[0]);
							probabilities[k] = Integer.parseInt(randomBranchTokens[1]);
						}
						break;
					default:
						System.out.println("Unrecognized event type when loading events for map: " + regionID + "-" + mapID + ". EventType: " + eventName);
						break;
					}
				}
				
				Event[] seqArray = seq.toArray(new Event[seq.size()]);
				Event[] parArray = par.toArray(new Event[par.size()]);
				temp.put(localID, new EventStream(currentGlobalID, localID, seqArray, parArray));
				currentGlobalID++;
			}
			
			streams.get(regionID).put(mapID, temp);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Will eventually load the events for all maps in the game.
	 */
	public static void loadAllEvents() {
		//TODO Actual implementation.
	}
	
}
