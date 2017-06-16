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

import java.text.MessageFormat;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.eclipse.aether.RepositorySystemSession;
import org.mule.tools.maven.repository.RepositoryGenerator;

@Mojo(name = "process-sources",
    defaultPhase = LifecyclePhase.PROCESS_SOURCES,
    requiresDependencyResolution = ResolutionScope.RUNTIME)
public class ProcessSourcesMojo extends AbstractMuleMojo {

  @Component
  protected org.eclipse.aether.RepositorySystem aetherRepositorySystem;
  @Parameter(defaultValue = "${repositorySystemSession}")
  protected RepositorySystemSession aetherRepositorySystemSession;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    long start = System.currentTimeMillis();
    getLog().debug("Processing sources...");

    if (!lightweightPackage) {
      RepositoryGenerator repositoryGenerator = new RepositoryGenerator(session, project, localRepository,
                                                                        remoteArtifactRepositories, outputDirectory,
                                                                        getLog(), aetherRepositorySystem,
                                                                        aetherRepositorySystemSession,
                                                                        repositorySystem, projectBuilder);
      repositoryGenerator.generate();
    }

    getLog().debug(MessageFormat.format("Process sources done ({0}ms)", System.currentTimeMillis() - start));
  }
}