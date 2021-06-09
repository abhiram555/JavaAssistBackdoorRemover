package com.abhiram.javassist.util;

import com.abhiram.javassist.model.PluginFile;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarUtil {
    public static void save(File jar, PluginFile plugin,boolean removejassist) {
        ArrayList<ClassNode> node = plugin.getNodes();
        try {
            if(!jar.exists()){
                jar.createNewFile();
            }
            JarOutputStream output = new JarOutputStream(new FileOutputStream(jar));

            for(ClassNode element : node) {
                if(removejassist) {
                    if (!element.name.contains("javassist")) {
                        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                        element.accept(writer);
                        output.putNextEntry(new JarEntry(element.name.replaceAll("\\.", "/") + ".class"));
                        output.write(writer.toByteArray());
                    }
                }else {
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    element.accept(writer);
                    output.putNextEntry(new JarEntry(element.name.replaceAll("\\.", "/") + ".class"));
                    output.write(writer.toByteArray());
                }
            }

            for(File entry : plugin.getFiles()){
                output.putNextEntry(new JarEntry(entry.getName()));
                FileInputStream fis = new FileInputStream(entry);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                entry.delete();
                entry.getParentFile().delete();
            }
            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
