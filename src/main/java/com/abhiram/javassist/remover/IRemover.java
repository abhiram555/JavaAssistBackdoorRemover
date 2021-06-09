package com.abhiram.javassist.remover;

import com.abhiram.javassist.model.PluginFile;

public interface IRemover {
    void check(PluginFile plugin);
    void remove(PluginFile plugin);
}
