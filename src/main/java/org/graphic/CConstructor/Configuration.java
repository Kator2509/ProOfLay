package org.graphic.CConstructor;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URLConnection;

public class Configuration extends MemorySpace implements ConfigInterface
{
    protected Plugin plugin;
    private String resourcePath, outPath, pathDir;

    public Configuration()
    {
        this.plugin = null;
        this.resourcePath = null;
        this.outPath = null;
        /*
         * This constructor extends MemorySpace and use there method. Implements ConfigInterface.
         * You can use Interface for create your methods and logic.
         *
         * plugin is empty. Need create access to your main Plugin class or call this constructor in main class.
         * createConfig is empty. Need to change a resourcePath for create and call a method.
         * setData is empty. You can fill this parm or don't change him. Default system create a config in plugins/<pluginName>/resourcePath
         * */
    }

    public Configuration(@NotNull Plugin plugin, @NotNull String resourcePath, @NotNull String outPath)
    {
        super(outPath);
        this.plugin = plugin;
        this.resourcePath = resourcePath;
        this.outPath = (outPath != null ? outPath : "plugins/" + plugin.getName() + "/" + resourcePath);
        this.pathDir = (outPath.substring(0, outPath.lastIndexOf(47) >= 0 ? outPath.lastIndexOf(47) : 0));
        createConfig(false);
        setData();
    }

    public String getConfigPath()
    {
        return this.outPath;
    }

    @Override
    public void createConfig(@NotNull boolean reset) {
        if(this.isExistsConfig() || reset) {
            URLConnection con = this.getURLConnection();
            if (con != null) {
                con.setUseCaches(false);
                InputStream var = this.getInputStream(con);
                if (!new File(this.pathDir).exists()) {
                    new File(this.pathDir).mkdirs();
                }
                try {
                    OutputStream var3 = new FileOutputStream(this.outPath);
                    int len;
                    byte[] var2 = new byte[1024];

                    while ((len = var.read(var2)) > 0) {
                        var3.write(var2, 0, len);
                    }

                    var3.close();
                    var.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public boolean isExistsConfig() {
        if(!new File(this.outPath).exists())
        {
            return true;
        }
        return false;
    }

    protected InputStream getInputStream(URLConnection urlConnection) {
        try {
            return urlConnection.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    protected ClassLoader getClassLoader() {
        return this.plugin.getClass().getProtectionDomain().getClassLoader();
    }

    protected URLConnection getURLConnection() {
        try {
            return getClassLoader().getResource(this.resourcePath).openConnection();
        } catch (IOException e) {
            return null;
        }
    }
}
