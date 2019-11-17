package greymerk.roguelike.dungeon;

import greymerk.roguelike.dungeon.base.IDungeonRoom;
import greymerk.roguelike.worldgen.IBounded;

import java.util.List;

public interface ILevelLayout {

    List<IBounded> getBoundingBoxes();

    List<DungeonNode> getNodes();

    List<DungeonTunnel> getTunnels();

    DungeonNode getStart();

    DungeonNode getEnd();

    boolean hasEmptyRooms();

    DungeonNode getBestFit(IDungeonRoom room);

}
