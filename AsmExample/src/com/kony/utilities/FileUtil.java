package com.kony.utilities;

import java.io.File;

public class FileUtil {
	public static String generateNewFileName(String fileName, String destFolder)
	{
		File f = new File(fileName);
		return destFolder + File.separator + f.getName();			
	}

}
