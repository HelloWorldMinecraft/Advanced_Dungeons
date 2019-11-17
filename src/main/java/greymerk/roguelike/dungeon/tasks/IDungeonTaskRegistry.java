package greymerk.roguelike.dungeon.tasks;

import greymerk.roguelike.dungeon.DungeonStage;

import java.util.List;

public interface IDungeonTaskRegistry {

    void addTask(IDungeonTask task, DungeonStage stage);

    List<IDungeonTask> getTasks(DungeonStage stage);

}
