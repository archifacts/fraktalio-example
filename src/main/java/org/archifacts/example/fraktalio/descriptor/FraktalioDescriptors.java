package org.archifacts.example.fraktalio.descriptor;

import org.archifacts.core.descriptor.ArtifactContainerDescriptor;
import org.archifacts.core.descriptor.BuildingBlockDescriptor;
import org.archifacts.core.descriptor.TargetBasedArtifactRelationshipDescriptor;
import org.archifacts.core.model.BuildingBlockType;

public final class FraktalioDescriptors {

	private FraktalioDescriptors() {
	}

	public static final class BuildingBlockDescriptors {

		private BuildingBlockDescriptors() {
		}

		public static final BuildingBlockDescriptor EventDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(BuildingBlockType.of("Event"), "Event");
		public static final BuildingBlockDescriptor CommandDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(BuildingBlockType.of("Command"), "Command");
		public static final BuildingBlockDescriptor QueryDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(BuildingBlockType.of("Query"), "Query");

	}

	public static final class RelationshipDescriptors {

		private RelationshipDescriptors() {
		}

		public static final TargetBasedArtifactRelationshipDescriptor EventPublisherDescriptor = new EventPublisherDescriptor();
		public static final TargetBasedArtifactRelationshipDescriptor CommandSenderDescriptor = new CommandSenderDescriptor();
		public static final TargetBasedArtifactRelationshipDescriptor QueryQuerierDescriptor = new QueryQuerierDescriptor();

	}

	public static final class ContainerDescriptors {

		private ContainerDescriptors() {
		}

		public static final ArtifactContainerDescriptor ModuleDescriptor = new ModuleDescriptor("com.fraktalio");

	}

}
