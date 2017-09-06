package com.kony.injectionCode;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class MyCode {

	
	public void newMethodAddedUsingASM(){
		System.out.println("I am injecting my code");
	}
	
	public static InsnList asmCodeofAddingCode(){
		InsnList insList = new InsnList();
		LabelNode ten = new LabelNode();
		insList.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		insList.add(new LdcInsnNode("This sysout is added to existing method using ASM"));
		insList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"));
		insList.add(new LabelNode(ten.getLabel()));
		return insList;

	}
	
}
