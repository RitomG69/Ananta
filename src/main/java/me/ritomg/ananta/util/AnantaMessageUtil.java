package me.ritomg.ananta.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;

public class AnantaMessageUtil extends Util{

    public static String Ananta = ChatFormatting.GRAY + "[" + ChatFormatting.DARK_PURPLE + "Ananta" + ChatFormatting.LIGHT_PURPLE+ "Client" + ChatFormatting.GRAY + "] " + ChatFormatting.RESET;

    public static void sendClientPrefixMessage(String message, ChatFormatting messageFormatting) {
        TextComponentString string1 = new TextComponentString(Ananta + messageFormatting + message);
        if (mc.player != null && mc.world !=null)
            mc.player.sendMessage(string1);
    }

    public static void sendClientPrefixMessage(String message) {
        TextComponentString string1 = new TextComponentString(Ananta + ChatFormatting.GRAY + message);
        if (mc.player != null && mc.world !=null)
            mc.player.sendMessage(string1);
    }

    public static void sendClientRawMessage(String message, ChatFormatting messageFormatting) {
        TextComponentString string = new TextComponentString(messageFormatting + message);
        if (mc.player != null && mc.world !=null)
            mc.player.sendMessage(string);
    }

    public static void sendServerMessage(String message, ChatFormatting formatting) {
        if (mc.player != null && mc.world !=null)
            player.connection.sendPacket(new CPacketChatMessage(formatting + message));
    }
    public static void sendServerMessage(String message) {
        if (mc.player != null && mc.world !=null)
            player.connection.sendPacket(new CPacketChatMessage(ChatFormatting.GRAY + message));
    }

}