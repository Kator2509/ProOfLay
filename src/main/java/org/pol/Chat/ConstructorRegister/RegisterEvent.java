package org.pol.Chat.ConstructorRegister;

public class RegisterEvent
{
    String symbol, permission, format, name;

    public RegisterEvent(String name, String symbol, String permission, String format)
    {
        this.name = name;
        this.symbol = symbol;
        this.permission = permission;
        this.format = format;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public String getPermission()
    {
        return this.permission;
    }

    public String getFormat()
    {
        return this.format;
    }

    public void setSymbol(char symbol)
    {
        this.symbol = String.valueOf(symbol);
    }

    public void setPermission(String permission)
    {
        this.permission = permission;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }
}
