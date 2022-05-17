#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.impl.internal;

import ${groupId}.${rootArtifactId}.accounts.impl.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Internal repository implementing spring data crud repository.
 *
 * @author vedad
 */
@Repository
public interface InternalUserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
}
