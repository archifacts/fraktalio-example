package org.archifacts.example.fraktalio.asciidoc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.archifacts.core.model.Application;
import org.archifacts.example.fraktalio.model.FraktalioApplication;
import org.archifacts.integration.asciidoc.AsciiDoc;
import org.archifacts.integration.asciidoc.TextDocElement;

public class AsciiDocWriter {

	public void writeAsciidoc(final Application application, final Path outputFile) throws IOException {

		final AsciiDoc asciiDoc = new AsciiDoc("Fraktalio");
		final FraktalioApplication fraktalioApplication = new FraktalioApplication(application);
		asciiDoc.addDocElement(new TextDocElement("\ninclude::_preamble.adoc[]\n") );
		asciiDoc.addDocElement(new ModuleInteractionMatrix(fraktalioApplication));
		asciiDoc.addDocElement(new C4ModelRenderer(fraktalioApplication));
		try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {
			asciiDoc.writeToWriter(writer);
		}

		System.out.println("Asciidoc written to " + outputFile.toString());
	}
}
