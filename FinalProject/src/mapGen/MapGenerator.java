package mapGen;

import java.io.File;
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
		//lower areas fill with water
		final double waterFill = Double.parseDouble(fileContents.get(3));
		//
		final double desertFill = Double.parseDouble(fileContents.get(4));
		//
		final double savannaFill = Double.parseDouble(fileContents.get(5));
		//
		//final double grasslandFill = Double.parseDouble(fileContents.get(6));
		
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
				double mos = mosGen.eval(row * frq, col * frq);
				
				//convert to biome
				BIOME out = null;
				if(temp < waterFill)
					out = BIOME.OCEAN;
				else if(temp < desertFill)
					out = BIOME.DESERT;
				else if(temp < savannaFill)
					out = BIOME.SAVANNA;
				else
					out = BIOME.GRASSLAND;
				
				//fill map
				biomeMap[row][col] = out;
			}
		}
		
		return biomeMap;
	}
}
