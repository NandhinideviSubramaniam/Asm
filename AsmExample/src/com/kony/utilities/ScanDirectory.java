package com.kony.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;


public class ScanDirectory extends DirectoryWalker<String> {
      
	    public ScanDirectory() {
	        super();
	    }
	    
	    public ScanDirectory(IOFileFilter dirFilter, IOFileFilter fileFilter) {
	        super(dirFilter, fileFilter, -1);
	    }
	    
	   
	    	    

	    public Collection<String> getClassFileList(File startDirectory) throws IOException {
	    	Collection<String> entries = new ArrayList<String>();	    
	        walk(startDirectory, entries);
	        return entries;
	    }

	    @Override
	    protected void handleFile(File file, int depth,
    		Collection<String> results) throws IOException
	    {
	    	results.add(file.getAbsolutePath());
	    	return;
	    }
	}

