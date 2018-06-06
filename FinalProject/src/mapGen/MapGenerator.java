package mapGen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import mapClasses.Tile;
import mapClasses.Tile.BIOME;
import mapClasses.Tile.STRUCTURE;
import peopleObjects.Bandit;

/**
 * Generate the TileMap
 * @author Caden
 *
 */
public class MapGenerator
{
	/** The seed of the map */
	int seed;
	
	/** temperature noise generator */
	OpenSimplexNoise tempGen;
	
	/** moisture noise generator */
	OpenSimplexNoise mosGen;
	
	/** new random generator */
	OpenSimplexNoise banditGen;
	
	/** Generator for the population */
	OpenSimplexNoise popGen;
	
	/**
	 * Create a MapGenerator with the specified seed
	 * @param seed
	 */
	public MapGenerator(int seed)
	{
		this.seed = seed;
		
		//create the generators
		tempGen = new OpenSimplexNoise(seed);
		mosGen = new OpenSimplexNoise(seed + 1);
		banditGen = new OpenSimplexNoise(seed + 2);
		popGen = new OpenSimplexNoise(seed + 3);
		
		System.out.println("Generated map with seed: " + seed);
	}
	
	/** Create a MapGenerator with a random seed from 0 to 99999 */
	public MapGenerator()
	{
		this((int)(Math.random() * 100000));
	}
	
	/**
	 * Generates a map area from starting coordinate
	 * <p>
	 * Will always generate an exact map from the same coordinates
	 * @param x left-most x position
	 * @param y left-most y position
	 * @param size size of the square map
	 * @return generated map
	 */
	public Tile[][] generateMapArea(int x, int y, int size)
	{
		 Tile[][] map = new Tile[size][size];
		 
		 final double tempFrq = 0.005;
		 final double mosFrq = 0.01;
		 
		 final double tempPow = 1.5;
		 final double mosPow = 0.5;
		 
		 for(int i = 0; i < size; i++)
		 {
			 for(int j = 0; j < size; j++)
			 {
				 //generate temperature
				 double temp = tempGen.eval((x + i + 50) * tempFrq, (y + j) * tempFrq);
				 temp = Math.pow(temp, tempPow);
				 
				 double mos = mosGen.eval((x + i) * mosFrq, (y + j) * mosFrq);
				 mos = Math.pow(mos, mosPow);
				 
				 BIOME biome = getBiome(temp, mos);
				 
				 Tile tile = new Tile(biome);
				 
				 //population
				 if(biome != BIOME.OCEAN) //no water cities
				 {
					 //calculate population at that spot
					 double pop = popGen.eval((x + i) * 7, (y + j) * 7);
					 if(pop > 0.7) //city
					 {
						tile.setStructure(STRUCTURE.CITY);
					 }
					 else if(pop > 0.6) //village
					 {
						 tile.setStructure(STRUCTURE.VILLAGE);
					 }
				 }
				 
				 //random characters
				 if(biome != BIOME.OCEAN) //no characters on water
				 {
					 if(banditGen.eval((x + i) * 20, (y + j) * 20) > 0.7)
					 {
						 //System.out.println("Made a bandit at (" + (x + i) + (y + j) + ")");
						 tile.addPerson(new Bandit());
					 } 
				 }
				 
				 map[i][j] = tile;
			 }
		 }
		 
		 //System.out.println("Generated map at (" + x + "," + y + ") of size " + size);
		 
		 return map;
	}
	
	//STATIC METHODS
	/**
	 * Generate a fixed-size square map of the given size
	 * <p>
	 * Generates a random seed from 0 to 999 inclusive
	 * @param size of the map
	 * @return 2D array of generated tiles
	 */
	public static Tile[][] generateMap(int size)
	{
		int seed = (int)(Math.random() * 1000);
		return generateMap(seed, size);
	}
	
	/**
	 * Generate a fixed-size square map of the given size
	 * @param seed the seed to generate the map with
	 * @param size the size of the map
	 * @return
	 */
	public static Tile[][] generateMap(int seed, int size)
	{
		BIOME[][] biomeMap = generateBiome(seed, size);
		
		Tile[][] map = new Tile[size][size];
		
		for(int row = 0; row < size; row ++)
		{
			for(int col = 0; col < size; col++)
			{
				Tile newTile = new Tile(biomeMap[row][col]);
				map[row][col] = newTile;
			}
		}
		
		System.out.println("Generated map with seed: " + seed);
		
		return map;
	}
	
	private static BIOME[][] generateBiome(int seed, int size)
	{
		//retrieve data from txt file
		List<String> fileContents = null;
		try
		{
			fileContents = Files.readAllLines(Paths.get("map-generator-values.txt"));
			for(int i = 0; i < fileContents.size(); i++)
			{
				String str = fileContents.get(i);
				str.replace(" ", "");
				int idx = str.indexOf("#");
				if(idx != -1)
				{
					str = str.substring(0, idx);
				}
				fileContents.set(i, str);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//frequency (zoom in / out)
		final double frq = Double.parseDouble(fileContents.get(1));
		//power (flat bottoms)
		final double power = Double.parseDouble(fileContents.get(2));
		/*
		/lower areas fill with water
		final double waterFill = Double.parseDouble(fileContents.get(3));
		//
		final double desertFill = Double.parseDouble(fileContents.get(4));
		//
		final double savannaFill = Double.parseDouble(fileContents.get(5));
		//
		//final double grasslandFill = Double.parseDouble(fileContents.get(6));
		 */		
		OpenSimplexNoise tempGen = new OpenSimplexNoise(seed);
		OpenSimplexNoise mosGen = new OpenSimplexNoise(seed + 1);
		
		BIOME[][] biomeMap = new BIOME[size][size];
		
		for(int row = 0; row < size; row++)
		{
			for(int col = 0; col < size; col++)
			{
				//calculate temp
				double temp = tempGen.eval(row * frq, col * frq);
				
				temp = Math.pow(temp, power);
				
				//calculate moisture
				double mos = mosGen.eval(row * frq * 2, col * frq * 2);
				mos = Math.pow(mos, 0.5);
				
				//convert to biome
				BIOME out = getBiome(temp, mos);
				
				//fill map
				biomeMap[row][col] = out;
			}
		}
		
		return biomeMap;
	}
	
	/**
	 * Picks a biome type based on the temperature and the moisture
	 * @param temp temperature
	 * @param mos moisture
	 * @return BIOMES.type
	 */
	private static BIOME getBiome(double temp, double mos)
	{
		if(temp < 0.1) return BIOME.OCEAN;
		if(temp < 0.12) return BIOME.COAST;
		
		if(temp < 0.25) //low temp
		{
			if(mos < 0.2) return BIOME.ICE_SHEET;
			if(mos < 0.5) return BIOME.TUNDRA;
			else return BIOME.PINE_FOREST;
		}
		else if(temp < 0.8) //med temp
		{
			if(mos < 0.33) return BIOME.SCHRUBLAND;
			if(mos < 0.66) return BIOME.GRASSLAND;
			else return BIOME.DECIDIOUS_FOREST;
		}
		else //high temp
		{
			if(mos < 0.33) return BIOME.DESERT;
			if(mos < 0.78) return BIOME.SAVANNA;
			else return BIOME.RAINFOREST;
		}
	}
}
