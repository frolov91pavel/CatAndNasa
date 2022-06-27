import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

public class Cat {
    private final String id;
    private final String text;
    private final String type;
    private final String user;
    private final int upvotes;

    public Cat(
            @JsonProperty("id") String id,
            @JsonProperty("text") String text,
            @JsonProperty("type") String type,
            @JsonProperty("user") String user,
            @JsonProperty("upvotes") int upvotes
    ) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.user = user;
        this.upvotes = upvotes;
    }

}
