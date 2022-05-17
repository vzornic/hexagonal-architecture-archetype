#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.repository.impl.entities;

import ${groupId}.${rootArtifactId}.domain.ExampleDomainModel;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * Example entity representation.
 *
 * @author vedad
 */
@Data
@Entity
@Table(name = "example_entity")
public class ExampleEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    /**
     * Returns domain model representation of this example entity.
     *
     * @return Example domain model
     */
    public ExampleDomainModel toDomainModel() {
        return ExampleDomainModel.builder()
                .id(this.id.toString())
                .description(this.description)
                .name(this.name)
                .build();
    }
}
