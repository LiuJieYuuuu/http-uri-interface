package com.httpuri.iagent.scan;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassPathBeanScanner {

    public void scannerPackages(Set<Class> set, String... basePackages) {
        for(String packages : basePackages){
            try {
                String path = this.getClass().getResource("/").getPath();
                String packagePath = packages.replace(".", File.separator);
                File file = new File(path + packagePath);
                File[] files = file.listFiles();
                for(File f : files){
                    String classPath = packages + "." + f.getName().replace(".class","");
                    if(f.isDirectory()){
                        throw new IllegalArgumentException("Includes directories under the package path:" + packages);
                    }
                    set.add(Class.forName(classPath));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
