# ARXML-files-reader
That's a program that reads an ARXML file containing a list of containers, each with a unique ID, and reorders the containers alphabetically by their name sub- container “"SHORT-NAME" ”. The program should write the reordered containers to a new ARXML file.

The name of the arxml file shall be an argument which needs to be passed through the command line.
If the file is not having .arxml extension then a user defined handled exception “NotVaildAutosarFileException” will be triggered.
If the file is empty, then a user defined unhandled exception “EmptyAutosarFileException” will be triggered.
The output file shall be named as the same of the input file concatenated with “_mod.arxml”
