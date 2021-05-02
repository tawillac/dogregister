package shared.domain;

import lombok.*;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@State(Scope.Benchmark)
public class Dog {

    @Id
    @Column(name="name")
    private String name = "";

    @Column(name="race")
    private String race = "";

    @Column(name="owner")
    private String owner = "";

    @Column(name="birthday")
    private LocalDate birthday = LocalDate.now();

}
