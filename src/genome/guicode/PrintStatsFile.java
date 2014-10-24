package genome.guicode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/******************************************************************************************************
 * @author Adam Mitchell
 * A utility class that prints the stats from the program gets called in main frame controller every 
 * 6 minutes.
 *****************************************************************************************************/
public class PrintStatsFile
{
  String address = "data/statsFile.txt";
  File outfile = new File(address);

  /****************************************************************************************************
   * The name of the file to be printed 
   * @param address
  *****************************************************************************************************/
  public PrintStatsFile(String address)
  {
    if (address.contains(".txt"))
    {
      this.address = "data/" + address;
    }
    else
    {
      this.address = "data/" + address + ".txt";
    }
  }

  /***************************************************************************************************
   * Writes the stats file 
   * void writeToFile(boolean append,int[] stats) throws IOException
   * 
   * @param append
   * @param stats
   * @throws IOException
   **************************************************************************************************/
  public void writeToFile(boolean append,int[] stats) throws IOException
  {
    FileWriter writer = new FileWriter(this.address, append);

    writer.write("----------------------Statistics---------------------------------"
        + System.getProperty("line.separator"));
    writer.write("Total Time Running" + System.getProperty("line.separator"));
    writer.write("m:s "+stats[0]+":"+stats[1] + System.getProperty("line.separator"));
    writer.write("Total Generations" + System.getProperty("line.separator") );
    writer.write(stats[2] + System.getProperty("line.separator"));
    writer.write("Hill Climbing Generations" + System.getProperty("line.separator"));
    writer.write(stats[3] + System.getProperty("line.separator"));
    writer.write("Genetic Algorithm Generations" + System.getProperty("line.separator"));
    writer.write(stats[4] + System.getProperty("line.separator"));
    writer.write("Generations per Second" + System.getProperty("line.separator"));
    writer.write(stats[9] + System.getProperty("line.separator"));
    writer.write("Tribe/Population Diversity" + System.getProperty("line.separator"));
    writer.write("# of tribes "+stats[5] + System.getProperty("line.separator"));
    writer.write("# of genomes "+stats[6] + System.getProperty("line.separator"));
    writer.write("Overall Best fitness" + System.getProperty("line.separator"));
    writer.write(stats[7] + System.getProperty("line.separator"));
    writer.write("----------------------------------------------------------------"
        + System.getProperty("line.separator"));
    writer.flush();
    writer.close();
    System.out.println("Wrote file");
  }
}
