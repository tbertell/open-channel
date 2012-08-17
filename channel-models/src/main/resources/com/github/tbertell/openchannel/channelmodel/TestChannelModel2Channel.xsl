<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="sayValue" />
	<xsl:param name="timerPeriod" />
	<xsl:template match="/">
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
  xmlns:camel="http://camel.apache.org/schema/blueprint"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="       http://www.osgi.org/xmlns/blueprint/v1.0.0 http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd       http://camel.apache.org/schema/blueprint http://camel.apache.org/schema/blueprint/camel-blueprint.xsd">
  <bean class="com.github.tbertell.openchannel.camel.channel.HelloBean" id="helloBean">
    <property name="say" value="{$sayValue}"/>
  </bean>
  <camelContext id="blueprintContext" trace="false" xmlns="http://camel.apache.org/schema/blueprint">
    <route id="timerToLog">
      <from uri="timer:foo?period={$timerPeriod}"/>
      <setBody>
        <method method="hello" ref="helloBean"/>
      </setBody>
      <log message="The message contains ${body}"/>
    </route>
  </camelContext>
</blueprint>
	</xsl:template>
</xsl:stylesheet>