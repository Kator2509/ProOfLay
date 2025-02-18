package org.graphic.test;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public abstract class MemorySpace implements MemoryInterface
{
    private Yaml yaml = new Yaml();
    protected FileInputStream fileInputStream;
    private LinkedHashMap<String, Object> data;
    private String pathFile;
    public MemorySpace()
    {
        this.pathFile = null;
    }

    public MemorySpace(@NotNull String pathFile)
    {
        this.pathFile = pathFile;
    }

    @Override
    public void setData()
    {
        try {
            this.fileInputStream = new FileInputStream(this.pathFile);
            this.data = yaml.load(this.fileInputStream);
        } catch (FileNotFoundException e)
        {
            System.out.println(ChatColor.AQUA + "[ProFlay] - Can't found the file. Check path to file: " + this.pathFile);
        }
    }

    @Override
    public void setData(@NotNull String pathFile) {
        try{
            this.fileInputStream = new FileInputStream(pathFile);
            this.data = yaml.load(this.fileInputStream);
            Preconditions.checkArgument(pathFile != null, "[ProOfLay] You change pathFile on null. That can cause a problem.");
        } catch (FileNotFoundException e)
        {
            System.out.println(ChatColor.AQUA + "[ProOfLay] Can't found tha file. Path to file may be path have illegal argument: " + pathFile);
        }
    }

    @Override
    public void resetData()
    {
        try {
            this.fileInputStream = new FileInputStream(this.pathFile);
            this.data = yaml.load(this.fileInputStream);
        } catch (FileNotFoundException e)
        {
            System.out.println(ChatColor.AQUA + "[ProFlay] Can't found the file. Maybe file migrate: " + this.pathFile);
        }
    }

    private String[] getRequest(@NotNull String path)
    {
        return path.split("\\.");
    }

    @Override
    public Object getObject(@NotNull String path) {
        Preconditions.checkArgument(this.data != null, ChatColor.AQUA + "[ProFlay] Data is null. Use setData or resetData. Check register of configuration: " + this.pathFile);
        Object var1 = this.data.get(getRequest(path)[0]), var2 = null;
        int i = 1;
        if(getRequest(path).length > 1) {
            do {
                var2 = ((LinkedHashMap<String, Object>) var1).get(getRequest(path)[i]);
                var1 = var2;
                i++;
            } while (i < getRequest(path).length);
        }
        Preconditions.checkArgument(var2 != null || var1 != null, ChatColor.AQUA + "[ProFlay] Argument is null. Check your path or config: " + path);
        return getRequest(path).length > 1 ? var2 : var1;
    }

    @Override
    public List<Object> getObjectsArray(@NotNull String path) {
        return ((List<Object>) getObject(path));
    }

    @Override
    public String getKeySet() {
        return null;
    }

    @Override
    public String getString(@NotNull String path) {
        return (String) getObject(path);
    }

    @Override
    public List<String> getStringArray(@NotNull String path) {
        return (List<String>) getObject(path);
    }

    @Override
    public Character getCharacter(@NotNull String path) {
        Preconditions.checkArgument(getObject(path).toString().length() > 1, ChatColor.AQUA + "[ProFlay] getCharacter detect a illegal request: " + path);
        Preconditions.checkArgument(getObject(path).toString().length() > 1, ChatColor.AQUA + "[ProFlay] Illegal object: " + getObject(path).toString());
        return (Character) (getObject(path).toString().length() <= 1 ? getObject(path).toString().charAt(0) : null);
    }

    @Override
    public List<Character> getCharacterArray(@NotNull String path) {
        return (List<Character>) getObject(path);
    }

    @Override
    public Boolean getBoolean(@NotNull String path) {
        return (Boolean) getObject(path);
    }

    @Override
    public List<Boolean> getBooleanArray(@NotNull String path) {
        return (List<Boolean>) getObject(path);
    }

    @Override
    public Integer getInteger(@NotNull String path) {
        return (Integer) getObject(path);
    }

    @Override
    public List<Integer> getIntegerArray(@NotNull String path) {
        return (List<Integer>) getObject(path);
    }

    @Override
    public Double getDouble(@NotNull String path) {
        return (Double) getObject(path);
    }

    @Override
    public List<Double> getDoubleArray(@NotNull String path) {
        return (List<Double>) getObject(path);
    }

    @Override
    public List<String> getKeySet(@Nullable String path) {
        Object var1 = this.data.get(getRequest(path)[0]), var2;
        int i = 1;
        do {
            var2 = ((LinkedHashMap<String, Object>) var1).get(getRequest(path)[i]);
            var1 = var2;
            i++;
        } while (i < getRequest(path).length);
        return ((LinkedHashMap<String, Object>) var2).keySet().stream().toList();
    }

    @Override
    public void setKey(@NotNull String path, String argument) {

    }
}
