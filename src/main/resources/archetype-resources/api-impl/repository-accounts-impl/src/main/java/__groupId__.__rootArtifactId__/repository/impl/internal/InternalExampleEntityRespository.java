#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.repository.impl.internal;

import ${groupId}.${rootArtifactId}.repository.impl.entities.ExampleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Internal repository implementing spring data crud repository.
 *
 * @author vedad
 */
@Repository
public interface InternalExampleEntityRespository extends PagingAndSortingRepository<ExampleEntity, UUID> {
}
