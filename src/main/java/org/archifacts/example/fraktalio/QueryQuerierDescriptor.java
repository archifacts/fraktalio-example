package org.archifacts.example.fraktalio;

import static org.archifacts.example.fraktalio.FraktalioDescriptors.BuildingBlockDescriptors.Query;

import java.util.stream.Stream;

import org.archifacts.core.descriptor.TargetBasedArtifactRelationshipDescriptor;
import org.archifacts.core.model.Artifact;
import org.archifacts.core.model.ArtifactRelationshipRole;
import org.archifacts.core.model.BuildingBlock;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructorCall;

class QueryQuerierDescriptor implements TargetBasedArtifactRelationshipDescriptor {

	@Override
	public ArtifactRelationshipRole role() {
		return ArtifactRelationshipRole.of("queries");
	}

	@Override
	public boolean isTarget(Artifact targetCandidateArtifact) {
		return targetCandidateArtifact instanceof BuildingBlock buildingBlock && buildingBlock.getType() == Query;
	}

	@Override
	public Stream<JavaClass> sources(JavaClass targetClass) {
		return targetClass.getConstructorCallsToSelf()
			.stream()
			.map(JavaConstructorCall::getOriginOwner)
			.filter(candidate -> !candidate.isAssignableTo(targetClass.getName()));
	}

}
