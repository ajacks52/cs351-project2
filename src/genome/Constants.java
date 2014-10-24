package genome;
/*********************************************************************************
 * @author Jordan Medlock 
 * @author Adam Mitchell
 * a class to store static final variables to use throughout the project
 *********************************************************************************/
import java.util.Random;

public class Constants
{
  /*********************************************************************************
   * Random random = new Random();
   * Random variable for the whole project
   * GENOME_SIZE = 200;
   * The size of a default genome 200 triangles 
   * DEBUG = false;
   * Print debugging messages and turn on assert testing
   * RASTER = true;
   * Using the raster image fitness method 10 times faster!
   ********************************************************************************/
  public static final Random random = new Random();
  public static final int GENOME_SIZE = 200;
  public static boolean DEBUG = false;
  public static boolean RASTER = true;
  public static final int TRIBE_SIZE = 8;

}
