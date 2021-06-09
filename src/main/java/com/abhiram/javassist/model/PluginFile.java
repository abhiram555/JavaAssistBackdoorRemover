package com.abhiram.javassist.model;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginFile {
    private final File plugin;
    private final ArrayList<ClassNode> nodes = new ArrayList<>();
    private final ArrayList<File> other_files = new ArrayList<>();

    public PluginFile(File plugin){
        this.plugin = plugin;

        try {
            this.loadNodes(plugin);
        }catch (Exception ez){
            System.out.println("There was an error while loading classes of " + plugin.getName());
        }
    }


    public void loadNodes(File file) throws Exception
    {
        nodes.clear();
        other_files.clear();

        try {
            JarFile jar = new JarFile(file);
            Enumeration<JarEntry> enumeration = jar.entries();
            while(enumeration.hasMoreElements()) {
                JarEntry next = enumeration.nextElement();
                if(next.getName().endsWith(".class")) {
                    ClassReader reader = new ClassReader(jar.getInputStream(next));
                    ClassNode node = new ClassNode();
                    reader.accept(node, ClassReader.EXPAND_FRAMES);
                    nodes.add(node);
                }else {
                    java.util.jar.JarEntry loli = next;
                    if(!loli.getName().contains("/")) {
                        File tempcache = new File("./" + plugin.getName() + "temp/");
                        java.io.File cache = new java.io.File(tempcache, loli.getName());
                        tempcache.mkdirs();
                       cache.createNewFile();
                        java.io.InputStream is = jar.getInputStream(loli);
                        java.io.FileOutputStream fos = new java.io.FileOutputStream(cache);
                        while (is.available() > 0) {
                            fos.write(is.read());
                        }
                        fos.close();
                        is.close();
                        other_files.add(cache);
                    }
                }
            }
            jar.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<ClassNode> getNodes(){
        return this.nodes;
    }

    public ArrayList<File> getFiles(){
        return this.other_files;
    }

    public File getPlugin(){
        return this.plugin;
    }
}
