package com.tilmenk.teamService.model.externalApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExternalPokeApiResponse {
    private Sprites sprites;

    @Data
    public static class OfficialArtwork {
        private String front_default;
    }

    @Data
    public static class Other {
        @JsonProperty("official-artwork")
        private OfficialArtwork official_artwork;
    }

    @Data
    public static class Sprites {
        private String front_default;

        private Other other;
    }
}
