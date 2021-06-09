package com.abhiram.javassist;

import com.abhiram.javassist.model.PluginFile;
import com.abhiram.javassist.remover.IRemover;
import com.abhiram.javassist.remover.impl.malware.HostFlowRemover;

import java.io.File;
import java.util.ArrayList;


public class AntiBackdoor {
    private static AntiBackdoor instance;
    private final ArrayList<PluginFile> plugins = new ArrayList<>();
    private final ArrayList<IRemover> removers = new ArrayList<>();

    public void init()  {
        File folder = new File("plugins");
        if(!folder.exists()){
            System.out.println("Plugins folder not found exiting....");
            System.exit(-1);
        }

        if(folder.listFiles().length == 0){
            System.out.println("No Plugins found on plugins folder");
            return;
        }

        for(File pl : folder.listFiles()){
            if(!pl.getName().endsWith(".jar")){
                System.out.println(pl.getName() + " is not an jar file...");
            }else {
                plugins.add(new PluginFile(pl));
            }
        }

        this.registerRemover(new HostFlowRemover());

        this.check();
    }

    public void check(){
        for(PluginFile pl : this.plugins){
            for(IRemover remover : this.removers){
                remover.check(pl);
            }
        }
    }
    public void registerRemover(IRemover remover){
        this.removers.add(remover);
    }

    public static AntiBackdoor getInstance(){
        if(instance == null){
            instance = new AntiBackdoor();
        }

        return instance;
    }
}
