package me.xemor.superheroes2.skills.implementations;

import me.xemor.superheroes2.HeroHandler;
import me.xemor.superheroes2.skills.Skill;
import me.xemor.superheroes2.skills.skilldata.BlockDropsData;
import me.xemor.superheroes2.skills.skilldata.SkillData;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlockDropsSkill extends SkillImplementation{
    public BlockDropsSkill(HeroHandler heroHandler) {
        super(heroHandler);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Collection<SkillData> skillDatas = heroHandler.getSuperhero(player).getSkillData(Skill.BLOCKDROPS);
        World world = player.getWorld();
        Block block = e.getBlock();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        for (SkillData skillData : skillDatas) {
            BlockDropsData blockDropsData = (BlockDropsData) skillData;
            e.setDropItems(!blockDropsData.shouldReplaceDrops());
            if (mainHand.hasItemMeta() && mainHand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH) && !blockDropsData.shouldReplaceDrops()) {
                continue;
            }
            Collection<ItemStack> drops = blockDropsData.getDrops(block.getType());
            for (ItemStack itemStack : drops) {
                world.dropItemNaturally(block.getLocation(), itemStack);
            }
        }
    }
}
