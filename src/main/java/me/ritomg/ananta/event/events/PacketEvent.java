package me.ritomg.ananta.event.events;

import me.ritomg.ananta.event.AnantaMainEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends AnantaMainEvent {

    private final Packet packet;

    public PacketEvent(Packet packet) {
        super();
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
    public static class Send extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

}
