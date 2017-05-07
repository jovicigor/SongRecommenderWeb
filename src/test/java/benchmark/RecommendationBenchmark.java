package benchmark;

import com.songrecommender.dataaccess.external.SpotifyProxyApi;
import com.songrecommender.dataaccess.repository.SongRepository;
import com.songrecommender.rest.controller.Recommendation;
import com.songrecommender.service.MachineLearningWrapper;
import com.songrecommender.service.RecommendationService;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;


public class RecommendationBenchmark {

    private static final String SONG_NAME = "rastafari anthem";

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Recommendation recommendationByOnlyEuclideanDistance() {
        RecommendationService recommendationService = new RecommendationService(
                new SpotifyProxyApi("http://localhost:9090/sp/"),
                new SongRepository(),
                new MachineLearningWrapper("data/Centroids.csv", "data/Min.csv", "data/Max.csv", "data/clusters/"))
                .withNumberOfMatches(1);
        Recommendation recommendationByEuclideanFor = recommendationService.getRecommendationByEuclideanFor(SONG_NAME);
        System.out.println(recommendationByEuclideanFor.getRecommendation().get(0).getName());
        return recommendationByEuclideanFor;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Recommendation recommendationByEuclideanDistanceAndGenreSimilarity() {
        RecommendationService recommendationService = new RecommendationService(
                new SpotifyProxyApi("http://localhost:9090/sp/"),
                new SongRepository(),
                new MachineLearningWrapper("data/Centroids.csv", "data/Min.csv", "data/Max.csv", "data/clusters/"))
                .withNumberOfMatches(1);
        return recommendationService.getRecommendationFor(SONG_NAME);
    }
}
