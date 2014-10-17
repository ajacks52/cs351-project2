package genome.guicode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrintStatsFile
{
  String address = "data/statsFile.txt";
  File outfile = new File(address);

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

  public void writeToFile(Object[] obj) throws IOException
  {
    FileWriter writer = new FileWriter(this.address, true);

    writer.write("----------------------Statistics Print 1------------------------"
        + System.getProperty("line.separator"));
    writer.write("Total Time Running" + System.getProperty("line.separator"));
    writer.write("m:s 0,0" + System.getProperty("line.separator"));
    writer.write( System.getProperty("line.separator"));
    writer.write("Total Generations" + System.getProperty("line.separator") );
    writer.write("0" + System.getProperty("line.separator"));
    writer.write("Hill Climbing Generations" + System.getProperty("line.separator"));
    writer.write("0" + System.getProperty("line.separator"));
    writer.write("Genetic Algorithm Generations" + System.getProperty("line.separator"));
    writer.write("0" + System.getProperty("line.separator"));
    writer.write("Current Tribe Fitness Total Best fitness" + System.getProperty("line.separator"));
    writer.write("0" + System.getProperty("line.separator"));
    writer.write("Tribe/Population Diversity" + System.getProperty("line.separator"));
    writer.write("# of tribes 0" + System.getProperty("line.separator"));
    writer.write("average fitness of tribes 0" + System.getProperty("line.separator"));
    writer.write("----------------------------------------------------------------"
        + System.getProperty("line.separator"));
    writer.flush();
    writer.close();
    System.out.println("Wrote file");
  }

  
  public static void main(String[] args)
  {
    try
    {
      new PrintStatsFile("file1").writeToFile(null);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
