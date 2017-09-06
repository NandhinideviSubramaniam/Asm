package com.kony.asmdemo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class Utils {

	public static Map<String, MethodNode> map = new HashMap<String, MethodNode>();

	public static String jarName = "D:/ASM/AsmExample/InjectionCode/MyCode.jar";

	public static String OUT_PATH = "D:/ASM/AsmExample/output";
	
	static {
		loadUp(jarName,false);
	}

    public static byte[] readFile(String name) {  
    	try{
    		File file = new File(name);
    		byte [] fileData = new byte[(int)file.length()];
    		DataInputStream dis = new DataInputStream((new FileInputStream(file)));
    		dis.readFully(fileData);
    		dis.close();
    		return fileData;
    	}catch (Exception e) {
    		System.out.println("Exception while reading"+e.toString());
    	}
    	return null;
    }  

	public static final void saveFile(String name,byte [] bytes){
		try{

			File file = new File(OUT_PATH+"/"+name);
			// if file doesnt exists, then create it
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fout= new FileOutputStream(file);

			fout.write(bytes);
			fout.flush();
			fout.close();
		}catch(Exception e){
			System.out.println("Exception while saving file"+e.toString());
		}
	}

	public static void addMethodToMap(String className, MethodNode node){
		String key = className + "::" + node.name + node.desc;		
		System.out.println("Adding method ==> " + key);
		System.out.println(node.name+","+node.desc);
		map.put(key, node);
	}

	public static MethodNode lookupMethod(String className, String methodName, String desc){
		String key = className + "::" + methodName+ desc; 
		System.out.println("Map ::"+map);
		return map.get(key); 
	}

	
	public static void loadUp(String jarFileName ,boolean reset){
		/*try
		{
			ClassNode classNode = new ClassNode();
			InputStream classFileInputStream = null;
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	        try {
	            for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                bos.write(buf, 0, readNum);
	            }
	        }catch(Exception e){
	        	
	        }
			classFileInputStream = new ByteArrayInputStream(bos.toByteArray());
			ClassReader classReader = new ClassReader(new FileInputStream(file));
			classReader.accept(classNode, 0);
			for (Object o : classNode.methods)
			{
				MethodNode mNode = (MethodNode) o;
				String key = classNode.name + "::" + mNode.name + mNode.desc;		
				map.put(key, mNode);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}			*/   
		
		JarEntry entry = null;
		JarInputStream jis = null;
		if(reset)
		{
			map.clear();
		}
		try
		{
			jis = new JarInputStream(new FileInputStream(jarName));
			while ((entry = jis.getNextJarEntry()) != null)			
			{

				System.out.println("Class fileName ==> " + entry.getName());				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte buffer[] = new byte[2048];

				while(true)
				{
					int read = jis.read(buffer);
					if(read < 0)
					{
						break;
					}
					os.write(buffer, 0,read);
				}

				if (os.size() > 0 && entry.getName().endsWith(".class"))
				{
					try
					{
						ClassNode classNode = new ClassNode();
						InputStream classFileInputStream = null;
						classFileInputStream = new ByteArrayInputStream(os.toByteArray());
						ClassReader classReader = new ClassReader(classFileInputStream);
						classReader.accept(classNode, 0);
						for (Object o : classNode.methods)
						{
							MethodNode mNode = (MethodNode) o;
							addMethodToMap(classNode.name, mNode);
						}

					} catch (Exception e)
					{
						System.out.println("entry Name   ===> " + entry.getName());
						e.printStackTrace();
					}			    	
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
}
