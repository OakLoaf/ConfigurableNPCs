package org.lushplugins.configurablenpcs.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Components {

    public static Component fromString(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public static String toString(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
}
