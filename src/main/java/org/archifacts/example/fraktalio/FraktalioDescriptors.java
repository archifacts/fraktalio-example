package org.archifacts.example.fraktalio;

import org.archifacts.core.descriptor.BuildingBlockDescriptor;
import org.archifacts.core.model.BuildingBlockType;

final class FraktalioDescriptors {

	
	private FraktalioDescriptors() {
	}

	static final class BuildingBlockDescriptors {
		final static BuildingBlockType Event = BuildingBlockType.of("Event");
		final static BuildingBlockType Command = BuildingBlockType.of("Command");
		final static BuildingBlockType Query = BuildingBlockType.of("Query");

		private BuildingBlockDescriptors() {
		}

		static final BuildingBlockDescriptor EventDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(Event, "Event");
		static final BuildingBlockDescriptor CommandDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(Command, "Command");
		static final BuildingBlockDescriptor QueryDescriptor = BuildingBlockDescriptor.forSimpleNameEndingWith(Query, "Query");

	}

	static final class RelationshipDescriptors {

		private RelationshipDescriptors() {
		}

		static final EventPublisherDescriptor EventPublisherDescriptor = new EventPublisherDescriptor(); 
		static final CommandSenderDescriptor CommandSenderDescriptor = new CommandSenderDescriptor(); 
		static final QueryQuerierDescriptor QueryQuerierDescriptor = new QueryQuerierDescriptor(); 

	}

	static final class ContainerDescriptors {

		private ContainerDescriptors() {
		}
		
		static final ModuleDescriptor ModuleDescriptor = new ModuleDescriptor("com.fraktalio");

	}

}
