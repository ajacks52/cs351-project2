Triangle Genome Project CS 351

Team Members Jordan Medlock and Adam Mitchell

Contents of this README:
1.  About, Usage
2.  Running Instructions
3.  Known Issues at Release
4.  Source Code, Libraries and Additional Copyright Information
--------------------------------------------------------------------------------


1. About / Usage

Triangle Genome Project, is an Image Compression algorithm to compress the size 
the input pictures.  In put pictures are set of five pictures, 
Leonardo_da_Vinci-Mona-Lisa-460x363, Claude_Monet-Poppy_Fields-450x338, 
Hokusai-Great_Wave_Off_Kanagawa-450x309, Hokusai-Great_Wave_Off_Kanagawa-200x137,
and Hot_Air_Balloon-400x300.  

The user can select a picture to display with the picture selector JComboBox then 
it will display to the screen and the hill climbing and genetic algorithms will 
try their best to replicate the chosen picture.  
By default Leonardo_da_Vinci-Mona-Lisa-460x363 is set when the program starts up.

While the program paused the user can slide through the triangle slider to see 
which triangles actually make up the picture.  

Statistics of how the algorithm is preforming are displayed on the GUI and are 
updated every second.  They are also printed to a file every 6 minutes.  This is 
how we keep a running total of the statistics of the program.  By default this 
is stored in statsFile.txt in the data directory.  You can change the name of the 
your stats file to what you desire by typing this name into the text field on the
bottom right corner of the screen.  Then pressed the append statsFile button in
statistics menu up on the menu bar.  

On the menu bar you will also find buttons to show Genome Table, Read a Genome File,
and write a Genome File.  The read Genome File button will open a file chooser 
where you can select an xml (in proper format) and once selected the contents of this 
file will build a new Genome and add it the population. 

The Displayed Genome comboBox allows users to chose which genome to display in the 
image area. 

The Displayed Tribe comboBox allows users to chose which tribe's genomes are displayed
in the Displayed Genome comboBox.

The rest of the program works as the spec defines. 
-------------------------------------------------------------------------------


2. Instructions 

To run go the a command prompt/terminal prompt of a machine that has Java 7 installed 
on it and type the following followed by pressing enter.  

java -jar TriangleGenome_Medlock_Mitchell.jar 
-------------------------------------------------------------------------------


3.  Known Issues at Release



-------------------------------------------------------------------------------

4.  Source Code, Libraries and Additional Copyright Information

Using Java 7, using only the default libraries

-------------------------------------------------------------------------------