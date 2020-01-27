package chatBot;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Message {
    @SerializedName("messages")
    private Map<String, String> message;

    public Message(Map<String, String> message) {
        this.message = message;
    }
}
