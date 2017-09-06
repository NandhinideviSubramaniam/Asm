package com.kony.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class JarUtil {

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) throws Exception {
	 * copyJar("D:\\sandbox\\kony\\RnD\\sourcejar\\input.jar",
	 * "D:\\sandbox\\kony\\RnD\\sourcejar\\input1.jar"); }
	 */

	public static void CodeInjectJar(String in, String out) throws FileNotFoundException, IOException {
		JarInputStream jis = new JarInputStream(new FileInputStream(in));
		System.out.println("Output jar fie name " + out);
		JarOutputStream jos = null;
		if (null == jis.getManifest()) {
			jos = new JarOutputStream(new FileOutputStream(out));
		} else {
			jos = new JarOutputStream(new FileOutputStream(out), jis.getManifest());
		}
		JarEntry entry;
		JarEntry newEntry;
		Injector inject = null;

		while ((entry = jis.getNextJarEntry()) != null) {
			try {
				if (entry.isDirectory()) {
					jos.putNextEntry(entry);
				} else {
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					byte buffer[] = new byte[2048];
					byte[] newContent = null;
					;
					while (true) {
						int read = jis.read(buffer);
						if (read < 0) {
							break;
						}
						os.write(buffer, 0, read);
					}

					if (entry.getName().contains(".class") && !entry.getName().contains(".classpath")) {
						System.out.println("Class name--"+entry.getName());
						inject = new Injector(null);
						newContent = inject.inject(new ByteArrayInputStream(os.toByteArray()));
					} else {
						
							newContent = os.toByteArray();
					}
					os.close();
					newEntry = new JarEntry(entry.getName());
					newEntry.setSize(newContent.length);
					jos.putNextEntry(newEntry);
					jos.write(newContent);
				}

				jos.closeEntry();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		jis.close();
		jos.close();
		System.out.println("Closing jar file");
	}
	/**
	 * Create a jar file from a particular directory.
	 * 
	 * @param root
	 *            in the root directory
	 * @param directory
	 *            in the directory we are adding
	 * @param jarStream
	 *            the jar stream to be added to
	 * @throws IOException
	 *             on IOException
	 */
	/*
	 * protected void createJarFromDirectory(File root, File directory,
	 * JarOutputStream jarStream) throws IOException { byte[] buffer = new
	 * byte[40960]; int bytesRead;
	 * 
	 * File[] filesToAdd = directory.listFiles();
	 * 
	 * for (File fileToAdd : filesToAdd) { if (fileToAdd.isDirectory()) {
	 * createJarFromDirectory(root, fileToAdd, jarStream); } else {
	 * FileInputStream addFile = new FileInputStream(fileToAdd); try { // Create
	 * a jar entry and add it to the temp jar. String entryName =
	 * fileToAdd.getPath().substring(root.getPath().length() + 1);
	 * 
	 * // If we leave these entries as '\'s, then the resulting zip file won't
	 * be // expandable on Unix operating systems like OSX, because it is
	 * possible to // have filenames with \s in them - so it's impossible to
	 * determine that this // is actually a directory. entryName =
	 * entryName.replace('\\', '/'); JarEntry entry = new JarEntry(entryName);
	 * jarStream.putNextEntry(entry);
	 * 
	 * // Read the file and write it to the jar. while ((bytesRead =
	 * addFile.read(buffer)) != -1) { jarStream.write(buffer, 0, bytesRead); }
	 * jarStream.closeEntry(); } finally { addFile.close(); } } } }
	 * 
	 *//**
		 * Create a JAR file from a directory, recursing through children.
		 * 
		 * @param directory
		 *            in directory source
		 * @param outputJar
		 *            in file to output the jar data to
		 * @return out File that was generated
		 * @throws IOException
		 *             when there is an I/O exception
		 *//*
		 * public File createJarFromDirectory(String directory, File outputJar)
		 * throws IOException { JarOutputStream jarStream = null; try { if
		 * (!outputJar.getParentFile().exists()) {
		 * outputJar.getParentFile().mkdirs(); }
		 * 
		 * jarStream = new JarOutputStream(new FileOutputStream(outputJar));
		 * File dir = new File(directory);
		 * 
		 * createJarFromDirectory(dir, dir, jarStream); } finally { if
		 * (jarStream != null) { jarStream.close(); } } return outputJar; }
		 */

}
