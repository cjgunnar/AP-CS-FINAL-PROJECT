package mapGen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import mapClasses.Tile;
import mapClasses.Tile.BIOME;

/**
 * Generate the TileMap
 * @author Caden
 *
 */
public class MapGenerator
{
	public static Tile[][] generateMap(int size)
	{
		int seed = (int)(Math.random() * 1000);
		return generateMap(seed, size);
	}
	
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
				double temp = tempGen.eval(row * frq + 5, col * frq + 5);
				
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
