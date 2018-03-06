# Archive Directories
This Java program takes in a user-chosen archive, writes each directory and file it contains to a text file, and each unique file extension it contains to another text file. This was created for an UpWork.com posting.

Libraries used:

 - [Apache Commons Compress](https://commons.apache.org/proper/commons-compress/)
 - [XZ-1.8 for Java](https://tukaani.org/xz/java.html)

### Supported File Types

    .zip
    .7z
    .tar.gz
    .gz

### Running The Program
There is an executable jar in this repository. Each time I update the source code, I will also update this jar. If you're running a Linux distro, make sure you `chmod +x` if you wish to open it from your file manager. 

Otherwise, you can start the program by running `java -jar "Archive-Tree.jar"` in the directory containing the executable.

### MIT License
> Copyright (c) 2018 Austin FitzGerald
> 
> Permission is hereby granted, free of charge, to any person obtaining
> a copy of this software and associated documentation files (the
> "Software"), to deal in the Software without restriction, including
> without limitation the rights to use, copy, modify, merge, publish,
> distribute, sublicense, and/or sell copies of the Software, and to
> permit persons to whom the Software is furnished to do so, subject to
> the following conditions:
> 
> The above copyright notice and this permission notice shall be
> included in all copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
> EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
> MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
> IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
> CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
> TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
> SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
