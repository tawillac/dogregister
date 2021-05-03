package shared.domain;

import lombok.*;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@State(Scope.Benchmark)
public class Dog {

    private String name = "";

    private String race = "";

    private String owner = "";

    private LocalDate birthday = LocalDate.now();

}
