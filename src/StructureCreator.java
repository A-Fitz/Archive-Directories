import java.io.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * This class is used to parse data from the chosen file, based on the file extension, and output into two text files.
 */

class StructureCreator
{
   // Initializing objects to be used by multiple methods
   private final File selectedFile;
   private final String extension;
   private String fileName;
   private FileWriter fw;
   private final ArrayList<String> extensions = new ArrayList<>();

   /**
    * This constructor is used when creating a new object of this class.
    *
    * @param extension    The String holding the extension that was found in the main class
    * @param selectedFile The user chosen file
    */
   public StructureCreator(String extension, File selectedFile)
   {
      this.selectedFile = selectedFile;
      this.extension = extension;
      this.fileName = selectedFile.getName();
   }

   /**
    * This is the main function which starts the parsing.
    *
    * @throws IOException Used when a file cannot be read
    */
   public void runner() throws IOException
   {
      createFile(true); // Creates the structure text file

      // Switch case to determine which method to call based on the file extension provided
      switch (extension)
      {
         case ".zip":
            zipStructure();
            break;
         case ".7z":
            sevenZStructure();
            break;
         case ".tar.gz":
            targzStructure();
            break;
         case ".rz":
            //.rz structure
            break;
         case ".gz":
            targzStructure();
            break;
         case ".s7z":
            //.s7z structure
            break;
      }
      uniqueExtensions(); // Calling the method that creates and fills the unique extensions text file
   }

   /**
    * This method creates either the directory structure text file or the extensions text file. Both are empty.
    *
    * @param which Used to decide which file to create
    * @throws IOException In case a file cannot be written
    */
   private void createFile(Boolean which) throws IOException
   {
      if (which)
      {
         fileName = fileName.replace(".", "") + "-Structure.txt";
         fw = new FileWriter(fileName);
      } else
      {
         fileName = fileName.replace("Structure", "Extensions");
         fw = new FileWriter(fileName);
      }
   }

   /**
    * This is the last method to be called. It runs through the previously created extensions list, creates the extensions
    * file, and writes each unique extension to it.
    *
    * @throws IOException In case the file cannot be written
    */
   private void uniqueExtensions() throws IOException
   {
      ArrayList<String> temp = new ArrayList<>(); // Contains every extension, non-unique

      // This loops through to add only one of each extension into a new list
      for (String l : extensions)
      {
         if (! temp.contains(l))
         {
            temp.add(l);
         }
      }

      createFile(false); // Creates the extension text file

      // Writes each part of the unique extension list
      FileWriter writer = new FileWriter(fileName);
      for (String str : temp)
      {
         writer.write(str + "\r\n");
      }
      writer.close();
   }

   /**
    * Kind of like the Main class' getExtension, but this takes in a String instead of the file.
    *
    * @param tempName The complete file name, including directories and such
    * @return The file extension, inside a String
    */
   private String getExtension(String tempName)
   {
      String file = tempName.substring(tempName.lastIndexOf("/"));

      return file.substring(file.indexOf(".")); // returns in format of ".zip" or ".tar.gz"
   }

   /**
    * This method sorts the previously created structure text file alphabetically, it also adds trailing slashes if needed.
    *
    * @throws IOException In case the file cannot be read, or written
    */
   private void sortFile() throws IOException
   {
      FileReader fileReader = new FileReader(fileName); // Creates a new file reader with the structure file we created
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      List<String> lines = new ArrayList<>(); // Temporary list to hold lines from the structure text file
      String line = null;
      while ((line = bufferedReader.readLine()) != null)
      {
         String temp = line;
         temp = needsSlash(line); // Calls the needsSlash method that adds an ending slash if needed
         lines.add(temp);
      }
      bufferedReader.close();

      lines.sort(Collator.getInstance()); // Sorts the entire list of Strings

      // Writes the sorted list to the same file (overwriting it)
      FileWriter writer = new FileWriter(fileName);
      for (String str : lines)
      {
         writer.write(str + "\r\n");
      }
      writer.close();
   }

   /**
    * Checks if a String is a directory, and if it has a trailing slash. If it doesn't have one, add one.
    *
    * @param line The String to be checked
    * @return The String with a trailing slash if needed
    */
   private String needsSlash(String line)
   {
      if (! line.contains("."))
      { // If not a file...
         if (! line.endsWith("/"))
         { // If it doesn't already have an ending slash...
            line += "/"; // Add one
         }
      } else
      {
         // If it is a file, add it to the extensions list. We'll use it later.
         extensions.add(getExtension(line));
      }
      return line;
   }

   /**
    * This method is for .zip archives. It reads each directory and file, which are called entries, and writes
    * each one to the structure text file.
    */
   private void zipStructure()
   {
      try (ZipFile zipFile = new ZipFile(selectedFile.getPath()))
      {
         Enumeration zipEntries = zipFile.entries();
         String fname;
         while (zipEntries.hasMoreElements())
         {
            fname = ((ZipEntry) zipEntries.nextElement()).getName();
            fw.write(fname + "\n");
         }
         fw.close();
         sortFile(); // Sorts the file before ending this method
      } catch (IOException e)
      {
         System.out.println("Exception! " + e);
      }
   }

   /**
    * This method is for .7z archives (and possibly .s7z?). It reads each entry and writes each to the structure text file.
    */
   private void sevenZStructure()
   {
      try
      {
         SevenZFile sevenZFile = new SevenZFile(new File(selectedFile.getPath()));
         SevenZArchiveEntry entry;
         String fname;
         while ((entry = sevenZFile.getNextEntry()) != null)
         {
            fname = entry.getName();
            fw.write(fname + "\n");
         }
         fw.close();
         sortFile(); // Sorts the structure text file
      } catch (Exception e)
      {
         System.out.println("Exception! " + e);
      }
   }

   /**
    * This method is for .tar.gz and .gz archives. It reads each entry and writes them to the structure text file.
    */
   private void targzStructure()
   {
      try
      {
         TarArchiveInputStream tarFile = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(selectedFile.getPath())));
         TarArchiveEntry entry;
         String fname;
         while ((entry = tarFile.getNextTarEntry()) != null)
         {
            fname = entry.getName();
            fw.write(fname + "\n");
         }
         fw.close();
         sortFile(); // Sorts the structure text file
      } catch (Exception e)
      {
         System.out.println("Exception! " + e);
      }
   }


}
