package com.kony.utilities;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

import com.kony.asmdemo.TemplateClass1;

public class Injector implements ClassFileTransformer{

	public Injector(String args) {

	}

	public static void premain(String agentArgs, Instrumentation inst) {
		// Add a new Instance of ourself as a Transformer. This will enable
		// the JVM to call our method transform() before classes are loaded.
		inst.addTransformer(new Injector(agentArgs));
	}

	public void inject(File file)
	{
		byte[] retVal = null;
		ClassWriter cw = new ClassWriter(1);
		ClassVisitor ca = new TemplateClass1(new CheckClassAdapter(cw));
		ClassReader cr = null;
		try{
			cr = new ClassReader(new FileInputStream(file));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		cr.accept(ca, 0);
		retVal = cw.toByteArray();
		DataOutputStream fos = null;
		try{
			fos = new DataOutputStream(new FileOutputStream(file));
			fos.write(retVal);
			fos.flush();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public byte[] inject(InputStream fos) throws Exception
	{
		byte[] retVal = null;
		ClassReader cr = new ClassReader(fos);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor ca = new TemplateClass1(new CheckClassAdapter(cw));
		cr.accept(ca, 0);
		retVal = cw.toByteArray();		
		return retVal;
	
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		byte[] retVal = null;
		ClassWriter cw = new ClassWriter(1);
//		ClassVisitor ca = new KonyPolicyAdapter(cw);
		ClassReader cr = new ClassReader(classfileBuffer);
//		cr.accept(ca, 0);
		retVal = cw.toByteArray();

		return retVal;
	}
	
}

