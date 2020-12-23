package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import entities.Entity;
import entities.EntityTable;
import events.EventStream;
import events.EventTable;

public class MapLoader {
	
	private static HashMap<Integer, HashMap<Integer, int[][]>> tileData = new HashMap<Integer, HashMap<Integer, int[][]>>();
	private static HashMap<Integer, HashMap<Integer, int[][]>> collisionData = new HashMap<Integer, HashMap<Integer, int[][]>>();
	private static HashMap<Integer, HashMap<Integer, int[][]>> eventMapData = new HashMap<Integer, HashMap<Integer, int[][]>>();
	private static HashMap<Integer, HashMap<Integer, EventStream[]>> eventData = new HashMap<Integer, HashMap<Integer, EventStream[]>>();
	private static HashMap<Integer, HashMap<Integer, Entity[]>> entityData = new HashMap<Integer, HashMap<Integer, Entity[]>>();
	private static HashMap<Integer, HashMap<Integer, Integer>> tilesetIDs = new HashMap<Integer, HashMap<Integer, Integer>>();
	private static HashMap<Integer, HashMap<Integer, Integer>> musicIDs = new HashMap<Integer, HashMap<Integer, Integer>>();
	private static HashMap<Integer, HashMap<Integer, Integer>> encounterTableIDs = new HashMap<Integer, HashMap<Integer, Integer>>();
	
	public static int[][] currentTileData;
	public static int[][] currentCollisionData;
	public static EventStream[] currentEventData;
	public static Entity[] currentEntityData;
	public static int currentTilesetID;
	public static int currentMusicID;
	public static int currentEncounterTableID;
	
	/**
	 * Will eventually load all maps in the resources/MAPS folder into the database.
	 */
	public static void loadAll() {
		//TODO Actual implementation
	}
	
	/**	
	 * Loads all data from the specified map into the database.
	 * @param regionID The region from which the map should be searched for.
	 * @param mapID The id of the map within that particular region.
	 */
	public static void loadMap(int regionID, int mapID) {
		try {
			BufferedReader br;
			
			if(Variables.runningAsJar) {
				String filename = "maps/MAP" + regionID + "-" + mapID + ".txt";
				InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
				br = new BufferedReader(new InputStreamReader(is));
			}
			else {
				String filename = "resources/maps/MAP" + regionID + "-" + mapID + ".txt";
				FileReader fr = new FileReader(filename);
				br = new BufferedReader(fr);
			}		
			
			int mapWidth = Integer.parseInt(br.readLine());
			int mapHeight = Integer.parseInt(br.readLine());
			
			if(mapWidth <= 0 || mapHeight <= 0) {
				System.out.println("Invalid dimensions when loading MAP" + regionID + "-" + mapID + "!");
				System.exit(1);
			}
			
			int tilesetID = Integer.parseInt(br.readLine());
			int musicID = Integer.parseInt(br.readLine());
			int encounterTableID = Integer.parseInt(br.readLine());
			
			//Load all of the tilemap data for the current map.
			int[][] tiles = new int[mapHeight][mapWidth];
			int[][] collisions = new int[mapHeight][mapWidth];
			
			for(int i = 0; i < mapHeight; i++) { 		
				String[] tokens = br.readLine().trim().split("\\s+");
				
				for(int j = 0; j < tokens.length; j++) {
					tiles[i][j] = Integer.parseInt(tokens[j]);
				}
			}
			
			//Load all of the collision data for the current map.
			for(int i = 0; i < mapHeight; i++) { 		
				String[] tokens = br.readLine().trim().split("\\s+");
				
				for(int j = 0; j < tokens.length; j++) {
					collisions[i][j] = Integer.parseInt(tokens[j]);
				}
			}
			
			//Load all of the event map data for the current map.
//			int[][] eventMap = new int[mapHeight][mapWidth];
//			for(int i = 0; i < mapHeight; i++) { 		
//				String[] tokens = br.readLine().trim().split("\\s+");
//				
//				for(int j = 0; j < tokens.length; j++) {
//					eventMap[i][j] = Integer.parseInt(tokens[j]);
//				}
//			}
			
			//Load all of the events listed according to local ID. Events will eventually be stored in a completely different file.
			int numberOfEvents = Integer.parseInt(br.readLine());
			EventStream[] events = new EventStream[numberOfEvents];
			
			if(numberOfEvents > 0) {
				String[] eventTokens = br.readLine().trim().split("\\s+");
				
				
				for(int i = 0; i < eventTokens.length; i++) {
					events[i] = EventTable.getEventStream(Integer.parseInt(eventTokens[i]));
				}
			}
			
			//Load all of the entities on this map according to local ID. Entities will evantually be stored in a completely different file.
			int numberOfEntities = Integer.parseInt(br.readLine());
			Entity[] entities = new Entity[numberOfEntities];
			
			if(numberOfEntities > 0) {
				String[] entityTokens = br.readLine().trim().split("\\s+");		
					
				for(int i = 0; i < entityTokens.length; i++) {
					entities[i] = EntityTable.getEntity(Integer.parseInt(entityTokens[i]));
				}
			}		
			
			if(tileData.containsKey(regionID)) {
				tileData.get(regionID).put(mapID, tiles);
				collisionData.get(regionID).put(mapID, collisions);
				entityData.get(regionID).put(mapID, entities);
				eventData.get(regionID).put(mapID, events);
				tilesetIDs.get(regionID).put(mapID, tilesetID);
				musicIDs.get(regionID).put(mapID, musicID);
				encounterTableIDs.get(regionID).put(mapID, encounterTableID);
			}
			else {
				tileData.put(regionID, new HashMap<Integer, int[][]>());
				tileData.get(regionID).put(mapID, tiles);
				collisionData.put(regionID, new HashMap<Integer, int[][]>());
				collisionData.get(regionID).put(mapID, collisions);
				entityData.put(regionID, new HashMap<Integer, Entity[]>());
				entityData.get(regionID).put(mapID, entities);
				eventData.put(regionID, new HashMap<Integer, EventStream[]>());
				eventData.get(regionID).put(mapID, events);
				tilesetIDs.put(regionID, new HashMap<Integer, Integer>());
				tilesetIDs.get(regionID).put(mapID, tilesetID);
				musicIDs.put(regionID, new HashMap<Integer, Integer>());
				musicIDs.get(regionID).put(mapID, musicID);
				encounterTableIDs.put(regionID, new HashMap<Integer, Integer>());
				encounterTableIDs.get(regionID).put(mapID, encounterTableID);
			}
			
			br.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Swaps to the specified map, treating it as the current location.
	 * @param regionID The id of the region to use.
	 * @param mapID The id of the map that should be swapped to within that region.
	 */
	public static void swapToMap(int regionID, int mapID) {
		currentTileData = tileData.get(regionID).get(mapID);
		currentCollisionData = collisionData.get(regionID).get(mapID);
		currentEventData = eventData.get(regionID).get(mapID);
		currentEntityData = entityData.get(regionID).get(mapID);
		currentTilesetID = tilesetIDs.get(regionID).get(mapID);
		currentMusicID = musicIDs.get(regionID).get(mapID);
		currentEncounterTableID = encounterTableIDs.get(regionID).get(mapID);
		
		Variables.currentRegionID = regionID;
		Variables.currentMapID = mapID;
	}
	
}
