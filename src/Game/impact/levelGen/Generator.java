package Game.impact.levelGen;

import java.io.FileOutputStream;
import java.util.Random;

import android.content.Context;

import Game.impact.GameActivity;

public class Generator {
	private static int WIDTH = 480;
	private static int TILE_SIZE = 40;
	private String[] entityType = new String[10];
	public static String path;
    Random gen;
	
    long x = 20+System.currentTimeMillis(), y = 50+System.currentTimeMillis(), z = 35+System.currentTimeMillis(), w = 18 + System.currentTimeMillis();
    long xor128() 
    {
        long t;
     
        t = x ^ (x << 11);
        x = y; y = z; z = w;
        return w = w ^ (w >> 19) ^ t ^ (t >> 8);
    }
    
    public Generator()
    {
    	gen = new Random(xor128());
    }
    
	public void genlvl(GameActivity activity, int level, int height)
	{
		try
		{
			FileOutputStream out = activity.openFileOutput("" + level + ".lvl",  Context.MODE_PRIVATE);
			
			String line = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	        line += "<level width=\"480\" height=\"" + height + "\">\n";
	        out.write(line.getBytes());
	        
	        int tiles = height/TILE_SIZE;
	        for(int i = 10; i <= tiles; ++i)
	        {
	        	int n = gen.nextInt(3);
	        	if(n == 0)
	        	{
	        		continue;
	        	}
	        	else if(n == 1)
	        	{
	        		line = "    <entity x=\"" + gen.nextInt(WIDTH-TILE_SIZE) + "\" y=\"" + i*TILE_SIZE + "\" type=\"coin\"/>\n";
	        	}
	        	else if(n == 2)
	        	{
	        		line = "    <entity x=\"" + gen.nextInt(WIDTH-TILE_SIZE) + "\" y=\"" + i*TILE_SIZE + "\" type=\"platform1\"/>\n";
	        	}
	        	
        		out.write(line.getBytes());
	        }
	        line = "    <entity x=\"240\" y=\"10\" type=\"platform1\"/>\n";
	        line += "    <entity x=\"240\" y=\"50\" type=\"player\"/>\n";
	        line += "</level>";
	        out.write(line.getBytes());
	        
	        out.close();
		}catch(Exception e)
		{
			System.out.println("Failed to Write\n");
		}
	}
}