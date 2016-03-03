// Bukkit Plugin "VirtualPack" by Siguza
// The license under which this software is released can be accessed at:
// http://creativecommons.org/licenses/by-nc-sa/3.0/

package net.drgnome.virtualpack.components;

import java.util.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v#MC_VERSION#.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import net.minecraft.server.v#MC_VERSION#.*;
import net.drgnome.virtualpack.util.*;

public class VInv implements IInventory
{
    protected ItemStack[] contents = new ItemStack[0];
    private long lastUpdate;
    private int _maxStack = 64;
    private String _name = "";

    public VInv(int rows)
    {
        contents = new ItemStack[rows * 9];
        lastUpdate = System.currentTimeMillis();
    }

    public VInv(int rows, String data[])
    {
        this(rows);
        for(int i = 0; i < Util.min(data.length, contents.length); i++)
        {
            contents[i] = Util.stringToItemStack(data[i]);
        }
    }

    public VInv(int rows, ItemStack[] items)
    {
        this(rows);
        int max = (contents.length > items.length) ? items.length : contents.length;
        for(int i = 0; i < max; i++)
        {
            contents[i] = items[i];
        }
    }

    public String[] save()
    {
        String string[] = new String[contents.length];
        for(int i = 0; i < contents.length; i++)
        {
            string[i] = Util.itemStackToString(contents[i]);
        }
        return string;
    }

    public void clear()
    {
        contents = new ItemStack[contents.length];
    }

    public void resize(int size)
    {
        if(contents.length != size)
        {
            contents = Arrays.copyOf(contents, size);
        }
    }

    public int getSize()
    {
        return contents.length;
    }

    public ItemStack getItem(int slot)
    {
        return (slot < contents.length) && (slot >= 0) ? contents[slot] : null;
    }

    public ItemStack splitStack(int slot, int size)
    {
        if((slot < contents.length) && (contents[slot] != null))
        {
            if(contents[slot].count <= size)
            {
                ItemStack item = Util.copy_old(contents[slot]);
                setItem(slot, null);
                return item;
            }
            ItemStack item = contents[slot].#FIELD_ITEMSTACK_3#(size); // Derpnote
            if(contents[slot].count <= 0)
            {
                setItem(slot, null);
            }
            return item;
        }
        return null;
    }

    public ItemStack splitWithoutUpdate(int slot)
    {
        if((slot < contents.length) && (contents[slot] != null))
        {
            ItemStack item = Util.copy_old(contents[slot]);
            contents[slot] = null;
            return item;
        }
        return null;
    }

    public void setItem(int slot, ItemStack item)
    {
        contents[slot] = item;
        if((item != null) && (item.count > getMaxStackSize()))
        {
            item.count = getMaxStackSize();
        }
        lastUpdate = System.currentTimeMillis();
    }

    public long getLastUpdate()
    {
        return lastUpdate;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return new ChatComponentText(_name);
    }

    public boolean hasCustomName()
    {
        return true;
    }

    public int getMaxStackSize()
    {
        return _maxStack;
    }

    public void setMaxStackSize(int i)
    {
        _maxStack = i;
    }

    public boolean #FIELD_IINVENTORY_1#(EntityHuman entityhuman) // Derpnote
    {
        return true;
    }

    public List<HumanEntity> getViewers()
    {
        return new ArrayList<HumanEntity>();
    }

    public InventoryHolder getOwner()
    {
        return null;
    }

    public Location getLocation()
    {
        return null;
    }

    public ItemStack[] getContents()
    {
        return contents;
    }

    public boolean #FIELD_IINVENTORY_3#(int slot, ItemStack item)
    {
        return true;
    }

    public int getProperty(int i)
    {
        return 0;
    }

    public void #FIELD_IINVENTORY_5#(int i, int j)
    {
    }

    public int #FIELD_IINVENTORY_6#()
    {
        return 0;
    }

    public void #FIELD_IINVENTORY_7#()
    {
        clear();
    }

    public void update()
    {
    }

    public void closeContainer(EntityHuman human)
    {
    }

    public void startOpen(EntityHuman human)
    {
    }

    public void onOpen(CraftHumanEntity paramCraftHumanEntity)
    {
    }

    public void onClose(CraftHumanEntity paramCraftHumanEntity)
    {
    }
}
