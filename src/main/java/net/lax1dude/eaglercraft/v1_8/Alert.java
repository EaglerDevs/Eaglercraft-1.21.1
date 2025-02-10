package net.lax1dude.eaglercraft.v1_8;

import org.teavm.jso.JSBody;

public class Alert {
    @JSBody(params = { "message" }, script = "confirm(\"Do you want to reload the page to apply changes?\") ? location.reload() : alert(\"Changes will be applied on the next visit.\")")
    public static native void alert(String message);
}
