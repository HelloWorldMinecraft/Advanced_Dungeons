package greymerk.roguelike.worldgen.filter;

import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.worldgen.IBounded;
import greymerk.roguelike.worldgen.IWorldEditor;

import java.util.Random;

public interface IFilter {

    void apply(IWorldEditor editor, Random rand, ITheme theme, IBounded box);

}
