package minecraftguitemplate.me.events;

abstract public class Cancellable extends Event {
    private boolean cancel;

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isCancelled() {
        return cancel;
    }
}
