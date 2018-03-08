import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * This program was created per these specifications:
 *    "I need a JAVA GUI app that can run on Windows and Linux and can without extracting the zip contents
 *       read the contents of these extensions
 *    1) .zip
 *    2) .7z
 *    3) .tar.gz
 *    4) .rz
 *    5) .gz
 *    6) .s7z
 *
 *    Then generate 2 text files -> one text file for the entire files and folder structure contained in the zipped file
 *       (should contain full path, as well as extensions), and a second text file to show only unique file extensions."
 *
 * The program follows each specification listed, except for .rz (RZIP) and .s7z (7Zip for Mac).
 *
 * RZIP usage is not implemented because there are no Java libraries that have support for it, I may end up making
 *    my own later on.
 * 7Zip for Mac usage is not implemented because of the extension's obscurity. I can find no test files online, and have
 *    no way to compress one myself. I'm pretty sure the regular .7z code would work though.
 *
 * The program takes in a user-chosen file, matching filetype specifications, and creates two text files for it.
 * Without extracting, it creates a text file that lists all directories and files, and another that lists all
 *    unique file extensions contained in the archive.
 *
 * @author Austin FitzGerald
 */

class Main
{
   public static void main(String[] args)
   {
      // Instantiating the file chooser
      JFileChooser jfc = new JFileChooser(System.getProperty("user.dir")); // Setting the file chooser to the current directory

      // Turning off the all-files filter
      jfc.setAcceptAllFileFilterUsed(false);

      // Setting the file extensions allowed
      jfc.addChoosableFileFilter(new FileNameExtensionFilter("Compressed", "zip", "7z", "tar.gz", "rz", "gz", "s7z"));

      // Displaying the file chooser, setting it to an integer representing if the file is chosen successfully
      int returnValue = jfc.showOpenDialog(null);

      if (returnValue == JFileChooser.APPROVE_OPTION) // If the file is chosen...
      {
         File selectedFile = jfc.getSelectedFile(); // Initializes a File object with the data from the chosen file

         // Creating the object that parses the archive data and creates the text files
         StructureCreator structCreate = new StructureCreator(getExtension(selectedFile), selectedFile);

         try
         {
            structCreate.runner(); // Actually starting the parsing
            System.out.println("done");
         } catch (IOException e)
         {
            System.out.println("Exception! " + e);
         }

      }

   }

   /**
    * This method gets the file extension of a given file.
    * @param selectedFile The file to pull the extension from
    * @return a String that holds the extension
    */
   private static String getExtension(File selectedFile)
   {
      String fileName = selectedFile.getAbsolutePath();
      String file = fileName.substring(fileName.lastIndexOf(File.separator));

      return file.substring(file.indexOf(".")); // returns in format of ".zip" or ".tar.gz"
   }


}
