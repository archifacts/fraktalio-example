package org.archifacts.example.fraktalio.descriptor;

import static org.archifacts.example.fraktalio.descriptor.FraktalioDescriptors.BuildingBlockDescriptors.QueryDescriptor;

import java.util.stream.Stream;

import org.archifacts.core.descriptor.TargetBasedArtifactRelationshipDescriptor;
import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationshipRole;
import org.archifacts.core.model.BuildingBlock;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructorCall;

final class QueryQuerierDescriptor implements TargetBasedArtifactRelationshipDescriptor {

	private static final ArtifactRelationshipRole ROLE = ArtifactRelationshipRole.of("queries");

	@Override
	public ArtifactRelationshipRole role() {
		return ROLE;
	}

	@Override
	public boolean isTarget(final Artifact targetCandidateArtifact) {
		return targetCandidateArtifact instanceof final BuildingBlock buildingBlock && buildingBlock.getType() == QueryDescriptor.type();
	}

	@Override
	public Stream<JavaClass> sources(final JavaClass targetClass) {
		return targetClass.getConstructorCallsToSelf()
			.stream()
			.map(JavaConstructorCall::getOriginOwner)
			.filter(candidate -> !candidate.isAssignableTo(targetClass.getName()));
	}

}
