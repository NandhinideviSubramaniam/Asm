package com.kony.asmdemo;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ListIterator;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class TemplateClass1 extends ClassNode{
	
	public TemplateClass1(ClassVisitor cv){
		this.cv = cv;
	}
	
	
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		// TODO Auto-generated method stub
		System.out.println("inside visitMethod::"+access+":::"+name+"::"+desc+"::"+signature+"::"+exceptions);
		return super.visitMethod(access, name, desc, signature, exceptions);
	}
	
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		// TODO Auto-generated method stub
		System.out.println("inside visitField::"+access+":::"+name+"::"+desc+"::"+value);
		return super.visitField(access, name, desc, signature, value);
	}
	
	
	public void visitEnd() {

		super.visitEnd();
		addNewMethod();
		addInstructions_To_First_ExistingMethod();

		System.out.println("-------visitEnd---------");
		try
		{
			accept(cv);					
		} catch (Exception e)
		{
			System.out.println("Error in accept in Class ==>" + this.name);
			e.printStackTrace();
		}

		System.out.println("Done injection Class ==> " + this.name + "\n");

	}

	public void addNewMethod() {
		MethodNode newMNode = Utils.lookupMethod("com/kony/injectionCode/MyCode","newMethodAddedUsingASM","()V");
		if(newMNode != null){
			System.out.println("Adding new method:"+newMNode);
			methods.add(newMNode);
		}
	}

	public void addInstructions_To_First_ExistingMethod() {
		InsnList insToBeInjected = getInstructionsASMCode("com/kony/injectionCode/MyCode","asmCodeofAddingCode");
		// take first method
		MethodNode firstMethod = ((MethodNode)methods.get(1));
		System.out.println("First method Name==="+firstMethod.name);
		InsnList ins =firstMethod.instructions;
		System.out.println("Size of the instructions "+ins.size());
		ListIterator<AbstractInsnNode> i = ins.iterator();
		
		while (i.hasNext()) {						
			AbstractInsnNode in = (AbstractInsnNode) i.next();
			System.out.println("Opcode of the instruction::"+in.getOpcode());
		}
		AbstractInsnNode loc = (AbstractInsnNode) ins.get(0);		
		
		firstMethod.instructions.insert(loc, insToBeInjected);
	}

	public static InsnList getInstructionsASMCode(String className,String methodName){
		/*try{
			Class classInstance = Class.forName(className.replace("/", "."));//, false, ucl);
			Method sample= classInstance.getMethod(methodName);
			InsnList ins = (InsnList)sample.invoke(null, null);
			
			return ins;
		}catch(Exception e){

		}
		return null;
*/
		URLClassLoader ucl = null ;
		File templateJar ;
		try{


			templateJar = new File("");
			URL myJarFile = new URL("jar","","file:"+templateJar.getAbsolutePath()+"!/");

			//URL url=new URL("jar:file:/C:\\android-sdk-windows-1.6_r1laest\\platforms\\android-17!/");
			//ucl = new URLClassLoader(new URL[]{myJarFile});

			URLClassLoader systemLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();

			Class systemClass = URLClassLoader.class;
			Method systemMethod = systemClass.getDeclaredMethod("addURL",new Class[] {URL.class});
			systemMethod.setAccessible(true);
			systemMethod.invoke(systemLoader, new Object[]{myJarFile});

			Class classInstance = Class.forName(className.replace("/", "."));//, false, ucl);
			Method sample= classInstance.getMethod(methodName);
			InsnList ins = (InsnList)sample.invoke(null, null);
			
			return ins;
		}catch(Exception e){

		}finally{

			if(ucl != null){
				try {
					((Closeable) ucl).close();
				} catch (IOException e) {
					
				}
			}
		}
		return null;

	}
}
