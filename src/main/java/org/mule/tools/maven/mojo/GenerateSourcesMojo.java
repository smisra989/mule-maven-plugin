/*
 * Mule ESB Maven Tools
 * <p>
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * <p>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.maven.mojo;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import org.mule.tools.maven.mojo.model.PackagingType;
import org.mule.tools.api.ContentGenerator;

/**
 * Copy resource to the proper places
 */
@Mojo(name = "generate-sources",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresDependencyResolution = ResolutionScope.RUNTIME)
public class GenerateSourcesMojo extends AbstractMuleMojo {

  public void execute() throws MojoExecutionException, MojoFailureException {
    long start = System.currentTimeMillis();
    getLog().debug("Generating source code...");

    try {
      getContentGenerator().createContent();
    } catch (IllegalArgumentException | IOException e) {
      throw new MojoFailureException("Fail to generate sources", e);
    }

    getLog().debug(MessageFormat.format("Source code generation done ({0}ms)", System.currentTimeMillis() - start));
  }

  protected ContentGenerator getContentGenerator() {
    return new ContentGenerator(project.getGroupId(), project.getArtifactId(), project.getVersion(),
                                PackagingType.fromString(project.getPackaging()),
                                Paths.get(projectBaseFolder.toURI()), Paths.get(project.getBuild().getDirectory()));
  }
}
