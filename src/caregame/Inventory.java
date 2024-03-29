/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.item.*;
import caregame.item.resource.Resource;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Inventory {
//    public int seeds;

//    public int fertilizer;
//    public int water;
    public List<Item> items = new ArrayList<Item>();
    public int maxSlots = -1; //-1 = infinity

    public Inventory(int maxSlots) {
        this.maxSlots = maxSlots;
    }
    public Inventory() {
    }

    public boolean add(Item item) {
        return add(items.size(), item);
    }
    
    public boolean add(int slot, Item item) {
        if (item instanceof ResourceItem) {
            ResourceItem toTake = (ResourceItem) item;
            ResourceItem has = findResource(toTake.resource);
            if (has == null) {
                if (maxSlots == -1 || items.size() < maxSlots) {
                    items.add(slot, toTake);
                    return true;
                }
            } else {
                has.count += toTake.count;
                return true;
            }
        } else {
            if (maxSlots == -1 || items.size() < maxSlots) {
                items.add(slot, item);
                return true;
            }
        }
        return false;
    }

    private ResourceItem findResource(Resource resource) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof ResourceItem) {
                ResourceItem has = (ResourceItem) items.get(i);
                if (has.resource == resource) {
                    return has;
                }
            }
        }
        return null;
    }

    public boolean hasResources(Resource r, int count) {
        ResourceItem ri = findResource(r);
        if (ri == null) {
            return false;
        }
        return ri.count >= count;
    }

    public boolean removeResource(Resource r, int count) {
        ResourceItem ri = findResource(r);
        if (ri == null) {
            return false;
        }
        if (ri.count < count) {
            return false;
        }
        ri.count -= count;
        if (ri.count <= 0) {
            items.remove(ri);
        }
        return true;
    }

    public int count(Item item) {
        if (item instanceof ResourceItem) {
            ResourceItem ri = findResource(((ResourceItem) item).resource);
            if (ri != null) {
                return ri.count;
            }
        } else {
            int count = 0;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).matches(item)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    public int count(Resource resource) {
        ResourceItem ri = findResource(resource);
        if (ri != null) {
            return ri.count;
        }
        return 0;
    }
    
    public void write(DataOutputStream out) throws IOException {
//        out.writeInt(OPCODES.opcodes.get(getClass()));
        
        out.writeInt(items.size());
        for (int i = 0; i < items.size(); i++) {
            items.get(i).write(out);
        }
    }
    
    public void read(DataInputStream in) throws IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            int type = in.readInt();
            if (type == OPCODES.OP_ITEM_FURNITURE) {
                FurnitureItem fi = new FurnitureItem(null);
                fi.read(in);
                add(0, fi);
                System.out.println("READ FURNITURE ITEM");
            } else if (type == OPCODES.OP_ITEM_RESOURCE) {
                ResourceItem ri = new ResourceItem(Resource.wood);
                ri.read(in);
                add(0, ri);
                System.out.println("READ RESORUCE ITEM");
            } else if (type == OPCODES.OP_ITEM_TOOL) {
                ToolItem ti = new ToolItem(ToolType.hoe, 0);
                ti.read(in);
                add(0, ti);
                System.out.println("READ TOOL ITEM");
            }
        }
    }

    public int remaining() {
        return maxSlots == -1 ? -1 : maxSlots - items.size();
    }

    public boolean isInfinite() {
        return maxSlots == -1;
    }
}
