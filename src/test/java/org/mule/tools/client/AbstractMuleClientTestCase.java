/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AbstractMuleClientTestCase {

  private AbstractMuleClient client;

  private AbstractMuleClient createClient(String businessgroup) {
    return new AbstractMuleClient(null, null, null, null, null, businessgroup) {};
  }

  @Test
  public void emptyBusinessGroup() {
    client = createClient("");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(0));
  }

  @Test
  public void nullBusinessGroup() {
    client = createClient(null);
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(0));
  }

  @Test
  public void simpleBusinessGroup() {
    client = createClient("my-business-group");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(1));
    assertThat(result[0], equalTo("my-business-group"));
  }

  @Test
  public void groupWithOneBackslash() {
    client = createClient("my\\\\business\\\\group");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(1));
    assertThat(result[0], equalTo("my\\business\\group"));
  }

  @Test
  public void oneBackslashAtEnd() {
    client = createClient("root\\\\");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(1));
    assertThat(result[0], equalTo("root\\"));
  }

  @Test
  public void twoBackslashAtEnd() {
    client = createClient("root\\\\\\\\");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(1));
    assertThat(result[0], equalTo("root\\\\"));
  }

  @Test
  public void groupWithTwoBackslash() {
    client = createClient("my\\\\\\\\group");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(1));
    assertThat(result[0], equalTo("my\\\\group"));
  }

  @Test
  public void hierarchicalBusinessGroup() {
    client = createClient("root\\leaf");
    String[] result = client.createBusinessGroupPath();
    assertThat(result.length, equalTo(2));
    assertThat(result[0], equalTo("root"));
    assertThat(result[1], equalTo("leaf"));
  }

}