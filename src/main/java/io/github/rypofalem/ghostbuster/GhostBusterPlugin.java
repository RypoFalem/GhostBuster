/*
 * Copyright (c) 2017 RypoFalem
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.rypofalem.ghostbuster;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GhostBusterPlugin extends JavaPlugin implements Listener, CommandExecutor {
    boolean active = true;

    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event){
        if(!active) return;
        if(event.getPlayer().isOnGround()) return;
        if(event.getPlayer().isFlying()) return;
        Block block = event.getBlock();
        event.getPlayer().sendBlockChange(block.getLocation(), block.getType(), block.getData());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        if(args != null && args.length > 0){
            if(args[0].equalsIgnoreCase("toggle")){
                active = !active;
                for(Player p: Bukkit.getOnlinePlayers()){
                    if(p.isOp()){
                        p.sendMessage(String.format("Ghost Block protection is now %s", active ? "active" : "disabled"));
                    }
                }
                return true;
            }
            return false;
        }
        Player player = (Player)sender;
        ItemStack pic = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = pic.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        pic.setItemMeta(meta);
        player.getInventory().addItem(pic);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60000, 2, true, false), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60000, 1, true, false), true);
        return true;
    }
}