// Bukkit Plugin "VirtualPack" by Siguza
// The license under which this software is released can be accessed at:
// http://creativecommons.org/licenses/by-nc-sa/3.0/

package net.drgnome.virtualpack.components;

import net.minecraft.server.v#MC_VERSION#.*;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v#MC_VERSION#.inventory.CraftItemStack;
import net.drgnome.virtualpack.util.Config;

public class VFurnace extends ContainerFurnace implements VGUI
{
    protected EntityPlayer player;
    protected TileEntityFurnace _data;
    private final boolean _readonly;

    public VFurnace(EntityPlayer player, TileEntityFurnace data, boolean canEdit)
    {
        super(player.inventory, data);
        this.checkReachable = false;
        this._data = data;
        this.player = player;
        this._readonly = !canEdit;
    }

    public final ItemStack #FIELD_CONTAINER_11#(int slot, int mouse, #F_INVCLICK_META# meta, EntityHuman human)
    {
        ItemStack item;
        if(allowClick(slot, mouse, meta, human))
        {
            item = super.#FIELD_CONTAINER_11#(slot, mouse, meta, human);
        }
        else
        {
            item = human.inventory.getCarried();
            update();
        }
        return item;
    }

    public boolean allowClick(int slot, int mouse, #F_INVCLICK_META# meta, EntityHuman human)
    {
        if(_readonly)
        {
            return false;
        }
        if(meta == #F_INVCLICK_QUICK_MOVE#)
        {
            if(slot >= _data.getSize())
            {
                return isItemAllowed(human, human.inventory.getItem(toInventorySlot(slot - _data.getSize())));
            }
            return true;
        }
        else if(meta == #F_INVCLICK_SWAP#)
        {
            return slot >= _data.getSize() || isItemAllowed(human, human.inventory.getItem(toInventorySlot(27 + mouse))); // "mouse"
        }
        else if((slot >= 0) && (slot < _data.getSize()))
        {
            return isItemAllowed(human, human.inventory.getCarried());
        }
        return true;
    }

    protected int toInventorySlot(int slot)
    {
        return (slot >= 27) ? (slot - 27) : (slot + 9);
    }

    private boolean isItemAllowed(EntityHuman human, ItemStack item)
    {
        return !Config.isBlacklisted(human.world.getWorld().getName(), (Player)human.getBukkitEntity(), "store", CraftItemStack.asBukkitCopy(item));
    }

    protected void update()
    {
        player.updateInventory(player.activeContainer);
    }
}
