package shared.domain;

import lombok.*;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
@State(Scope.Benchmark)
public class DogReport {

    private Map<String, Integer> numberOfDogsPerRace = new HashMap<>();
    private float averageNumberOfDogsPerOwner = 0;


    public void addRaceCount(String race, Integer numberOfDog) {
        numberOfDogsPerRace.put(race, numberOfDog);
    }

    public void setRaceCount(Map<String, Integer> numberOfDogsPerRace) {
        this.numberOfDogsPerRace = numberOfDogsPerRace;
    }



}
