package com.musicapp.serverapimusicapp.api.output;

import com.musicapp.serverapimusicapp.dto.SongDTO;

import java.util.List;

public class RecommendSongOutput {
    List<SongDTO> recommendedSongsRate;
    List<SongDTO> recommendedSongsView;

    public List<SongDTO> getRecommendedSongsRate() {
        return recommendedSongsRate;
    }

    public void setRecommendedSongsRate(List<SongDTO> recommendedSongsRate) {
        this.recommendedSongsRate = recommendedSongsRate;
    }

    public List<SongDTO> getRecommendedSongsView() {
        return recommendedSongsView;
    }

    public void setRecommendedSongsView(List<SongDTO> recommendedSongsView) {
        this.recommendedSongsView = recommendedSongsView;
    }
}
