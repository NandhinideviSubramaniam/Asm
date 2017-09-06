package com.kony.asmdemo;

import java.io.IOException;
import java.util.Collection;
import com.kony.utilities.*;



public class ASM_Main {

	  public static void main(String[] args) throws IOException{
		  CodeInjection();
	  }
	  
	  public static void CodeInjection() throws IOException {
		  
			try {
			/*	System.out.println("demoInjection=======================init");
				byte[] classdata =  Utils.readFile("D:/ASM/AsmExample/res/HelloClass.class");
				
				System.out.println("classdata=======================" + classdata.length);
				
				byte[] retVal = null;
				ByteArrayInputStream bais = new ByteArrayInputStream(classdata);
				ClassReader cr = new ClassReader(bais);
				
				ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				ClassVisitor cv = null;
				cv = new TemplateClass1(new CheckClassAdapter(cw, false));
				cr.accept(cv, 0);
				retVal = cw.toByteArray();
				System.out.println("retVal=======================" + retVal.length);
				Utils.saveFile("TestJava.jar", retVal);
				System.out.println("demoInjectionb=======================done");*/
				
				
				String jarDir = "D:/ASM/AsmExample/res";
				String destDir = "D:/ASM/AsmExample/output";
				Collection<String> jarFileList = GenerateFileList.getFileList(jarDir, ".jar");
				for (String jarName : jarFileList)
				{
				    System.out.println("About to Inject in Jar file " + jarName + " in " + destDir);
					JarUtil.CodeInjectJar(jarName, FileUtil.generateNewFileName(jarName, destDir));
					System.out.println("DONE Inject in Jar file " + jarName);
				}
			} catch (Exception e) {
				System.out.println("Exception in demoInjection  " + e.toString());
			}
	  }
}
