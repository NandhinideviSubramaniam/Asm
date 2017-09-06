package com.kony.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;

public class GenerateFileList {
	static Collection<String> classFiles = null;
	public static Collection<String> getFileList(String basedir, String extn)
    {
    	try
    	{
    		ScanDirectory walker = new ScanDirectory(
    			    HiddenFileFilter.VISIBLE,
    			    FileFilterUtils.suffixFileFilter(extn));
    		//Collection<SuperEntry> hierarchyList = walker.getHierarchies(new File(args[0]));
    		classFiles = walker.getClassFileList(new File(basedir));
    		
    	} catch (IOException ioe)
    	{
    		ioe.printStackTrace();
    	}
    	return classFiles;
    }

}
